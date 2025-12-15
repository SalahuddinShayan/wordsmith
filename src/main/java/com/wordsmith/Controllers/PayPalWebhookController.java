package com.wordsmith.Controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wordsmith.Entity.Membership;
import com.wordsmith.Entity.PaymentTransaction;
import com.wordsmith.Entity.WebhookEventLog;
import com.wordsmith.Repositories.WebhookEventLogRepository;
import com.wordsmith.Services.MembershipService;
import com.wordsmith.Services.PaymentTransactionService;
import com.wordsmith.Services.WebhookFailureService;
import com.wordsmith.Util.EmailMasker;
import com.wordsmith.Util.PayPalClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import jakarta.servlet.http.HttpServletRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/paypal")
public class PayPalWebhookController {

    private static final Logger logger = LoggerFactory.getLogger(PayPalWebhookController.class);

    private final MembershipService membershipService;
    private final PaymentTransactionService paymentService;
    private final WebhookFailureService webhookFailureService;
    private final PayPalClient payPalClient;
    private final WebhookEventLogRepository eventLogRepo;

    
    private final String webhookId;


    private final ObjectMapper mapper = new ObjectMapper();

    public PayPalWebhookController(MembershipService membershipService, PaymentTransactionService paymentService, WebhookFailureService webhookFailureService,
                                   PayPalClient payPalClient, WebhookEventLogRepository eventLogRepo) {
        this.membershipService = membershipService;
        this.paymentService = paymentService;
        this.webhookFailureService = webhookFailureService;
        this.payPalClient = payPalClient;
        this.eventLogRepo = eventLogRepo;

        // 🔥 Load webhookId based on active profile (dev → sandbox, prod → DB)
        this.webhookId = payPalClient.getWebhookId();
    }

    // ===============================================================
    // 🔥 MAIN WEBHOOK ENDPOINT
    // ===============================================================
    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(HttpServletRequest request) {

        String rawBody = null;
        JsonNode webhookEventJson = null;
        String eventId = "UNKNOWN";
        String eventType = "UNKNOWN";
        WebhookEventLog eventLog = null;

        try {
            // 1) Read raw body
            rawBody = readRequestBody(request);

            // 2) Parse JSON
            webhookEventJson = mapper.readTree(rawBody);

            // 3) Extract required fields
            eventId = optText(webhookEventJson, "id");
            eventType = optText(webhookEventJson, "event_type");

            if (eventId == null || eventType == null) {
                webhookFailureService.logFailure(
                        "MISSING_EVENT_FIELDS",
                        "Missing id or event_type",
                        rawBody
                );
                return ResponseEntity.badRequest().body("Missing id or event_type");
            }

            // 4) Signature headers
            String transmissionId  = request.getHeader("paypal-transmission-id");
            String timestamp       = request.getHeader("paypal-transmission-time");
            String certUrl         = request.getHeader("paypal-cert-url");
            String authAlgo        = request.getHeader("paypal-auth-algo");
            String transmissionSig = request.getHeader("paypal-transmission-sig");

            if (isBlank(transmissionId) || isBlank(timestamp) ||
                isBlank(certUrl) || isBlank(authAlgo) || isBlank(transmissionSig)) {

                webhookFailureService.logFailure(
                        eventId,
                        eventType,
                        null,
                        rawBody,
                        buildHeadersJson(request),
                        "Missing PayPal security headers"
                );

                return ResponseEntity.badRequest().body("Missing headers");
            }

            // 5) Verify signature
            Map<String, Object> verificationPayload = new HashMap<>();
            verificationPayload.put("transmission_id", transmissionId);
            verificationPayload.put("transmission_time", timestamp);
            verificationPayload.put("cert_url", certUrl);
            verificationPayload.put("auth_algo", authAlgo);
            verificationPayload.put("transmission_sig", transmissionSig);
            verificationPayload.put("webhook_id", webhookId);
            verificationPayload.put("webhook_event", webhookEventJson);

            if (!payPalClient.verifySignature(verificationPayload)) {
                webhookFailureService.logFailure(
                        eventId,
                        eventType,
                        null,
                        rawBody,
                        buildHeadersJson(request),
                        "Signature verification failed"
                );
                return ResponseEntity.badRequest().body("Invalid signature");
            }

            // -------------------------------------------------
            // ✅ CORRECT REPLAY HANDLING (FIXED)
            // -------------------------------------------------

            eventLog = eventLogRepo.findById(eventId).orElse(null);

            if (eventLog != null && eventLog.isProcessedSuccessfully()) {
                logger.info("WEBHOOK_REPLAY_CONFIRMED id={} type={}", eventId, eventType);
                return ResponseEntity.ok("Replay ignored");
            }

            if (eventLog == null) {
                eventLog = new WebhookEventLog(eventId, eventType);
                eventLog.setProcessedSuccessfully(false);
                eventLogRepo.save(eventLog);
            }

            // ------------------------------------------------------
            // 🔐 OWNERSHIP VERIFICATION — ONLY WHEN SAFE
            // ------------------------------------------------------
            String subscriptionId = extractSubscriptionId(webhookEventJson);
            String payerEmail     = extractPayerEmail(webhookEventJson);

            boolean requiresLinkedSubscription =
                "BILLING.SUBSCRIPTION.ACTIVATED".equals(eventType)
             || "PAYMENT.SALE.COMPLETED".equals(eventType);

        if (requiresLinkedSubscription && subscriptionId != null) {

            boolean existsAndLinked =
                    membershipService.existsLinkedSubscription(subscriptionId);

            if (!existsAndLinked) {

                String msg = "Subscription not yet linked to any user";

                webhookFailureService.logFailure(
                        eventId,
                        eventType,
                        subscriptionId,
                        rawBody,
                        buildHeadersJson(request),
                        msg
                );

                logger.warn(
                    "WEBHOOK_SUB_NOT_LINKED id={} type={} subId={}",
                    eventId, eventType, subscriptionId
                );

                return ResponseEntity.badRequest().body("Subscription not linked");
            }
        }



            // -------------------------------------------------
            // BUSINESS LOGIC
            // -------------------------------------------------

            processEvent(eventType, rawBody);

            // -------------------------------------------------
            // ✅ MARK SUCCESS ONLY HERE
            // -------------------------------------------------

            eventLog.setProcessedSuccessfully(true);
            eventLogRepo.save(eventLog);

            return ResponseEntity.ok("Webhook processed");

        } catch (Exception e) {

            logger.error("WEBHOOK_FATAL_ERROR id={} type={}", eventId, eventType, e);

            webhookFailureService.logFailure(
                    eventId,
                    eventType,
                    null,
                    rawBody != null ? rawBody : "",
                    buildHeadersJson(request),
                    "Unexpected error: " + e.getMessage()
            );

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Webhook error");
        }
    }



    // ===============================================================
    // 🔧 Read body safely
    // ===============================================================
    private String readRequestBody(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;

        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        return sb.toString();
    }

    // ===============================================================
    // 🔥 Event Router
    // ===============================================================
    private void processEvent(String eventType, String rawBody) {
        logger.info("📡 Processing Webhook Event: {}", eventType);

        try {
            switch (eventType) {
                case "BILLING.SUBSCRIPTION.CREATED":      handleSubscriptionCreated(rawBody); break;
                case "BILLING.SUBSCRIPTION.ACTIVATED":    handleSubscriptionActivated(rawBody); break;
                case "BILLING.SUBSCRIPTION.CANCELLED":    handleSubscriptionCancelled(rawBody); break;
                case "BILLING.SUBSCRIPTION.EXPIRED":      handleSubscriptionExpired(rawBody); break;
                case "PAYMENT.SALE.COMPLETED":            handlePaymentCompleted(rawBody); break;
                case "BILLING.SUBSCRIPTION.PAYMENT.FAILED": handlePaymentFailed(rawBody); break;
                case "BILLING.SUBSCRIPTION.SUSPENDED":    handleSubscriptionSuspended(rawBody); break;
                case "BILLING.SUBSCRIPTION.RE-ACTIVATED": handleSubscriptionReactivated(rawBody); break;
                case "BILLING.SUBSCRIPTION.UPDATED":      handleSubscriptionUpdated(rawBody); break;
                case "PAYMENT.SALE.DENIED":               handlePaymentDenied(rawBody); break;

                default:
                    logger.warn("⚠️ Unhandled PayPal Event Type: {}", eventType);
            }

        } catch (Exception ex) {
            logger.error("❌ Exception while processing event {}: {}", eventType, ex.getMessage(), ex);

            webhookFailureService.logFailure(
                    eventType + "_ERROR",
                    ex.getMessage(),
                    rawBody
            );
        }
    }

    // ===============================================================
    // 🔥 EVENT HANDLERS (All rewritten with real logging)
    // ===============================================================

    private void handleSubscriptionCreated(String rawBody) throws Exception {
        JsonNode root = mapper.readTree(rawBody);
        JsonNode resource = safeGet(root, "resource");

        if (resource == null) {
            logger.error("WEBHOOK_SUB_CREATED_MISSING_RESOURCE raw={}", rawBody);
            webhookFailureService.logFailure(
                    "UNKNOWN", "SUB_CREATED", null, rawBody, "N/A",
                    "Missing resource object during Subscription Created"
            );
            return;
        }

        String subscriptionId = optText(resource, "id");
        String planId = optText(resource, "plan_id");
        String email = optText(resource, "subscriber", "email_address");

        if (subscriptionId == null) {
            logger.error("WEBHOOK_SUB_CREATED_NO_SUB_ID raw={}", rawBody);
            return;
        }

        logger.info("SUBSCRIPTION_CREATED subscriptionId={} planId={}", subscriptionId, planId);

        // Avoid duplicate creation
        var existing = membershipService.findBySubscriptionId(subscriptionId);
        if (existing.isPresent()) {
            logger.info("SUB_CREATED_ALREADY_EXISTS subscriptionId={}", subscriptionId);
            return;
        }

        Membership m = new Membership();
        m.setSubscriptionId(subscriptionId);
        m.setPlanId(planId);
        m.setStatus("PENDING");
        m.setAutoRenew(true);
        m.setUsername(email != null ? email : "PENDING-" + subscriptionId);

        membershipService.save(m);
        logger.info("SUB_CREATED_SAVED subscriptionId={}", subscriptionId);
    }

    private void handleSubscriptionActivated(String rawBody) throws Exception {
        JsonNode root = mapper.readTree(rawBody);
        JsonNode resource = safeGet(root, "resource");

        if (resource == null) {
            logger.error("WEBHOOK_SUB_ACTIVATED_MISSING_RESOURCE raw={}", rawBody);
            return;
        }

        String subscriptionId = optText(resource, "id");
        String nextBilling = optText(resource, "billing_info", "next_billing_time");

        if (subscriptionId == null) {
            logger.error("WEBHOOK_SUB_ACTIVATED_NO_SUB_ID raw={}", rawBody);
            return;
        }

        logger.info("SUBSCRIPTION_ACTIVATED subscriptionId={}", subscriptionId);

        membershipService.findBySubscriptionId(subscriptionId).ifPresent(m -> {
            m.setStatus("ACTIVE");
            m.setStartDate(ZonedDateTime.now());
            if (nextBilling != null) {
                m.setEndDate(ZonedDateTime.parse(nextBilling));
            }
            membershipService.save(m);
        });
    }


    private void handleSubscriptionCancelled(String rawBody) throws Exception {
        JsonNode root = mapper.readTree(rawBody);
        JsonNode resource = safeGet(root, "resource");

        if (resource == null) return;

        String subscriptionId = optText(resource, "id");
        if (subscriptionId == null) return;

        logger.info("SUBSCRIPTION_CANCELLED subscriptionId={}", subscriptionId);

        membershipService.updateStatus(subscriptionId, "CANCELLED");
    }


    private void handleSubscriptionExpired(String rawBody) throws Exception {
        JsonNode root = mapper.readTree(rawBody);
        JsonNode resource = safeGet(root, "resource");

        if (resource == null) return;

        String subscriptionId = optText(resource, "id");
        if (subscriptionId == null) return;

        logger.info("SUBSCRIPTION_EXPIRED subscriptionId={}", subscriptionId);

        membershipService.updateStatus(subscriptionId, "EXPIRED");
    }


    private void handlePaymentCompleted(String rawBody) throws Exception {
        JsonNode root = mapper.readTree(rawBody);
        JsonNode resource = safeGet(root, "resource");

        if (resource == null) {
            logger.error("WEBHOOK_PAY_COMPLETE_NO_RESOURCE raw={}", rawBody);
            return;
        }

        String orderId  = optText(resource, "id");
        String subscriptionId = optText(resource, "billing_agreement_id");

        JsonNode amountNode = safeGet(resource, "amount");
        final double amount;
        final String currency;

        if (amountNode != null) {
            double tempAmount = 0.0;
            String tempCurrency = null;
            try {
                tempAmount = amountNode.get("total").asDouble(0.0);
                tempCurrency = optText(amountNode, "currency");
            } catch (Exception e) {
                logger.warn("WEBHOOK_PAY_AMOUNT_PARSE_FAIL orderId={} raw={}", orderId, rawBody);
            }
            amount = tempAmount;
            currency = tempCurrency;
        } else {
            amount = 0.0;
            currency = null;
        }

        String payerEmail = optText(resource, "payer", "payer_info", "email");
        String payerName = (
                optText(resource, "payer", "payer_info", "first_name") + " " +
                optText(resource, "payer", "payer_info", "last_name")
        ).trim();

        logger.info("PAYMENT_COMPLETED orderId={} subscriptionId={}", orderId, subscriptionId);

        if (subscriptionId == null) {
            logger.warn("PAYMENT_COMPLETE_NO_SUB_ID orderId={} raw={}", orderId, rawBody);
            return;
        }

        // Get the correct username from membership
        membershipService.findBySubscriptionId(subscriptionId).ifPresent(m -> {
            String username = m.getUsername();
            paymentService.savePayment(
                    subscriptionId, orderId, amount,
                    currency, payerEmail, payerName, username
            );

            JsonNode subJson = payPalClient.getSubscription(subscriptionId);
            String nextBilling = optText(subJson, "billing_info", "next_billing_time");

            if (nextBilling != null) {
                membershipService.extendToNextBilling(subscriptionId, ZonedDateTime.parse(nextBilling));
                logger.info("PAYMENT_COMPLETED_EXTENDED subscriptionId={} nextBilling={}",
                        subscriptionId, nextBilling);
            }
        });
    }


    private void handlePaymentFailed(String rawBody) throws Exception {
        JsonNode root = mapper.readTree(rawBody);
        JsonNode resource = safeGet(root, "resource");

        if (resource == null) return;

        String subscriptionId = optText(resource, "id");
        if (subscriptionId == null) return;

        logger.warn("PAYMENT_FAILED subscriptionId={}", subscriptionId);

        membershipService.updateStatus(subscriptionId, "PAST_DUE");
    }


    private void handleSubscriptionSuspended(String rawBody) throws Exception {
        JsonNode root = mapper.readTree(rawBody);
        JsonNode resource = safeGet(root, "resource");

        if (resource == null) return;

        String subscriptionId = optText(resource, "id");
        if (subscriptionId == null) return;

        logger.warn("SUBSCRIPTION_SUSPENDED subscriptionId={}", subscriptionId);

        membershipService.updateStatus(subscriptionId, "SUSPENDED");
    }


    private void handleSubscriptionReactivated(String rawBody) throws Exception {
        JsonNode root = mapper.readTree(rawBody);
        JsonNode resource = safeGet(root, "resource");

        if (resource == null) return;

        String subscriptionId = optText(resource, "id");
        if (subscriptionId == null) return;

        logger.info("SUBSCRIPTION_REACTIVATED subscriptionId={}", subscriptionId);

        membershipService.updateStatus(subscriptionId, "ACTIVE");
    }


    private void handleSubscriptionUpdated(String rawBody) throws Exception {
        JsonNode root = mapper.readTree(rawBody);
        JsonNode resource = safeGet(root, "resource");

        if (resource == null) return;

        String subscriptionId = optText(resource, "id");
        String newPlanId = optText(resource, "plan_id");

        if (subscriptionId == null) return;

        logger.info("SUBSCRIPTION_UPDATED subscriptionId={} newPlan={}", subscriptionId, newPlanId);

        membershipService.updatePlan(subscriptionId, newPlanId);
    }


    private void handlePaymentDenied(String rawBody) throws Exception {
        JsonNode root = mapper.readTree(rawBody);
        JsonNode resource = safeGet(root, "resource");

        if (resource == null) return;

        String orderId = optText(resource, "id");

        logger.warn("PAYMENT_DENIED orderId={}", orderId);

        PaymentTransaction txn = new PaymentTransaction();
        txn.setPaypalOrderId(orderId);
        txn.setStatus("DENIED");
        paymentService.save(txn);
    }



    private String optText(JsonNode node, String fieldName) {
        if (node == null || !node.has(fieldName) || node.get(fieldName).isNull()) {
        return null;
        }
        return node.get(fieldName).asText(null);
    }

   
    // ---------------------------------------------
    // SAFE STRING EXTRACTION – 2 levels
    // optText(node, "subscriber", "email_address")
    // ---------------------------------------------
    private String optText(JsonNode root, String field1, String field2) {
        if (root == null || !root.has(field1)) return null;

        JsonNode child = root.get(field1);
        if (child == null || !child.has(field2) || child.get(field2).isNull()) return null;

        return child.get(field2).asText();
    }

    // ---------------------------------------------
    // SAFE STRING EXTRACTION – 3 levels
    // optText(node, "payer", "payer_info", "email")
    // ---------------------------------------------
    private String optText(JsonNode root, String f1, String f2, String f3) {
        if (root == null || !root.has(f1)) return null;

        JsonNode n1 = root.get(f1);
        if (n1 == null || !n1.has(f2)) return null;

        JsonNode n2 = n1.get(f2);
        if (n2 == null || !n2.has(f3) || n2.get(f3).isNull()) return null;

        return n2.get(f3).asText();
    }


    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    private String buildHeadersJson(HttpServletRequest request) {
        try {
            Map<String, String> headers = new HashMap<>();
            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames != null && headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                headers.put(name, request.getHeader(name));
            }
            return mapper.writeValueAsString(headers);
        } catch (Exception e) {
            // Fallback if JSON serialization fails
            return "{\"error\":\"failed to serialize headers\"}";
        }
    }


    private String extractSubscriptionId(JsonNode webhookEventJson) {

        String eventType = webhookEventJson.path("event_type").asText();
        JsonNode resource = webhookEventJson.path("resource");

        if (resource.isMissingNode()) {
            return null;
        }

        // Subscription lifecycle events
        if (eventType.startsWith("BILLING.SUBSCRIPTION")) {
            return resource.path("id").asText(null);
        }

        // Payment events (VERY IMPORTANT)
        if (eventType.equals("PAYMENT.SALE.COMPLETED")) {
            return resource.path("billing_agreement_id").asText(null);
        }

        return null;
    }


    private String extractPayerEmail(JsonNode event) {
        try {
            return event.path("resource")
                        .path("subscriber")
                        .path("email_address")
                        .asText(null);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Safely extracts a nested field from a JSON node.
     * Example: safeGet(json, "resource", "billing_info", "next_billing_time")
     */
    private JsonNode safeGet(JsonNode node, String... path) {
        if (node == null) return null;

        JsonNode current = node;
        for (String key : path) {
            if (current.has(key)) {
                current = current.get(key);
            } else {
                return null;
            }
        }
        return current;
    }



}
