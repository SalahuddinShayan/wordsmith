package com.wordsmith.Controllers;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wordsmith.Entity.User;
import com.wordsmith.Services.FavoriteService;
import com.wordsmith.Util.EmailMasker;

import jakarta.servlet.http.HttpSession;

@Controller
public class FavoriteController {

    private static final Logger logger = LoggerFactory.getLogger(FavoriteController.class);

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
        logger.info("FavoriteController initialized");
    }

    @PostMapping("/toggle-favorite")
    @ResponseBody
    public Map<String, Object> toggleFavorite(
            @RequestParam Long novelId,
            HttpSession session) {

        Map<String, Object> response = new HashMap<>();

        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            logger.warn("POST /toggle-favorite blocked - user not logged in");
            response.put("status", "error");
            response.put("message", "User not logged in");
            return response;
        }

        String maskedUser = EmailMasker.mask(user.getUsername());

        logger.info("POST /toggle-favorite — user={} toggling favorite for novelId={}", 
                     maskedUser, novelId);

        try {
            boolean favorited = favoriteService.toggleFavorite(user.getUsername(), novelId);
            long count = favoriteService.getFavoriteCount(novelId);

            response.put("status", "success");
            response.put("favorited", favorited);
            response.put("count", count);

            logger.debug("Favorite toggle result — user={}, novelId={}, favorited={}, totalFavorites={}",
                         maskedUser, novelId, favorited, count);

        } catch (Exception e) {
            logger.error("Error toggling favorite — user={}, novelId={}", maskedUser, novelId, e);

            response.put("status", "error");
            response.put("message", "Something went wrong, please try again");
        }

        return response;
    }
}
