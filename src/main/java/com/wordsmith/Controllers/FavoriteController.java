package com.wordsmith.Controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wordsmith.Entity.User;
import com.wordsmith.Services.FavoriteService;

import jakarta.servlet.http.HttpSession;

@Controller
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping("/toggle-favorite")
    @ResponseBody
    public Map<String, Object> toggleFavorite(
            @RequestParam Long novelId,
            HttpSession session) {

        Map<String, Object> response = new HashMap<>();

        var user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            response.put("status", "error");
            response.put("message", "User not logged in");
            return response;
        }

        boolean favorited = favoriteService.toggleFavorite(user.getUsername(), novelId);
        long count = favoriteService.getFavoriteCount(novelId);

        response.put("status", "success");
        response.put("favorited", favorited);
        response.put("count", count);

        return response;
    }
}

