package com.wordsmith.Controllers;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.wordsmith.Entity.ChapterPurchase;
import com.wordsmith.Entity.CoinLedger;
import com.wordsmith.Entity.Membership;
import com.wordsmith.Entity.PaymentTransaction;
import com.wordsmith.Entity.User;
import com.wordsmith.Services.ChapterPurchaseService;
import com.wordsmith.Services.MembershipService;
import com.wordsmith.Services.PaymentTransactionService;
import com.wordsmith.Services.ProfileService;
import com.wordsmith.Services.UserService;
import com.wordsmith.Util.EmailMasker;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ProfileController {

    private static final Logger log = LoggerFactory.getLogger(ProfileController.class);

    private final UserService userService;
    private final ProfileService profileService;
    private final MembershipService membershipService;
    private final PaymentTransactionService paymentService;
    private final ChapterPurchaseService chapterPurchaseService;

    public ProfileController(UserService userService, ProfileService profileService,  MembershipService membershipService,  PaymentTransactionService paymentService,
                             ChapterPurchaseService chapterPurchaseService) {

        this.userService = userService;
        this.profileService = profileService;
        this.membershipService = membershipService;
        this.paymentService = paymentService;
        this.chapterPurchaseService = chapterPurchaseService;
    }

    // ======================================================
    // GET PROFILE PAGE
    // ======================================================
    @GetMapping("/profile")
    public String profilePage(HttpSession session, Model model) {

        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            log.warn("PROFILE ACCESS DENIED — No logged-in user in session");
            return "redirect:/auth/loginpage";
        }

        String username = user.getUsername();
        log.info("PROFILE VIEW — user={}", username);

        Optional<Membership> top = membershipService.findByUsername(username);

        // 2️⃣ Full history (expired, cancelled, suspended)
        List<Membership> history = membershipService.findMembershipHistory(user.getUsername());

        model.addAttribute("user", user);
        model.addAllAttributes(profileService.getProfileData(username));
        model.addAttribute("topMembership", top.orElse(null));
        model.addAttribute("membershipHistory", history);

        return "profile";
    }

    // ======================================================
    // SERVE PROFILE IMAGE
    // ======================================================
    @GetMapping("/user-image/{username}")
    @ResponseBody
    void showImage(@PathVariable("username") String username,
                   HttpServletResponse response,
                   User user) throws ServletException, IOException {

        log.info("PROFILE IMAGE REQUEST — username={}", username);

        user = userService.findByUsername(username);

        if (user == null) {
            log.warn("PROFILE IMAGE NOT FOUND — username={}", username);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        byte[] image = user.getProfilePicture();
        response.setContentType("image/jpeg");

        if (image != null && image.length > 0) {
            response.getOutputStream().write(image);
        }

        response.getOutputStream().flush();
    }

    // ======================================================
    // CHANGE PROFILE PICTURE
    // ======================================================
    @PostMapping("/ChangeProfilePicture")
    public String ChangeProfilePicture(@RequestParam String UserName,
                                       @RequestParam MultipartFile Pic)
            throws IOException {

        log.info("PROFILE PICTURE UPDATE — username={}, fileSize={}",
                UserName, Pic.getSize());

        userService.updateProfilePicture(UserName, Pic);

        log.info("PROFILE PICTURE UPDATED — username={}", UserName);

        return "redirect:/profile";
    }

    @GetMapping("/profile/purchases")
    public String profilePurchasesPage(HttpSession session, Model model) {

        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            log.warn("PROFILE PURCHASES ACCESS DENIED — No logged-in user in session");
            return "redirect:/auth/loginpage";
        }

        String username = user.getUsername();
        log.info("PROFILE PURCHASES VIEW — user={}", username);

        List<ChapterPurchase> purchases = chapterPurchaseService.findPurchasesByUsername(username);

        model.addAttribute("user", user);
        model.addAttribute("purchases", purchases);

        return "profile-purchases";
    }

    @GetMapping("/profile/coin-ledger")
    public String profileCoinsLedgerPage(HttpSession session, Model model) {

        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            log.warn("PROFILE COINS LEDGER ACCESS DENIED — No logged-in user in session");
            return "redirect:/auth/loginpage";
        }

        String username = user.getUsername();
        log.info("PROFILE COINS LEDGER VIEW — user={}", username);

        model.addAttribute("coinLedger", profileService.getCoinLedger(username));

        return "profile-coins-ledger";
    }
}