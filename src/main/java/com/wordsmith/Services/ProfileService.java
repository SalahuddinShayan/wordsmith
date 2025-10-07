package com.wordsmith.Services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    private final FavoriteService favoriteService;
    private final CommentService commentService;
    private final LikeService likeService;

    public ProfileService(FavoriteService favoriteService,
                          CommentService commentService,
                          LikeService likeService) {
        this.favoriteService = favoriteService;
        this.commentService = commentService;
        this.likeService = likeService;
    }

    public Map<String, Object> getProfileData(String username) {
        Map<String, Object> data = new HashMap<>();

        data.put("favorites", favoriteService.getUserFavoriteNovels(username));
        data.put("comments", commentService.getUserCommentsExcludingReplies(username));
        data.put("replies", commentService.getUserReplies(username));
        data.put("likedComments", likeService.getUserLikedOrDislikedComments(username));
        System.out.println("likes worked");

        return data;
    }
}

