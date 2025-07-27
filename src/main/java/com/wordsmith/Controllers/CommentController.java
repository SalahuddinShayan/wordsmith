package com.wordsmith.Controllers;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

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

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    /** 🔹 Fetch Comments for an Entity (Chapter, Novel, Announcement) */
    @GetMapping("/{entityType}/{entityId}")
    public String getComments(@PathVariable String entityType, @PathVariable Long entityId, Model model, HttpSession session) {
        System.out.println(entityType);
        System.out.println(entityId);
    	
    	CommentEntityType type = CommentEntityType.valueOf(entityType.toUpperCase());
        List<Comment> comments = commentService.getCommentsByEntity(type, entityId);

        model.addAttribute("comments", comments);
        model.addAttribute("entityType", entityType);
        model.addAttribute("entityId", entityId);
        model.addAttribute("loggedInUser", session.getAttribute("loggedInUser"));

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
            return "redirect:/auth/loginpage";
        }
        

        Comment comment = new Comment();
        comment.setId(commentService.getLastCommentId() +1);
        comment.setEntityType(CommentEntityType.valueOf(entityType.toUpperCase()));
        comment.setEntityId(entityId);
        comment.setUserName(user.getUsername());
        ZonedDateTime serverTime = ZonedDateTime.now(ZoneId.systemDefault());
        comment.setCreatedAt(serverTime);
        comment.setUpdatedAt(null);
        comment.setContent(content);
        if(comment.getEntityType() == (CommentEntityType.COMMENT)){
            System.out.println("Adding a reply to comment with ID: " + entityId);
            Comment parentComment = commentService.getCommentById(entityId);

            parentComment.setHasReplies(true);
            commentService.saveComment(parentComment);
        }

        commentService.saveComment(comment);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer +"#comment";
    }

    /** 🔹 Delete a Comment */
    @PostMapping("/delete")
    public String deleteComment(@RequestParam Long commentId, HttpSession session, boolean hasReplies, HttpServletRequest request) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/auth/loginpage";
        }

        Comment comment = commentService.getCommentById(commentId);
        if (comment != null && (comment.getUserName().equals(user.getUsername()) || user.getRole().equals(Role.ADMIN))) {
            commentService.deleteComment(commentId);
        }

        String referer = request.getHeader("Referer");
        return "redirect:" + referer +"#comment";
    }

    /** 🔹 Flag a Comment */
    @PostMapping("/flag")
    public String flagComment(@RequestParam Long commentId, HttpSession session, HttpServletRequest request) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/auth/loginpage";
        }

        commentService.flagComment(commentId);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer +"#comment";
    }
    
    @RequestMapping("/allcomments")
	public String messaegs(Model m) {
		m.addAttribute("Comments", commentService.findallcomment());
		return "allcomments";
	}
}