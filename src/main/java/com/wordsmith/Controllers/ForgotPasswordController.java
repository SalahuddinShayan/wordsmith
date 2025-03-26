package com.wordsmith.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.wordsmith.Entity.User;
import com.wordsmith.Services.EmailService;
import com.wordsmith.Services.UserService;
import com.wordsmith.Util.OTPUtil;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class ForgotPasswordController {
    private final UserService userService;
    private final EmailService emailService;

    public ForgotPasswordController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    // Step 1: Show Forgot Password Page
    @GetMapping("/forgot-password")
    public String showForgotPasswordPage() {
        return "forgot-password";
    }

    // Step 2: Handle Forgot Password Request
    @PostMapping("/forgot-password")
    public String sendOtpForPasswordReset(@RequestParam String email, HttpSession session, Model model) {
        User user = userService.findByEmail(email);
        if (user == null) {
            model.addAttribute("error", "No account found with this email.");
            return "forgot-password";
        }

        // Generate OTP
        String otp = OTPUtil.generateOTP();
        long otpTimestamp = System.currentTimeMillis();

        // Store OTP in session with timestamp
        session.setAttribute("resetOtp", otp);
        session.setAttribute("resetOtpTimestamp", otpTimestamp);
        session.setAttribute("resetEmail", email);

        // Send OTP via email
        emailService.sendOtpEmail(email, otp);
        
        return "redirect:/auth/verify-reset-otp";
    }

    // Step 3: Show OTP Verification Page
    @GetMapping("/verify-reset-otp")
    public String showOtpVerificationPage() {
        return "verify-reset-otp";
    }

    // Step 4: Verify OTP
    @PostMapping("/verify-reset-otp")
    public String verifyOtp(@RequestParam String otp, HttpSession session, Model model) {
        String storedOtp = (String) session.getAttribute("resetOtp");
        Long otpTimestamp = (Long) session.getAttribute("resetOtpTimestamp");
        long currentTime = System.currentTimeMillis();

        if (storedOtp == null || otpTimestamp == null) {
            model.addAttribute("error", "No OTP found. Please request a new one.");
            return "verify-reset-otp";
        }

        long otpExpiryTime = 5 * 60 * 1000; // 5 minutes
        if (currentTime - otpTimestamp > otpExpiryTime) {
            session.removeAttribute("resetOtp");
            session.removeAttribute("resetOtpTimestamp");
            model.addAttribute("error", "OTP expired! Please request a new one.");
            return "verify-reset-otp";
        }

        if (storedOtp.equals(otp)) {
            return "redirect:/auth/reset-password";
        } else {
            model.addAttribute("error", "Invalid OTP! Please try again.");
            return "verify-reset-otp";
        }
    }

    // Step 5: Show Reset Password Page
    @GetMapping("/reset-password")
    public String showResetPasswordPage() {
        return "reset-password";
    }

    // Step 6: Handle Password Reset
    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String password, HttpSession session, Model model) {
        String email = (String) session.getAttribute("resetEmail");
        if (email == null) {
            model.addAttribute("error", "Session expired. Please request a new password reset.");
            return "forgot-password";
        }

        userService.updatePassword(email, password);

        session.removeAttribute("resetOtp");
        session.removeAttribute("resetOtpTimestamp");
        session.removeAttribute("resetEmail");

        model.addAttribute("success", "Password reset successful! You can now log in.");
        return "login";
    }
}