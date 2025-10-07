package com.wordsmith.Controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wordsmith.Entity.User;
import com.wordsmith.Enum.CommentEntityType;
import com.wordsmith.Enum.LikeEnum;
import com.wordsmith.Services.LikeService;

import jakarta.servlet.http.HttpSession;

@Controller
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/save-like")
    @ResponseBody
    public Map<String, Object> saveLike(
            @RequestParam String entityType,
            @RequestParam long entityId,
            @RequestParam String likeStatus,
            HttpSession session
    ) {

        Map<String, Object> response = new HashMap<>();

        // 🔒 Ensure user is logged in
        var user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            response.put("status", "error");
            response.put("message", "User not logged in");
            return response;
        }

        // ✅ Save or update reaction
        likeService.saveLike(entityType, entityId, user.getUsername(), likeStatus);

        // ✅ If the entity is a COMMENT → only return like/dislike
        if (CommentEntityType.valueOf(entityType.toUpperCase()) == CommentEntityType.COMMENT) {
            long likes = likeService.getLikesCount(entityId, entityType);
            long dislikes = likeService.getDislikesCount(entityId, entityType);

            response.put("status", "success");
            response.put("likes", likes);
            response.put("dislikes", dislikes);
        }
        // ✅ If it's CHAPTER (or NOVEL later) → return all 6 reactions
        else {
            Map<String, Long> reactionCounts = new HashMap<>();
            for (LikeEnum reaction : LikeEnum.values()) {
                // Skip LIKE/DISLIKE for chapters if you only want the 6 expressive ones
                if (reaction == LikeEnum.LIKE || reaction == LikeEnum.DISLIKE) continue;

                long count = likeService.getReactionCount(entityId, entityType, reaction);
                reactionCounts.put(reaction.name().toLowerCase(), count);
            }

            response.put("status", "success");
            response.put("reactions", reactionCounts);
        }

        return response;
    }
}
