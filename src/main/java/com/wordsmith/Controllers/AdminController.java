package com.wordsmith.Controllers;

import com.wordsmith.Entity.Chapter;
import com.wordsmith.Services.AdminService;
import com.wordsmith.Services.ChapterPurchaseService;
import com.wordsmith.Services.PaymentTransactionService;
import com.wordsmith.Services.WalletService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;
    private final PaymentTransactionService paymentService;
    private final WalletService walletService;
    private final ChapterPurchaseService chapterPurchaseService;

    public AdminController(AdminService adminService, PaymentTransactionService paymentService, WalletService walletService, ChapterPurchaseService chapterPurchaseService) {
        this.adminService = adminService;
        this.paymentService = paymentService;
        this.walletService = walletService;
        this.chapterPurchaseService = chapterPurchaseService;
    }

    // ============================================================
    // Dashboard
    // ============================================================
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        adminService.populateDashboard(model);
        return "dashboard";
    }

    // ============================================================
    // Memberships
    // ============================================================
    @GetMapping("/memberships")
    public String memberships(
            @RequestParam(required = false) String status,
            Model model
    ) {
        adminService.populateMemberships(status, model);
        return "memberships";
    }

    @PostMapping("/membership/resolve")
    public String resolveMembership(@RequestParam String subscriptionId) {
        adminService.resolveMembership(subscriptionId);
        return "redirect:/admin/memberships";
    }

    // ============================================================
    // Transactions
    // ============================================================
    @GetMapping("/transactions")
    public String transactions(Model model) {
        adminService.populateTransactions(model);
        return "transactions";
    }

    // ============================================================
    // Webhook failures
    // ============================================================
    @GetMapping("/webhook-failures")
    public String webhookFailures(Model model) {
        adminService.populateWebhookFailures(model);
        return "webhook-failures";
    }


    @GetMapping("/webhook-events")
    public String webhookEvents(Model model) {

        model.addAttribute("events", adminService.getRecentWebhookEvents());

        return "webhook-events";
    }

    @PostMapping("/transactions/recover")
    public String recoverTransaction(
            @RequestParam String subscriptionId,
            RedirectAttributes redirectAttributes
    ) {
        try {
            paymentService.recoverFromPayPal(subscriptionId);
            redirectAttributes.addFlashAttribute(
                    "success",
                    "Transaction recovered successfully."
            );
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute(
                    "error",
                    "Recovery failed: " + ex.getMessage()
            );
        }

        return "redirect:/admin/webhook-failures";
    }

    @GetMapping("/chapter-purchases")
    public String chapterPurchases(Model model) {
        model.addAttribute("purchases", chapterPurchaseService.findAllPurchases());
        return "chapter-purchases";
    }

    @GetMapping("/wallets")
    public String wallets(Model model) {
        model.addAttribute("wallets", walletService.getAllWallets());
        return "wallets";
    }

    @GetMapping("/coin-ledgers")
    public String coinLedgers(Model model) {
        model.addAttribute("ledgers", walletService.getAllLedgers());
        return "coin-ledgers";
    }
}

