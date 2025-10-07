package com.wordsmith.Controllers;

import java.io.IOException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.wordsmith.Entity.User;
import com.wordsmith.Services.ProfileService;
import com.wordsmith.Services.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class ProfileController {

    private final UserService userService;
    private final ProfileService profileService;


    public ProfileController(UserService userService, ProfileService profileService) {
        this.userService = userService;
        this.profileService = profileService;
    }

        @GetMapping("/profile")
    public String profilePage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/auth/loginpage"; // Redirect to login if not logged in
        }

        model.addAttribute("user", user);
        model.addAllAttributes(profileService.getProfileData(user.getUsername()));

        return "profile"; // JSP page
    }

@GetMapping("/user-image/{username}")
	@ResponseBody
	void showImage(@PathVariable("username") String username, HttpServletResponse response, User user)
			throws ServletException, IOException {
		user = userService.findByUsername(username);
		response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
        byte[] image = user.getProfilePicture();
		if (image != null && image.length > 0) {
		response.getOutputStream().write(image);}
		response.getOutputStream().flush();
	}

    @PostMapping("/ChangeProfilePicture")
    public String ChangeProfilePicture(@RequestParam String UserName, @RequestParam MultipartFile Pic) throws IllegalStateException, IOException {
        userService.updateProfilePicture(UserName, Pic);
        
        return "redirect:/profile";
    }
    


}
