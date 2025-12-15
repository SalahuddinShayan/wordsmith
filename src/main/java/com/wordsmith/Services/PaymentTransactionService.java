package com.wordsmith.Services;

import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.wordsmith.Entity.Membership;
import com.wordsmith.Entity.PaymentTransaction;
import com.wordsmith.Repositories.PaymentTransactionRepository;
import com.wordsmith.Util.EmailMasker;
import com.wordsmith.Util.PayPalClient;

import jakarta.transaction.Transactional;

@Service
public class PaymentTransactionService {

    private static final Logger log = LoggerFactory.getLogger(PaymentTransactionService.class);

    private final PaymentTransactionRepository paymentRepo;
    private final MembershipService membershipService;
    private final PayPalClient payPalClient;

    public PaymentTransactionService(PaymentTransactionRepository paymentRepo, 
                                     MembershipService membershipService, PayPalClient payPalClient) {
        this.paymentRepo = paymentRepo;
        this.membershipService = membershipService;
        this.payPalClient = payPalClient;
    }

    // ------------------------------------------------------
    // BASIC QUERIES
    // ------------------------------------------------------

    public PaymentTransaction save(PaymentTransaction transaction) {
        log.debug("PAYMENT_TXN_SAVE orderId={} subscriptionId={} user={}",
                transaction.getPaypalOrderId(),
                transaction.getSubscriptionId(),
                EmailMasker.mask(transaction.getUsername()));

        return paymentRepo.save(transaction);
    }

    public List<PaymentTransaction> findByUsername(String username) {
        log.debug("PAYMENT_TXN_FIND_BY_USER user={}", EmailMasker.mask(username));
        return paymentRepo.findByUsername(username);
    }

    public List<PaymentTransaction> findAll() {
        log.debug("PAYMENT_TXN_FIND_ALL");
        return paymentRepo.findAll();
    }

    // ------------------------------------------------------
    // SAVE PAYMENT (Called by Webhook: PAYMENT.SALE.COMPLETED)
    // ------------------------------------------------------

    public void savePayment(String subscriptionId,
                            String orderId,
                            double amount,
                            String currency,
                            String payerEmail,
                            String payerName,
                            String username) {

        String maskedPayer = EmailMasker.mask(payerEmail);
        String maskedUser = EmailMasker.mask(username);

        log.info("PAYMENT_TXN_CREATE_START orderId={} subscriptionId={} user={} amount={} {} payerEmail={}",
                orderId,
                subscriptionId,
                maskedUser,
                amount,
                currency,
                maskedPayer);

        // Defensive checks
        if (orderId == null || orderId.isBlank()) {
            log.warn("PAYMENT_TXN_MISSING_ORDER_ID subscriptionId={} user={} payerEmail={}",
                    subscriptionId, maskedUser, maskedPayer);
        }

        if (subscriptionId == null || subscriptionId.isBlank()) {
            log.warn("PAYMENT_TXN_MISSING_SUBSCRIPTION orderId={} payerEmail={} user={}",
                    orderId, maskedPayer, maskedUser);
        }

        PaymentTransaction txn = new PaymentTransaction();

        txn.setPaypalOrderId(orderId);
        txn.setSubscriptionId(subscriptionId);
        txn.setAmount(amount);
        txn.setCurrency(currency);
        txn.setStatus("COMPLETED");
        txn.setPayerEmail(payerEmail);
        txn.setPayerName(payerName);
        txn.setCreatedAt(ZonedDateTime.now());
        txn.setUsername(username);

        paymentRepo.save(txn);

        log.info("PAYMENT_TXN_CREATED orderId={} subscriptionId={} user={} amount={} {}",
                orderId, subscriptionId, maskedUser, amount, currency);
    }


    @Transactional
    public PaymentTransaction recoverFromPayPal(String subscriptionId) {

        // 1️⃣ Fetch membership
        Membership membership = membershipService
                .findBySubscriptionId(subscriptionId)
                .orElseThrow(() ->
                        new IllegalStateException("Membership not found for subscriptionId=" + subscriptionId)
                );

        // 2️⃣ Fetch transactions from PayPal (CORRECT SOURCE)
        JsonNode transactions = payPalClient.getSubscriptionTransactions(subscriptionId);

        JsonNode completedSale = null;

        for (JsonNode txn : transactions.path("transactions")) {
            if ("COMPLETED".equalsIgnoreCase(txn.path("status").asText())) {
                completedSale = txn;
                break;
            }
        }

        if (completedSale == null) {
            throw new IllegalStateException(
                    "No completed PayPal transaction found for subscriptionId=" + subscriptionId
            );
        }

        // 3️⃣ Extract transaction details
        String paypalOrderId = completedSale.path("id").asText();
        BigDecimal amount = new BigDecimal(
                completedSale.path("amount_with_breakdown")
                        .path("gross_amount")
                        .path("value")
                        .asText()
        );
        String currency = completedSale
                .path("amount_with_breakdown")
                .path("gross_amount")
                .path("currency_code")
                .asText();

        // 4️⃣ Idempotency
        if (paymentRepo.existsByPaypalOrderId(paypalOrderId)) {
            return paymentRepo.findByPaypalOrderId(paypalOrderId).orElseThrow();
        }

        // 5️⃣ Create transaction
        PaymentTransaction tx = new PaymentTransaction();
        tx.setUsername(membership.getUsername());
        tx.setPaypalOrderId(paypalOrderId);
        tx.setSubscriptionId(subscriptionId);
        tx.setPlanId(membership.getPlanId());
        tx.setAmount(amount.doubleValue());
        tx.setCurrency(currency);
        tx.setStatus("COMPLETED");
        tx.setCreatedAt(ZonedDateTime.now(ZoneOffset.UTC));

        return paymentRepo.save(tx);
    }

}
