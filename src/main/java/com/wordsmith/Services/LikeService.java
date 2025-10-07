package com.wordsmith.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.wordsmith.Entity.Comment;
import com.wordsmith.Entity.Likes;
import com.wordsmith.Enum.CommentEntityType;
import com.wordsmith.Enum.LikeEnum;
import com.wordsmith.Repositories.ChapterRepository;
import com.wordsmith.Repositories.CommentRepository;
import com.wordsmith.Repositories.LikeRepository;
import com.wordsmith.Repositories.NovelRepository;

@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;
    private final ChapterRepository chapterRepository;
    private final NovelRepository novelRepository;

    public LikeService(LikeRepository likeRepository, CommentRepository commentRepository, ChapterRepository chapterRepository, NovelRepository novelRepository) {
        this.likeRepository = likeRepository;
        this.commentRepository = commentRepository;
        this.chapterRepository = chapterRepository;
        this.novelRepository = novelRepository;
    }

    public void saveLike(String entityType, long entityId, String username, String likeStatus) {
        var existingLike = likeRepository.findByEntityTypeAndEntityIdAndUsername(CommentEntityType.valueOf(entityType.toUpperCase()), entityId, username);
        if ("NONE".equalsIgnoreCase(likeStatus)) {
            // ✅ User removed their reaction → delete row
            if (existingLike != null) {
                likeRepository.delete(existingLike);
            }
            return;
        }

        if (existingLike == null) {
            existingLike = new Likes();
            existingLike.setEntityType(CommentEntityType.valueOf(entityType.toUpperCase()));
            existingLike.setEntityId(entityId);
            existingLike.setUsername(username);
        }

        existingLike.setLikeStatus(LikeEnum.valueOf(likeStatus.toUpperCase()));
        likeRepository.save(existingLike);
    }

    public long getLikesCount(long entityId, String entityType) {
        return likeRepository.countByEntityIdAndEntityTypeAndLikeStatus(entityId, CommentEntityType.valueOf(entityType.toUpperCase()), LikeEnum.LIKE);
    }

    public long getDislikesCount(long entityId, String entityType) {
        return likeRepository.countByEntityIdAndEntityTypeAndLikeStatus(entityId, CommentEntityType.valueOf(entityType.toUpperCase()), LikeEnum.DISLIKE);
    }

    public LikeEnum getUserReaction(String entityType, long entityId, String username) {
        var existingLike = likeRepository.findByEntityTypeAndEntityIdAndUsername(CommentEntityType.valueOf(entityType.toUpperCase()), entityId, username);
        return existingLike != null ? existingLike.getLikeStatus() : null;
    }

    // For chapters (flexible, supports LOVE/ANGRY/LAUGH/etc.)
    public long getReactionCount(long entityId, String entityType, LikeEnum reaction) {
        return likeRepository.countByEntityIdAndEntityTypeAndLikeStatus(entityId, CommentEntityType.valueOf(entityType.toUpperCase()), reaction);
    }

    public List<Comment> getUserLikedOrDislikedComments(String username) {
    List<Likes> likes = likeRepository.findByUsernameAndEntityTypeAndLikeStatusIn(username, CommentEntityType.COMMENT, List.of(LikeEnum.LIKE, LikeEnum.DISLIKE));

    List<Comment> comments = new ArrayList<>();
    int n = 0;
    for (Likes like : likes) {
        Comment comment = commentRepository.findById(like.getEntityId()).orElse(null);
        comment.setUserReaction(like.getLikeStatus());
        if (comment != null) {
            if(comment.getEntityType() == CommentEntityType.CHAPTER){
                comment.setChapter(chapterRepository.findById(comment.getEntityId()).orElse(null));
            } else if (comment.getEntityType() == CommentEntityType.NOVEL) {
                comment.setNovel(novelRepository.findById(comment.getEntityId() != null ? comment.getEntityId().intValue() : null).orElse(null));
            } else if (comment.getEntityType() == CommentEntityType.COMMENT) {
                comment.setParentComment(commentRepository.findById(comment.getEntityId()).orElse(null));
                if(comment.getParentComment() != null && comment.getParentComment().getEntityType() == CommentEntityType.CHAPTER){
                    comment.getParentComment().setChapter(chapterRepository.findById(comment.getParentComment().getEntityId()).orElse(null));
                } else if (comment.getParentComment() != null && comment.getParentComment().getEntityType() == CommentEntityType.NOVEL) {
                    comment.getParentComment().setNovel(novelRepository.findById(comment.getParentComment().getEntityId() != null ? comment.getParentComment().getEntityId().intValue() : null).orElse(null));
                }    
            }
            comments.add(comment);
            n++;
            System.out.println("like loop " + n);
            System.out.println(comments.size());

        }
    }
    System.out.println(comments.size());
    return comments;
    }

}
