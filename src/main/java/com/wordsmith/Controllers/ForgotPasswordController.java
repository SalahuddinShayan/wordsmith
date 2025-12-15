package com.wordsmith.Controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.wordsmith.Entity.User;
import com.wordsmith.Services.EmailService;
import com.wordsmith.Services.UserService;
import com.wordsmith.Util.EmailMasker;
import com.wordsmith.Util.OTPUtil;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class ForgotPasswordController {

    private static final Logger logger = LoggerFactory.getLogger(ForgotPasswordController.class);

    private final UserService userService;
    private final EmailService emailService;

    public ForgotPasswordController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
        logger.info("ForgotPasswordController initialized");
    }

    // Step 1: Show Forgot Password Page
    @GetMapping("/forgot-password")
    public String showForgotPasswordPage() {
        logger.debug("GET /auth/forgot-password — displaying forgot-password page");
        return "forgot-password";
    }

    // Step 2: Handle Forgot Password Request
    @PostMapping("/forgot-password")
    public String sendOtpForPasswordReset(@RequestParam String email, HttpSession session, Model model) {

        String maskedEmail = EmailMasker.mask(email);
        logger.info("POST /auth/forgot-password — reset request for email={}", maskedEmail);

        User user = userService.findByEmail(email);

        if (user == null) {
            logger.warn("Password reset attempted for unknown email={}", maskedEmail);
            model.addAttribute("error", "No account found with this email.");
            return "forgot-password";
        }

        // Generate and store OTP
        String otp = OTPUtil.generateOTP();
        long otpTimestamp = System.currentTimeMillis();

        session.setAttribute("resetOtp", otp);
        session.setAttribute("resetOtpTimestamp", otpTimestamp);
        session.setAttribute("resetEmail", email);

        logger.debug("Generated OTP for email={} (OTP stored securely in session)", maskedEmail);

        emailService.sendOtpEmail(email, otp);
        logger.info("OTP email sent for password reset to {}", maskedEmail);

        return "redirect:/auth/verify-reset-otp";
    }

    // Step 3: Show OTP Verification Page
    @GetMapping("/verify-reset-otp")
    public String showOtpVerificationPage() {
        logger.debug("GET /auth/verify-reset-otp — showing OTP verification page");
        return "verify-reset-otp";
    }

    // Step 4: Verify OTP
    @PostMapping("/verify-reset-otp")
    public String verifyOtp(@RequestParam String otp, HttpSession session, Model model) {

        String storedOtp = (String) session.getAttribute("resetOtp");
        Long otpTimestamp = (Long) session.getAttribute("resetOtpTimestamp");

        if (storedOtp == null || otpTimestamp == null) {
            logger.warn("OTP verification attempt without stored OTP (session expired?)");
            model.addAttribute("error", "No OTP found. Please request a new one.");
            return "verify-reset-otp";
        }

        long currentTime = System.currentTimeMillis();
        long otpExpiryTime = 5 * 60 * 1000; // 5 minutes

        if (currentTime - otpTimestamp > otpExpiryTime) {
            logger.warn("Expired OTP used during password reset");
            session.removeAttribute("resetOtp");
            session.removeAttribute("resetOtpTimestamp");
            model.addAttribute("error", "OTP expired! Please request a new one.");
            return "verify-reset-otp";
        }

        if (storedOtp.equals(otp)) {
            logger.info("OTP verification successful — proceeding to password reset page");
            return "redirect:/auth/reset-password";
        } else {
            logger.warn("Invalid OTP entered during password reset attempt");
            model.addAttribute("error", "Invalid OTP! Please try again.");
            return "verify-reset-otp";
        }
    }

    // Step 5: Show Reset Password Page
    @GetMapping("/reset-password")
    public String showResetPasswordPage() {
        logger.debug("GET /auth/reset-password — showing reset-password form");
        return "reset-password";
    }

    // Step 6: Handle Password Reset
    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String password, HttpSession session, Model model) {

        String email = (String) session.getAttribute("resetEmail");

        if (email == null) {
            logger.warn("Password reset attempt without valid session email");
            model.addAttribute("error", "Session expired. Please request a new password reset.");
            return "forgot-password";
        }

        String maskedEmail = EmailMasker.mask(email);
        logger.info("POST /auth/reset-password — resetting password for email={}", maskedEmail);

        userService.updatePassword(email, password);

        session.removeAttribute("resetOtp");
        session.removeAttribute("resetOtpTimestamp");
        session.removeAttribute("resetEmail");

        logger.info("Password reset successful for {}", maskedEmail);

        model.addAttribute("success", "Password reset successful! You can now log in.");
        return "login";
    }
}
