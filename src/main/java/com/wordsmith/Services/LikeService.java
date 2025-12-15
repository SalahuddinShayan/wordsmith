package com.wordsmith.Services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.wordsmith.Entity.Comment;
import com.wordsmith.Entity.Likes;
import com.wordsmith.Enum.CommentEntityType;
import com.wordsmith.Enum.LikeEnum;
import com.wordsmith.Repositories.ChapterRepository;
import com.wordsmith.Repositories.CommentRepository;
import com.wordsmith.Repositories.LikeRepository;
import com.wordsmith.Repositories.NovelRepository;
import com.wordsmith.Util.EmailMasker;

@Service
public class LikeService {

    private static final Logger log = LoggerFactory.getLogger(LikeService.class);

    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;
    private final ChapterRepository chapterRepository;
    private final NovelRepository novelRepository;

    public LikeService(LikeRepository likeRepository,
                       CommentRepository commentRepository,
                       ChapterRepository chapterRepository,
                       NovelRepository novelRepository) {

        this.likeRepository = likeRepository;
        this.commentRepository = commentRepository;
        this.chapterRepository = chapterRepository;
        this.novelRepository = novelRepository;
    }

    /** ---------------------------------------------------------
     * Save or update a reaction
     * --------------------------------------------------------- */
    public void saveLike(String entityType, long entityId, String username, String likeStatus) {

        String maskedUser = EmailMasker.mask(username);

        log.info("REACTION_SAVE_ATTEMPT user={} entityType={} entityId={} status={}",
                maskedUser, entityType, entityId, likeStatus);

        try {
            CommentEntityType type = CommentEntityType.valueOf(entityType.toUpperCase());
            Likes existingLike = likeRepository.findByEntityTypeAndEntityIdAndUsername(type, entityId, username);

            // Removing reaction
            if ("NONE".equalsIgnoreCase(likeStatus)) {
                if (existingLike != null) {
                    likeRepository.delete(existingLike);

                    log.info("REACTION_REMOVED user={} entityType={} entityId={}",
                            maskedUser, entityType, entityId);
                }
                return;
            }

            // Creating new reaction
            if (existingLike == null) {
                existingLike = new Likes();
                existingLike.setEntityType(type);
                existingLike.setEntityId(entityId);
                existingLike.setUsername(username);

                log.debug("NEW_REACTION_ENTRY user={} entityType={} entityId={}",
                        maskedUser, entityType, entityId);
            }

            // Updating the reaction choice
            existingLike.setLikeStatus(LikeEnum.valueOf(likeStatus.toUpperCase()));
            likeRepository.save(existingLike);

            log.info("REACTION_SAVED user={} entityType={} entityId={} newStatus={}",
                    maskedUser, entityType, entityId, likeStatus);

        } catch (Exception ex) {
            log.error("REACTION_SAVE_ERROR user={} entityType={} entityId={} error={}",
                    maskedUser, entityType, entityId, ex.getMessage());
            throw ex;
        }
    }

    /** ---------------------------------------------------------
     * Count likes
     * --------------------------------------------------------- */
    public long getLikesCount(long entityId, String entityType) {
        return likeRepository.countByEntityIdAndEntityTypeAndLikeStatus(
                entityId, CommentEntityType.valueOf(entityType.toUpperCase()), LikeEnum.LIKE
        );
    }

    /** ---------------------------------------------------------
     * Count dislikes
     * --------------------------------------------------------- */
    public long getDislikesCount(long entityId, String entityType) {
        return likeRepository.countByEntityIdAndEntityTypeAndLikeStatus(
                entityId, CommentEntityType.valueOf(entityType.toUpperCase()), LikeEnum.DISLIKE
        );
    }

    /** ---------------------------------------------------------
     * Get the user's current reaction
     * --------------------------------------------------------- */
    public LikeEnum getUserReaction(String entityType, long entityId, String username) {
        String maskedUser = EmailMasker.mask(username);
        LikeEnum reaction = null;

        try {
            Likes existingLike =
                    likeRepository.findByEntityTypeAndEntityIdAndUsername(
                            CommentEntityType.valueOf(entityType.toUpperCase()),
                            entityId,
                            username
                    );

            reaction = (existingLike != null ? existingLike.getLikeStatus() : null);

            log.debug("REACTION_FETCH user={} entityType={} entityId={} reaction={}",
                    maskedUser, entityType, entityId, reaction);

            return reaction;

        } catch (Exception ex) {
            log.error("REACTION_FETCH_ERROR user={} entityType={} entityId={} error={}",
                    maskedUser, entityType, entityId, ex.getMessage());
            throw ex;
        }
    }

    /** ---------------------------------------------------------
     * Count expressive reactions (LOVE, ANGRY, LAUGH, etc.)
     * --------------------------------------------------------- */
    public long getReactionCount(long entityId, String entityType, LikeEnum reaction) {
        return likeRepository.countByEntityIdAndEntityTypeAndLikeStatus(
                entityId, CommentEntityType.valueOf(entityType.toUpperCase()), reaction
        );
    }

    /** ---------------------------------------------------------
     * List of comments the user has liked/disliked
     * --------------------------------------------------------- */
    public List<Comment> getUserLikedOrDislikedComments(String username) {
        String maskedUser = EmailMasker.mask(username);

        log.info("REACTION_COMMENT_LIST_REQUEST user={}", maskedUser);

        List<Likes> likes =
                likeRepository.findByUsernameAndEntityTypeAndLikeStatusIn(
                        username,
                        CommentEntityType.COMMENT,
                        List.of(LikeEnum.LIKE, LikeEnum.DISLIKE)
                );

        List<Comment> comments = new ArrayList<>();

        try {
            for (Likes like : likes) {

                Comment comment = commentRepository.findById(like.getEntityId()).orElse(null);
                if (comment != null) {

                    // attach reaction
                    comment.setUserReaction(like.getLikeStatus());

                    // enrich comment with associations
                    if (comment.getEntityType() == CommentEntityType.CHAPTER) {
                        comment.setChapter(chapterRepository.findById(comment.getEntityId()).orElse(null));
                    } else if (comment.getEntityType() == CommentEntityType.NOVEL) {
                        comment.setNovel(novelRepository.findById(comment.getEntityId().intValue()).orElse(null));
                    } else if (comment.getEntityType() == CommentEntityType.COMMENT) {
                        Comment parent = commentRepository.findById(comment.getEntityId()).orElse(null);
                        comment.setParentComment(parent);

                        if (parent != null && parent.getEntityType() == CommentEntityType.CHAPTER) {
                            parent.setChapter(chapterRepository.findById(parent.getEntityId()).orElse(null));
                        } else if (parent != null && parent.getEntityType() == CommentEntityType.NOVEL) {
                            parent.setNovel(novelRepository.findById(parent.getEntityId().intValue()).orElse(null));
                        }
                    }

                    comments.add(comment);
                }
            }

            log.info("REACTION_COMMENT_LIST_SUCCESS user={} total={}", maskedUser, comments.size());

        } catch (Exception ex) {
            log.error("REACTION_COMMENT_LIST_ERROR user={} error={}", maskedUser, ex.getMessage());
        }

        return comments;
    }

    public List<Likes> getAllReactions() {
        return likeRepository.findAll();
    }
}
