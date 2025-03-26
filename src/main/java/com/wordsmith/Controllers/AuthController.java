package com.wordsmith.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wordsmith.Entity.User;
import com.wordsmith.Services.EmailService;
import com.wordsmith.Services.UserService;
import com.wordsmith.Util.OTPUtil;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    private EmailService emailService;
    
    public AuthController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model, HttpSession session) {
    	if (session.getAttribute("loggedInUser") != null) {
            return "redirect:/home"; // Redirect logged-in users
        }
    	
    	model.addAttribute("user", new User());
        return "register"; // Returns register.jsp
    }

 // Step 1: Register user & send OTP
    @PostMapping("/register")
    public String registerUser(@RequestParam String email, @RequestParam String username, 
                               @RequestParam String password, HttpSession session, Model model) {
        if (userService.findByEmail(email) != null) {
            model.addAttribute("error", "Email is already registered!");
            return "register"; // Stay on the register page
        }
        if (userService.findByUsername(username) != null) {
            model.addAttribute("error", "Username is already taken!");
            return "register"; // Stay on the register page
        }

        // Store user data temporarily in session
        session.setAttribute("tempUserEmail", email);
        session.setAttribute("tempUsername", username);
        session.setAttribute("tempPassword", password);

     // Generate OTP and store with timestamp
        String otp = OTPUtil.generateOTP();
        long otpTimestamp = System.currentTimeMillis(); // Store current time

        session.setAttribute("otp", otp);
        session.setAttribute("otpTimestamp", otpTimestamp); // Store OTP creation time

        emailService.sendOtpEmail(email, otp);

        return "redirect:/auth/verify"; // Redirect to OTP entry page
    }
    
    @GetMapping("/verify")
    public String showVerifyPage() {
        return "verify"; // Show the verify.jsp page
    }

 // Step 2: Verify OTP and complete registration
    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam String otp, HttpSession session, Model model) {
        String storedOtp = (String) session.getAttribute("otp");
        Long otpTimestamp = (Long) session.getAttribute("otpTimestamp");
        long currentTime = System.currentTimeMillis();

        if (storedOtp == null || otpTimestamp == null) {
            model.addAttribute("error", "No OTP found. Please request a new one.");
            return "verify";
        }

        long otpExpiryTime = 5 * 60 * 1000; // 5 minutes in milliseconds

        if (currentTime - otpTimestamp > otpExpiryTime) {
            session.removeAttribute("otp"); // Invalidate OTP
            session.removeAttribute("otpTimestamp");
            model.addAttribute("error", "OTP expired! Please request a new one.");
            return "verify";
        }

        if (storedOtp.equals(otp)) {
            // OTP is correct and within time limit → Register user
            userService.saveUser((String) session.getAttribute("tempUserEmail"),
                                 (String) session.getAttribute("tempUsername"),
                                 (String) session.getAttribute("tempPassword"));

            session.removeAttribute("otp");
            session.removeAttribute("otpTimestamp");
            session.removeAttribute("tempUserEmail");
            session.removeAttribute("tempUsername");
            session.removeAttribute("tempPassword");

            model.addAttribute("success", "Registration successful! You can now log in.");
            return "login";
        } else {
            model.addAttribute("error", "Invalid OTP! Please try again.");
            return "verify";
        }
    }
}