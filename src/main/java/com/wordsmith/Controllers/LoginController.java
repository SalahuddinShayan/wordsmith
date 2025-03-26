package com.wordsmith.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wordsmith.Entity.User;
import com.wordsmith.Enum.Role;
import com.wordsmith.Services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class LoginController {
    @Autowired
    private UserService userService;

    // ✅ Serve the login page when accessed via browser
    @GetMapping("/loginpage")
    public String showLoginPage(HttpServletRequest request, HttpSession session) {
    	if (session.getAttribute("loggedInUser") != null) {
            return "redirect:/home"; // Redirect logged-in users
        }
    	
    	String referer = request.getHeader("Referer");
        if (referer != null && !referer.contains("/auth/login")) {
            session.setAttribute("PRE_LOGIN_URL", referer);
        }
    	
        return "login"; // This will return login.jsp
    }
    	


    // ✅ Handle login form submission
    @PostMapping("/login")
    public String loginUser(@RequestParam String identifier, // Now accepts username or email
                            @RequestParam String password,
                            HttpSession session, Model model) {
        User user = userService.findByIdentifier(identifier);
        if (user != null && userService.authenticateUser(identifier, password)) {
            session.setAttribute("loggedInUser", user);
            session.setAttribute("userRole", user.getRole().toString());
            
            Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, 
            	    List.of(new SimpleGrantedAuthority(user.getRole().toString().toUpperCase())));

            	SecurityContextHolder.getContext().setAuthentication(authentication);
            	
            	session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
            

            // Redirect based on role
            if (user.getRole() == Role.ADMIN || user.getRole() == Role.TRANSLATOR || user.getRole() == Role.EDITOR) {
                return "redirect:/dashboard";
            } else {
                return "redirect:"+ session.getAttribute("PRE_LOGIN_URL");
            }
        } else {
            model.addAttribute("error", "Invalid username/email or password!");
            return "login"; // Show login page with error message
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpSession session) {
        session.invalidate(); // Clear session data
        String referer = request.getHeader("Referer");
        return "redirect:" + referer; // Redirect to login page
    }
}
