package com.wordsmith.Controllers;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wordsmith.Entity.User;
import com.wordsmith.Enum.CommentEntityType;
import com.wordsmith.Enum.LikeEnum;
import com.wordsmith.Services.LikeService;

import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;

@Controller
public class LikeController {

    private static final Logger logger = LoggerFactory.getLogger(LikeController.class);
    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
        logger.info("LikeController initialized");
    }

    @PostMapping("/save-like")
    @ResponseBody
    public Map<String, Object> saveLike(
            @RequestParam String entityType,
            @RequestParam long entityId,
            @RequestParam String likeStatus,
            HttpSession session
    ) {

        logger.debug("POST /save-like — entityType={}, entityId={}, likeStatus={}", 
                entityType, entityId, likeStatus);

        Map<String, Object> response = new HashMap<>();

        // 🔒 Ensure user is logged in
        var user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            logger.warn("Like attempt without login — entityType={}, entityId={}", entityType, entityId);

            response.put("status", "error");
            response.put("message", "User not logged in");
            return response;
        }

        logger.info("Saving like — user={}, entityType={}, entityId={}, likeStatus={}", 
                user.getUsername(), entityType, entityId, likeStatus);

        // ✅ Save or update reaction
        likeService.saveLike(entityType, entityId, user.getUsername(), likeStatus);

        // COMMENT entity → Only likes/dislikes matter
        if (CommentEntityType.valueOf(entityType.toUpperCase()) == CommentEntityType.COMMENT) {

            long likes = likeService.getLikesCount(entityId, entityType);
            long dislikes = likeService.getDislikesCount(entityId, entityType);

            logger.debug("COMMENT reaction updated — user={}, commentId={}, likes={}, dislikes={}",
                    user.getUsername(), entityId, likes, dislikes);

            response.put("status", "success");
            response.put("likes", likes);
            response.put("dislikes", dislikes);
        }
        // CHAPTER (or future NOVEL) → Return ALL 6 reactions
        else {

            Map<String, Long> reactionCounts = new HashMap<>();
            for (LikeEnum reaction : LikeEnum.values()) {

                // Skip LIKE/DISLIKE for multi-reaction entities
                if (reaction == LikeEnum.LIKE || reaction == LikeEnum.DISLIKE) continue;

                long count = likeService.getReactionCount(entityId, entityType, reaction);
                reactionCounts.put(reaction.name().toLowerCase(), count);
            }

            logger.debug("Multi-reaction entity updated — user={}, entity={}, reactionCounts={}",
                    user.getUsername(), entityId, reactionCounts);

            response.put("status", "success");
            response.put("reactions", reactionCounts);
        }

        logger.info("Like saved successfully — user={}, entityType={}, entityId={}",
                user.getUsername(), entityType, entityId);

        return response;
    }


    @GetMapping("/all-reactions")
    public String getAllReactions(Model model) {
        model.addAttribute("allReactions", likeService.getAllReactions());
        return "all-reactions";
    }
}
