package com.wordsmith.Services;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.wordsmith.Entity.Membership;
import com.wordsmith.Repositories.MembershipRepository;
import com.wordsmith.Util.PayPalClient;

import jakarta.transaction.Transactional;

@Service
public class MembershipService {


    @Value("${paypal.plan.monthly}")
    private String monthlyPlanId;

    @Value("${paypal.plan.halfyear}")
    private String halfYearPlanId;

    @Value("${paypal.plan.yearly}")
    private String yearlyPlanId;

    private final MembershipRepository membershipRepo;
    private final PayPalClient payPalClient;

    public MembershipService(MembershipRepository membershipRepo, PayPalClient payPalClient) {
        this.membershipRepo = membershipRepo;
        this.payPalClient = payPalClient;
    }

    public Membership save(Membership membership) {
        return membershipRepo.save(membership);
    }

    public Optional<Membership> findByUsername(String username) {
        return membershipRepo.findTopByUsernameOrderByCreatedAtDesc(username);
    }

    public Optional<Membership> findBySubscriptionId(String subscriptionId) {
        return membershipRepo.findBySubscriptionId(subscriptionId);
    }

    public List<Membership> findAllActive() {
        return membershipRepo.findByStatus("ACTIVE");
    }

    public boolean isActiveMember(String username) {
        return membershipRepo.findByUsername(username)
                .map(Membership::isActive)
                .orElse(false);
    }

    // ---------------------------------------------------
    // CANCEL / EXPIRE / SUSPEND / REACTIVATE
    // ---------------------------------------------------
    public void deactivateMembership(String subscriptionId) {
        updateStatus(subscriptionId, "CANCELLED");
    }

    public void cancelMembership(String subscriptionId) {
        updateStatus(subscriptionId, "CANCELLED");
    }

    public void expireMembership(String subscriptionId) {
        updateStatus(subscriptionId, "EXPIRED");
    }

    public void suspendMembership(String subscriptionId) {
        updateStatus(subscriptionId, "SUSPENDED");
    }

    public void reactivateMembership(String subscriptionId) {
        updateStatus(subscriptionId, "ACTIVE");
    }


    // ---------------------------------------------------
    // 1️⃣ CONFIRM SUBSCRIPTION (CALLED BY /membership/confirm)
    // ---------------------------------------------------
    @Transactional
    public void createOrUpdatePendingMembership(String username, String planId, String subscriptionId) {

        // 1️⃣ Check if subscriptionId exists and belongs to someone else
    Optional<Membership> existingSub = membershipRepo.findBySubscriptionId(subscriptionId);

    if (existingSub.isPresent()) {
        Membership m = existingSub.get();

        // Case: Webhook created placeholder
        if (m.getUsername() == null || m.getUsername().startsWith("PENDING-")) {
            m.setUsername(username);
            m.setPlanId(planId);
            m.setStatus("PENDING");
            membershipRepo.save(m);
            System.out.println("✔ Placeholder membership with Subscription Id:"+ subscriptionId+" updated to user: " + username);
            return;
        }

        // Case: Already linked to correct user
        if (m.getUsername().equals(username)) {
            System.out.println("✔ Subscription with Subscription Id:"+ subscriptionId+" already linked to the User: " + username);
            return;
        }

        // Case: Linked to a DIFFERENT USER → security issue
        throw new IllegalStateException("❌ Subscription with Subscription Id:"+ subscriptionId+" already linked to another user.");
    }

    // 2️⃣ Subscription does not exist → create new row
    Membership newM = new Membership();
    newM.setUsername(username);
    newM.setPlanId(planId);
    newM.setSubscriptionId(subscriptionId);
    newM.setStatus("PENDING");
    newM.setAutoRenew(true);
    membershipRepo.save(newM);

    System.out.println("✔ New membershipwith Subscription Id:"+ subscriptionId+" created and linked to user: " + username);
    }


    // ---------------------------------------------------
    // 2️⃣ WEBHOOK: update status
    // ---------------------------------------------------
    public void updateStatus(String subscriptionId, String status) {
        membershipRepo.findBySubscriptionId(subscriptionId).ifPresent(m -> {
            m.setStatus(status);
            membershipRepo.save(m);
        });
    }

    // ---------------------------------------------------
    // 3️⃣ WEBHOOK: update plan_id (user upgraded/downgraded)
    // ---------------------------------------------------
    public void updatePlan(String subscriptionId, String planId) {
        membershipRepo.findBySubscriptionId(subscriptionId).ifPresent(m -> {
            m.setPlanId(planId);
            membershipRepo.save(m);
        });
    }

    // ---------------------------------------------------
    // 4️⃣ WEBHOOK: Activated subscription
    // ---------------------------------------------------
    public void activateMembership(String subscriptionId, ZonedDateTime endDate) {
        membershipRepo.findBySubscriptionId(subscriptionId).ifPresent(m -> {
            m.setStatus("ACTIVE");
            m.setStartDate(ZonedDateTime.now());
            m.setEndDate(endDate);
            membershipRepo.save(m);
        });
    }

    // ---------------------------------------------------
    // 5️⃣ WEBHOOK: Renew membership on payment
    // ---------------------------------------------------
    public void extendToNextBilling(String subscriptionId, ZonedDateTime nextBilling) {
        membershipRepo.findBySubscriptionId(subscriptionId).ifPresent(m -> {
            m.setEndDate(nextBilling);
            m.setStatus("ACTIVE");
            membershipRepo.save(m);
        });
    }


    public boolean verifySubscriptionOwnership(String subscriptionId, String payerEmail) {
        Optional<Membership> opt = membershipRepo.findBySubscriptionId(subscriptionId);

        // 1) Does subscription exist?
        if (opt.isEmpty()) {
            return false;
        }

        Membership m = opt.get();

        // 2) If payerEmail is available from webhook, ensure match
        if (payerEmail != null && m.getUsername() != null) {
            String emailPart = m.getUsername() + "@"; 
            // We cannot fully validate because username != email always,
            // but we can detect MAJOR mismatches.

            if (!payerEmail.toLowerCase().contains(m.getUsername().toLowerCase())) {
                return false; 
            }
        }

        return true;
    }

    public Optional<Membership> findActiveByUsername(String username) {
        return membershipRepo.findByUsernameAndStatus(username, "ACTIVE");
    }

    public List<Membership> findMembershipHistory(String username) {
        return membershipRepo.findAllByUsernameOrderByStartDateDesc(username);
    }

    public long countAll() {
        return membershipRepo.count();
    }

    public long countByStatus(String status) {
        return membershipRepo.countByStatus(status);
    }

    public List<Membership> findAllOrdered() {
        return membershipRepo.findAllByOrderByCreatedAtDesc();
    }

    public void resolveFromPayPal(Membership membership, JsonNode subscription) {

        ZonedDateTime nextBillingDate = null;

        if (subscription.has("billing_info")
                && subscription.get("billing_info").has("next_billing_time")) {

            nextBillingDate = ZonedDateTime.parse(
                    subscription.get("billing_info")
                            .get("next_billing_time")
                            .asText()
            );
        }

        membership.setStatus("ACTIVE");
        membership.setStartDate(ZonedDateTime.now());
        membership.setEndDate(nextBillingDate);
        membership.setAutoRenew(true);

        membershipRepo.save(membership);
    }

    public List<Membership> findByStatusOrdered(String status) {
        return membershipRepo
                .findByStatusOrderByCreatedAtDesc(status);
    }


    public boolean existsLinkedSubscription(String subscriptionId) {
        return membershipRepo.existsBySubscriptionIdAndUsernameIsNotNull(subscriptionId);
    }


    public Map<String, String> getPlanIds() {
        Map<String, String> plans = new HashMap<>();
        plans.put("MONTHLY", monthlyPlanId);
        plans.put("HALFYEAR", halfYearPlanId);
        plans.put("YEARLY", yearlyPlanId);
        return plans;
    }



}

