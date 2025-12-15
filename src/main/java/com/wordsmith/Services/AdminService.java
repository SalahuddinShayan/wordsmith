package com.wordsmith.Services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.fasterxml.jackson.databind.JsonNode;
import com.wordsmith.Entity.Membership;
import com.wordsmith.Entity.WebhookEventLog;
import com.wordsmith.Repositories.WebhookEventLogRepository;
import com.wordsmith.Util.PayPalClient;

@Service
public class AdminService {

    private final MembershipService membershipService;
    private final PaymentTransactionService paymentService;
    private final WebhookFailureService webhookFailureService;
    private final WebhookEventLogRepository eventLogRepo;
    private final PayPalClient payPalClient;

    public AdminService(
            MembershipService membershipService,
            PaymentTransactionService paymentService,
            WebhookFailureService webhookFailureService,
            WebhookEventLogRepository eventLogRepo,
            PayPalClient payPalClient
    ) {
        this.membershipService = membershipService;
        this.paymentService = paymentService;
        this.webhookFailureService = webhookFailureService;
        this.eventLogRepo = eventLogRepo;
        this.payPalClient = payPalClient;
    }

    // ------------------------------------------------------------
    // Dashboard data
    // ------------------------------------------------------------
    public void populateDashboard(Model model) {

        model.addAttribute("totalMemberships", membershipService.countAll());
        model.addAttribute("activeMemberships", membershipService.countByStatus("ACTIVE"));
        model.addAttribute("pendingMemberships", membershipService.countByStatus("PENDING"));
        model.addAttribute("cancelledMemberships", membershipService.countByStatus("CANCELLED"));

        model.addAttribute("recentWebhookFailures",
                webhookFailureService.findRecentFailures(24));

        model.addAttribute("lastWebhookTime",
                eventLogRepo.findLatestEventTime().orElse(null));
    }

    // ------------------------------------------------------------
    // Membership list
    // ------------------------------------------------------------
    public void populateMemberships(String status, Model model) {

        if (status != null && !status.isBlank()) {
            model.addAttribute("memberships",
                    membershipService.findByStatusOrdered(status));
            model.addAttribute("selectedStatus", status);
        } else {
            model.addAttribute("memberships",
                    membershipService.findAllOrdered());
            model.addAttribute("selectedStatus", "");
        }
    }

    // ------------------------------------------------------------
    // Resolve membership manually
    // ------------------------------------------------------------
    public void resolveMembership(String subscriptionId) {

        Membership membership = membershipService
                .findBySubscriptionId(subscriptionId)
                .orElseThrow(() ->
                        new IllegalStateException(
                                "Membership not found for subscriptionId=" + subscriptionId));

        JsonNode subscription = payPalClient.getSubscription(subscriptionId);

        membershipService.resolveFromPayPal(membership, subscription);
    }

    // ------------------------------------------------------------
    // Transactions & failures
    // ------------------------------------------------------------
    public void populateTransactions(Model model) {
        model.addAttribute("transactions", paymentService.findAll());
    }

    public void populateWebhookFailures(Model model) {
        model.addAttribute("failures", webhookFailureService.findAll());
    }

    public List<WebhookEventLog> getRecentWebhookEvents() {
        return eventLogRepo.findAllByOrderByReceivedAtDesc();
    }
}
