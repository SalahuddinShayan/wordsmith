package com.wordsmith.Controllers;

import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.wordsmith.Entity.Membership;
import com.wordsmith.Entity.User;
import com.wordsmith.Services.MembershipService;
import com.wordsmith.Services.PaymentTransactionService;
import com.wordsmith.Util.EmailMasker;
import com.wordsmith.Util.PayPalClient;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/membership")
public class MembershipController {

    private static final Logger logger = LoggerFactory.getLogger(MembershipController.class);

    private final MembershipService membershipService;
    private final PaymentTransactionService paymentService;
    private final PayPalClient payPalClient;

    public MembershipController(
            MembershipService membershipService,
            PaymentTransactionService paymentService,
            PayPalClient payPalClient
    ) {
        this.membershipService = membershipService;
        this.paymentService = paymentService;
        this.payPalClient = payPalClient;

        logger.info("MembershipController initialized");
    }


    // ============================================================
    //  GET — Membership page
    // ============================================================
    @GetMapping
    public String membershipPage(Model model, HttpSession session) {

        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            logger.warn("Unauthorized access to /membership — redirecting to login");
            return "redirect:/auth/loginpage";
        }

        String maskedUser = EmailMasker.mask(user.getUsername());
        logger.info("Loading membership page for user={}", maskedUser);

        Optional<Membership> existing = membershipService.findByUsername(user.getUsername());
        existing.ifPresent(m -> model.addAttribute("membership", m));

        // ✅ Use dynamic PayPal clientId (dev sandbox OR prod live)
        model.addAttribute("paypalClientId", payPalClient.getClientId());
        model.addAttribute("paypalPlans", membershipService.getPlanIds());

        logger.debug("Membership page rendered — user={}, existingMembership={}",
                maskedUser,
                existing.isPresent());

        return "membership";
    }


    // ============================================================
    //  POST — Cancel membership (manual)
    // ============================================================
    @PostMapping("/cancel")
    public String cancelMembership(
            @RequestParam("subscriptionId") String subscriptionId,
            HttpSession session,
            Model model
    ) {
        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            logger.warn("Unauthorized membership cancel attempt — no user in session");
            return "redirect:/auth/loginpage";
        }

        logger.info("Cancelling subscription manually — user={}, subscriptionId={}",
                EmailMasker.mask(user.getUsername()),
                subscriptionId);

        membershipService.deactivateMembership(subscriptionId);

        model.addAttribute("message", "Membership cancelled successfully.");

        return "redirect:/membership";
    }



    // ============================================================
    //  GET — Manual Ajax status refresh
    // ============================================================
    @GetMapping("/status")
    @ResponseBody
    public Membership getMembershipStatus(HttpSession session) {

        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            logger.warn("Unauthorized status check attempt — no user in session");
            return null;
        }

        logger.debug("Fetching membership status — user={}",
                EmailMasker.mask(user.getUsername()));

        return membershipService
                .findByUsername(user.getUsername())
                .orElse(null);
    }



    // ============================================================
    //  POST — Confirm subscription (link user ↔ subscription)
    // ============================================================
    @PostMapping("/confirm")
    @ResponseBody
    public ResponseEntity<?> confirmSubscription(
            @RequestBody Map<String, String> payload,
            HttpSession session
    ) {

        logger.debug("POST /membership/confirm called with payload={}", payload);

        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            logger.warn("confirmSubscription failed — no loggedInUser in session");
            return ResponseEntity.status(401).body("Not logged in");
        }

        String username = user.getUsername();
        String maskedUser = EmailMasker.mask(username);

        String subscriptionId = payload.get("subscriptionID");
        String planId = payload.get("planID");

        logger.info("Attempting to link subscription — user={}, subscriptionId={}, planId={}",
                maskedUser,
                subscriptionId,
                planId);

        Optional<Membership> existingOpt = membershipService.findByUsername(username);

        if (existingOpt.isPresent()) {
            Membership existing = existingOpt.get();
            String status = existing.getStatus();

            // Prevent creating a new subscription if PENDING or ACTIVE
            if ("ACTIVE".equalsIgnoreCase(status) || "PENDING".equalsIgnoreCase(status)) {

                logger.warn("Subscription linking blocked — user={}, existingStatus={}",
                        maskedUser, status);

                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body("You already have an active or pending membership.");
            }
        }

        // LINK membership
        membershipService.createOrUpdatePendingMembership(
                username,
                planId,
                subscriptionId
        );

        logger.info("Subscription successfully linked — user={}, subscriptionId={}",
                maskedUser,
                subscriptionId);

        return ResponseEntity.ok("Linked");
    }
}

