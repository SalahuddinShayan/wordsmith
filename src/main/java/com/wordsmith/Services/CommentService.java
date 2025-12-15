package com.wordsmith.Services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import com.wordsmith.Entity.Comment;
import com.wordsmith.Enum.CommentEntityType;
import com.wordsmith.Repositories.ChapterRepository;
import com.wordsmith.Repositories.CommentRepository;
import com.wordsmith.Repositories.LikeRepository;
import com.wordsmith.Repositories.NovelRepository;

@Service
public class CommentService {

    private static final Logger log = LoggerFactory.getLogger(CommentService.class);

    private final CommentRepository commentRepository;
    private final ChapterRepository chapterRepository;
    private final NovelRepository novelRepository;
    private final LikeRepository likeRepository;

    public CommentService(CommentRepository commentRepository,
                          ChapterRepository chapterRepository,
                          NovelRepository novelRepository,
                          LikeRepository likeRepository) {
        this.commentRepository = commentRepository;
        this.chapterRepository = chapterRepository;
        this.novelRepository = novelRepository;
        this.likeRepository = likeRepository;
    }

    // ----------------------------------------------------
    // SAVE COMMENT
    // ----------------------------------------------------
    public Comment saveComment(Comment comment) {
        log.info("COMMENT_SAVE — id={}, entityType={}, entityId={}, username={}",
                comment.getId(),
                comment.getEntityType(),
                comment.getEntityId(),
                comment.getUserName()
        );

        try {
            Comment saved = commentRepository.save(comment);
            log.info("COMMENT_SAVE_SUCCESS — id={}", saved.getId());
            return saved;
        } catch (Exception ex) {
            log.error("COMMENT_SAVE_ERROR — id={}, error={}",
                    comment.getId(), ex.getMessage(), ex);
            throw ex;
        }
    }

    // ----------------------------------------------------
    // FETCH COMMENTS BY ENTITY
    // ----------------------------------------------------
    public List<Comment> getCommentsByEntity(CommentEntityType entityType, Long entityId) {

        log.info("COMMENT_FETCH_BY_ENTITY — entityType={}, entityId={}", entityType, entityId);

        List<Comment> list = commentRepository.findByEntityTypeAndEntityId(entityType, entityId);

        log.info("COMMENT_FETCH_BY_ENTITY_SUCCESS — entityType={}, entityId={}, count={}",
                entityType, entityId, list.size());

        return list;
    }

    // ----------------------------------------------------
    // FETCH A COMMENT BY ID
    // ----------------------------------------------------
    public Comment getCommentById(Long commentId) {

        log.info("COMMENT_FETCH_BY_ID — id={}", commentId);

        Comment c = commentRepository.findById(commentId).orElse(null);

        if (c == null) {
            log.warn("COMMENT_NOT_FOUND — id={}", commentId);
        } else {
            log.info("COMMENT_FETCH_SUCCESS — id={}", commentId);
        }

        return c;
    }

    // ----------------------------------------------------
    // DELETE COMMENT
    // ----------------------------------------------------
    public void deleteComment(Long commentId) {

        log.info("COMMENT_DELETE — id={}", commentId);

        try {
            Comment comment = commentRepository.findById(commentId).orElse(null);

            if (comment == null) {
                log.warn("COMMENT_DELETE_NOT_FOUND — id={}", commentId);
                return;
            }

            boolean hasReplies = comment.isHasReplies();
            log.info("COMMENT_DELETE — id={}, hasReplies={}", commentId, hasReplies);

            // Delete replies
            if (hasReplies) {
                commentRepository.deleteByEntityTypeAndEntityId(CommentEntityType.COMMENT, commentId);
                log.info("COMMENT_DELETE_REPLIES_SUCCESS — parentId={}", commentId);
            }

            // Delete likes on this comment
            likeRepository.deleteByEntityTypeAndEntityId(CommentEntityType.COMMENT, commentId);
            log.info("COMMENT_DELETE_LIKES_SUCCESS — id={}", commentId);

            // Delete actual comment
            commentRepository.deleteById(commentId);
            log.info("COMMENT_DELETE_SUCCESS — id={}", commentId);

        } catch (Exception ex) {
            log.error("COMMENT_DELETE_ERROR — id={}, error={}", commentId, ex.getMessage(), ex);
        }
    }

    // ----------------------------------------------------
    // FLAG COMMENT
    // ----------------------------------------------------
    public void flagComment(Long commentId) {

        log.info("COMMENT_FLAG — id={}", commentId);

        try {
            Comment comment = commentRepository.findById(commentId).orElse(null);

            if (comment == null) {
                log.warn("COMMENT_FLAG_NOT_FOUND — id={}", commentId);
                return;
            }

            comment.setFlagged(true);
            commentRepository.save(comment);

            log.info("COMMENT_FLAG_SUCCESS — id={}", commentId);

        } catch (Exception ex) {
            log.error("COMMENT_FLAG_ERROR — id={}, error={}", commentId, ex.getMessage(), ex);
        }
    }

    // ----------------------------------------------------
    // GET LAST COMMENT ID
    // ----------------------------------------------------
    public Long getLastCommentId() {
        log.info("COMMENT_FETCH_LAST_ID");

        Long id = commentRepository.findLastCommentId();

        log.info("COMMENT_FETCH_LAST_ID_SUCCESS — lastId={}", id);

        return id;
    }

    // ----------------------------------------------------
    // FIND ALL
    // ----------------------------------------------------
    public List<Comment> findallcomment() {

        log.info("COMMENT_FETCH_ALL");

        List<Comment> list = commentRepository.findAll();

        log.info("COMMENT_FETCH_ALL_SUCCESS — count={}", list.size());

        return list;
    }

    // ----------------------------------------------------
    // FETCH COMMENTS BY USER
    // ----------------------------------------------------
    public List<Comment> getCommentsByUser(String username) {

        log.info("COMMENT_FETCH_BY_USER — username={}", username);

        List<Comment> list = commentRepository.findByUserName(username);

        log.info("COMMENT_FETCH_BY_USER_SUCCESS — username={}, count={}", username, list.size());

        return list;
    }

    // ----------------------------------------------------
    // USER COMMENTS + REPLIES
    // ----------------------------------------------------
    public List<Comment> getUserComments(String username) {

        log.info("COMMENT_FETCH_USER_COMMENTS — username={}", username);

        List<Comment> comments = commentRepository.findByUserName(username);

        for (Comment c : comments) {
            if (c.isHasReplies()) {
                c.setReplies(getCommentsByEntity(CommentEntityType.COMMENT, c.getId()));
            }
        }

        log.info("COMMENT_FETCH_USER_COMMENTS_SUCCESS — username={}, count={}", username, comments.size());

        return comments;
    }

    // ----------------------------------------------------
    // FETCH REPLIES MADE BY USER
    // ----------------------------------------------------
    public List<Comment> getUserReplies(String username) {

        log.info("COMMENT_FETCH_USER_REPLIES — username={}", username);

        List<Comment> replies = commentRepository.findByUserNameAndEntityType(username, CommentEntityType.COMMENT);

        for (Comment reply : replies) {

            if (reply.getEntityId() != null) {
                Comment parent = getCommentById(reply.getEntityId());
                reply.setParentComment(parent);

                if (parent != null) {
                    if (parent.getEntityType() == CommentEntityType.CHAPTER) {
                        reply.setChapter(chapterRepository.findById(parent.getEntityId()).orElse(null));
                    } else if (parent.getEntityType() == CommentEntityType.NOVEL) {
                        reply.setNovel(novelRepository.findById(parent.getEntityId().intValue()).orElse(null));
                    }
                }
            }
        }

        log.info("COMMENT_FETCH_USER_REPLIES_SUCCESS — username={}, count={}", username, replies.size());

        return replies;
    }

    // ----------------------------------------------------
    // FETCH USER COMMENTS (EXCLUDING REPLIES)
    // ----------------------------------------------------
    public List<Comment> getUserCommentsExcludingReplies(String username) {

        log.info("COMMENT_FETCH_USER_COMMENTS_NO_REPLIES — username={}", username);

        List<Comment> comments = commentRepository.findByUserNameExcludingReplies(username);

        for (Comment c : comments) {
            if (c.getEntityType() == CommentEntityType.CHAPTER) {
                c.setChapter(chapterRepository.findById(c.getEntityId()).orElse(null));
            } else if (c.getEntityType() == CommentEntityType.NOVEL) {
                c.setNovel(novelRepository.findById(c.getEntityId().intValue()).orElse(null));
            }

            if (c.isHasReplies()) {
                c.setReplies(getCommentsByEntity(CommentEntityType.COMMENT, c.getId()));
            }
        }

        log.info("COMMENT_FETCH_USER_COMMENTS_NO_REPLIES_SUCCESS — username={}, count={}", username, comments.size());

        return comments;
    }
}
