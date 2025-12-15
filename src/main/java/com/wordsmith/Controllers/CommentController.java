package com.wordsmith.Controllers;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wordsmith.Entity.Comment;
import com.wordsmith.Entity.User;
import com.wordsmith.Enum.CommentEntityType;
import com.wordsmith.Enum.Role;
import com.wordsmith.Services.CommentService;
import com.wordsmith.Util.EmailMasker;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/comments")
public class CommentController {

    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
        logger.info("CommentController initialized");
    }

    /** 🔹 Fetch Comments for an Entity (Chapter, Novel, Announcement) */
    @GetMapping("/{entityType}/{entityId}")
    public String getComments(@PathVariable String entityType,
                              @PathVariable Long entityId,
                              Model model,
                              HttpSession session) {

        logger.info("GET /comments/{}/{} - fetching comments", entityType, entityId);

        try {
            CommentEntityType type = CommentEntityType.valueOf(entityType.toUpperCase());
            List<Comment> comments = commentService.getCommentsByEntity(type, entityId);

            model.addAttribute("comments", comments);
            model.addAttribute("entityType", entityType);
            model.addAttribute("entityId", entityId);
            model.addAttribute("loggedInUser", session.getAttribute("loggedInUser"));

            logger.debug("Loaded {} comments for {} with entityId={}", comments.size(), entityType, entityId);
        }
        catch (Exception e) {
            logger.error("Error fetching comments for entityType={}, entityId={}", entityType, entityId, e);
        }

        return "comments-section";
    }

    /** 🔹 Add a New Comment */
    @PostMapping("/add")
    public String addComment(@RequestParam String entityType,
                             @RequestParam Long entityId,
                             @RequestParam String content,
                             HttpSession session,
                             HttpServletRequest request) {

        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            logger.warn("POST /comments/add blocked - user not logged in");
            return "redirect:/auth/loginpage";
        }

        String maskedUser = EmailMasker.mask(user.getUsername());
        logger.info("POST /comments/add by user={} entity={} entityId={}", maskedUser, entityType, entityId);

        try {
            Comment comment = new Comment();
            comment.setId(commentService.getLastCommentId() + 1);
            comment.setEntityType(CommentEntityType.valueOf(entityType.toUpperCase()));
            comment.setEntityId(entityId);
            comment.setUserName(user.getUsername());
            comment.setCreatedAt(ZonedDateTime.now(ZoneId.systemDefault()));
            comment.setUpdatedAt(null);
            comment.setContent(content);

            // Handle replies
            if (comment.getEntityType() == CommentEntityType.COMMENT) {
                logger.debug("Adding reply to commentId={}", entityId);
                Comment parentComment = commentService.getCommentById(entityId);
                parentComment.setHasReplies(true);
                commentService.saveComment(parentComment);
            }

            commentService.saveComment(comment);
            logger.info("Comment saved successfully - id={}, user={}, entityType={}, entityId={}",
                    comment.getId(), maskedUser, entityType, entityId);

        } catch (Exception e) {
            logger.error("Error saving comment for user={} entity={} entityId={}",
                    maskedUser, entityType, entityId, e);
        }

        String referer = request.getHeader("Referer");
        return "redirect:" + referer + "#comment";
    }

    /** 🔹 Delete a Comment */
    @PostMapping("/delete")
    public String deleteComment(@RequestParam Long commentId,
                                HttpSession session,
                                boolean hasReplies,
                                HttpServletRequest request) {

        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            logger.warn("POST /comments/delete blocked - user not logged in");
            return "redirect:/auth/loginpage";
        }

        String maskedUser = EmailMasker.mask(user.getUsername());
        logger.info("POST /comments/delete - commentId={} requestedBy={}", commentId, maskedUser);

        try {
            Comment comment = commentService.getCommentById(commentId);

            if (comment != null &&
                    (comment.getUserName().equals(user.getUsername()) || user.getRole().equals(Role.ADMIN))) {

                commentService.deleteComment(commentId);
                logger.info("Comment deleted - commentId={}, deletedBy={}", commentId, maskedUser);
            } else {
                logger.warn("Unauthorized delete attempt - commentId={}, attemptedBy={}", commentId, maskedUser);
            }
        }
        catch (Exception e) {
            logger.error("Error deleting commentId={} by user={}", commentId, maskedUser, e);
        }

        String referer = request.getHeader("Referer");
        return "redirect:" + referer + "#comment";
    }

    /** 🔹 Flag a Comment */
    @PostMapping("/flag")
    public String flagComment(@RequestParam Long commentId,
                              HttpSession session,
                              HttpServletRequest request) {

        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            logger.warn("POST /comments/flag blocked - user not logged in");
            return "redirect:/auth/loginpage";
        }

        String maskedUser = EmailMasker.mask(user.getUsername());
        logger.info("POST /comments/flag - commentId={} flaggedBy={}", commentId, maskedUser);

        try {
            commentService.flagComment(commentId);
            logger.info("Comment flagged - commentId={}, user={}", commentId, maskedUser);
        } catch (Exception e) {
            logger.error("Error flagging commentId={} by user={}", commentId, maskedUser, e);
        }

        String referer = request.getHeader("Referer");
        return "redirect:" + referer + "#comment";
    }

    /** 🔹 Admin: View all comments */
    @RequestMapping("/allcomments")
    public String allComments(Model m) {
        logger.info("GET /comments/allcomments - admin view");

        try {
            var list = commentService.findallcomment();
            m.addAttribute("Comments", list);
            logger.debug("Loaded {} total comments for admin view", list.size());
        }
        catch (Exception e) {
            logger.error("Error loading all comments for admin", e);
        }

        return "allcomments";
    }
}
