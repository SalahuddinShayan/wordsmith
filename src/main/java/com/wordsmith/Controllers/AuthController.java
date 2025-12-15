package com.wordsmith.Controllers;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.wordsmith.Entity.User;
import com.wordsmith.Services.EmailService;
import com.wordsmith.Services.UserService;
import com.wordsmith.Util.EmailMasker;
import com.wordsmith.Util.OTPUtil;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserService userService;

    private EmailService emailService;

    public AuthController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    // -------------------------------
    // GET: Registration Page
    // -------------------------------
    @GetMapping("/register")
    public String showRegistrationForm(Model model, HttpSession session) {

        if (session.getAttribute("loggedInUser") != null) {
            log.info("[AUTH] Registration page access blocked. User already logged in.");
            return "redirect:/home";
        }

        log.info("[AUTH] Serving registration page");
        model.addAttribute("user", new User());
        return "register";
    }

    // -------------------------------
    // POST: Register User + Send OTP
    // -------------------------------
    @PostMapping("/register")
    public String registerUser(
            @RequestParam String email,
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam MultipartFile Pic,
            HttpSession session,
            Model model) throws IOException {

        log.info("[AUTH] Registration attempt email={}, username={}",
                EmailMasker.mask(email), EmailMasker.mask(username));

        if (userService.findByEmail(email) != null) {
            log.warn("[AUTH] Registration failed: Email already registered {}", EmailMasker.mask(email));
            model.addAttribute("error", "Email is already registered!");
            return "register";
        }

        if (userService.findByUsername(username) != null) {
            log.warn("[AUTH] Registration failed: Username unavailable {}", EmailMasker.mask(username));
            model.addAttribute("error", "Username is already taken!");
            return "register";
        }

        // Store temporary data
        session.setAttribute("tempUserEmail", email);
        session.setAttribute("tempUsername", username);
        session.setAttribute("tempPassword", password);
        byte[] compressedBytes = null;

        if (!Pic.isEmpty()) {
            compressedBytes = userService.compressImage(Pic);
        }
        session.setAttribute("tempProfilePicture", compressedBytes);

        // Create OTP
        String otp = OTPUtil.generateOTP();
        long otpTimestamp = System.currentTimeMillis();

        session.setAttribute("otp", otp);
        session.setAttribute("otpTimestamp", otpTimestamp);

        log.info("[AUTH] OTP generated & stored for email={} (OTP masked for security)",
                EmailMasker.mask(email));

        emailService.sendOtpEmail(email, otp);

        log.info("[AUTH] OTP email sent to {}", EmailMasker.mask(email));
        return "redirect:/auth/verify";
    }

    // -------------------------------
    // GET: Show OTP verification page
    // -------------------------------
    @GetMapping("/verify")
    public String showVerifyPage() {
        log.info("[AUTH] Serving OTP verification page");
        return "verify";
    }

    // -------------------------------
    // POST: Verify OTP & complete registration
    // -------------------------------
    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam String otp, HttpSession session, Model model) {

        String storedOtp = (String) session.getAttribute("otp");
        Long otpTimestamp = (Long) session.getAttribute("otpTimestamp");
        long now = System.currentTimeMillis();

        String email = (String) session.getAttribute("tempUserEmail");
        String username = (String) session.getAttribute("tempUsername");

        log.info("[AUTH] OTP verification attempt for email={}, username={}",
                EmailMasker.mask(email), EmailMasker.mask(username));

        if (storedOtp == null || otpTimestamp == null) {
            log.warn("[AUTH] OTP verification failed — OTP missing for {}", EmailMasker.mask(email));
            model.addAttribute("error", "No OTP found. Please request a new one.");
            return "verify";
        }

        long OTP_VALIDITY = 5 * 60 * 1000;

        if (now - otpTimestamp > OTP_VALIDITY) {
            log.warn("[AUTH] OTP expired for {}", EmailMasker.mask(email));

            session.removeAttribute("otp");
            session.removeAttribute("otpTimestamp");

            model.addAttribute("error", "OTP expired! Please request a new one.");
            return "verify";
        }

        if (!storedOtp.equals(otp)) {
            log.warn("[AUTH] Invalid OTP attempt for {}", EmailMasker.mask(email));
            model.addAttribute("error", "Invalid OTP! Please try again.");
            return "verify";
        }

        // Correct OTP → Register user
        try {
            userService.saveUser(
                    (String) session.getAttribute("tempUserEmail"),
                    (String) session.getAttribute("tempUsername"),
                    (String) session.getAttribute("tempPassword"),
                    (byte[]) session.getAttribute("tempProfilePicture"));

            log.info("[AUTH] Registration complete: email={}, username={}",
                    EmailMasker.mask(email), EmailMasker.mask(username));

        } catch (IOException ex) {
            log.error("[AUTH] Registration failed during saveUser: {}", ex.getMessage());
            model.addAttribute("error", "An error occurred while saving the user. Please try again.");
            return "verify";
        }

        // Cleanup
        session.removeAttribute("otp");
        session.removeAttribute("otpTimestamp");
        session.removeAttribute("tempUserEmail");
        session.removeAttribute("tempUsername");
        session.removeAttribute("tempPassword");

        model.addAttribute("success", "Registration successful! You can now log in.");
        return "login";
    }
}