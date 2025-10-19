package com.wordsmith.Controllers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wordsmith.Entity.Announcement;
import com.wordsmith.Entity.Comment;
import com.wordsmith.Entity.User;
import com.wordsmith.Enum.CommentEntityType;
import com.wordsmith.Enum.LikeEnum;
import com.wordsmith.Services.AnnouncementService;
import com.wordsmith.Services.CommentService;
import com.wordsmith.Services.LikeService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/announcements")
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private CommentService commentService;

    // View all announcements (visible only)
    @GetMapping
    public String viewAnnouncements(Model model) {
        model.addAttribute("announcements", announcementService.getAllVisibleAnnouncements());
        return "announcements";
    }

    // Admin panel view
    @GetMapping("/manage")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String manageAnnouncements(Model model) {
        model.addAttribute("announcements", announcementService.getAllVisibleAnnouncements());
        return "manage-announcements";
    }

    @PostMapping("/add")
@PreAuthorize("hasAuthority('ADMIN')")
public String addAnnouncement(@RequestParam String title,
                              @RequestParam String content,
                              HttpSession session) {

    User loggedInUser = (User) session.getAttribute("loggedInUser");
    String postedBy = (loggedInUser != null) ? loggedInUser.getUsername() : "Admin";

    announcementService.createAnnouncement(title, content, postedBy);
    return "redirect:/announcements/manage";
}

    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String editAnnouncement(@RequestParam Long id,
                                   @RequestParam String title,
                                   @RequestParam String content) {
        announcementService.updateAnnouncement(id, title, content);
        return "redirect:/announcements/manage";
    }

    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteAnnouncement(@RequestParam Long id) {
        announcementService.deleteAnnouncement(id);
        return "redirect:/announcements/manage";
    }

    @PostMapping("/toggle-visibility")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String toggleVisibility(@RequestParam Long id) {
        announcementService.toggleVisibility(id);
        return "redirect:/announcements/manage";
    }

    @GetMapping("/{id}")
    public String viewAnnouncementDetails(@PathVariable Long id, Model model, HttpServletRequest request) {
        Announcement announcement = announcementService.getAnnouncementById(id);

        announcement.setLoveCount(likeService.getReactionCount(id, "announcement", LikeEnum.LOVE));
        announcement.setAngryCount(likeService.getReactionCount(id, "announcement", LikeEnum.ANGRY));
        announcement.setLaughCount(likeService.getReactionCount(id, "announcement", LikeEnum.LAUGH));
        announcement.setSadCount(likeService.getReactionCount(id, "announcement", LikeEnum.SAD));
        announcement.setWowCount(likeService.getReactionCount(id, "announcement", LikeEnum.WOW));
        announcement.setFireCount(likeService.getReactionCount(id, "announcement", LikeEnum.FIRE));

        var user = (com.wordsmith.Entity.User) request.getSession().getAttribute("loggedInUser");

        if (user != null) {
			var userReaction = likeService.getUserReaction("chapter", id, user.getUsername());
			announcement.setUserReaction(userReaction);
		}
		
        if (announcement.getCreatedAt() != null) {
            announcement.setTimeAgo(getTimeDifference(announcement.getCreatedAt()));
        }



        model.addAttribute("announcement", announcement);

        CommentEntityType type = CommentEntityType.ANNOUNCEMENT;
		List<Comment> comments = commentService.getCommentsByEntity(type, id);

        for (Comment comment : comments) {
            if (comment.getCreatedAt() != null) {
                comment.setTimeAgo(getTimeDifference(comment.getCreatedAt()));
            }
            comment.setLikeCount(likeService.getLikesCount(comment.getId(), "comment"));
            comment.setDislikeCount(likeService.getDislikesCount(comment.getId(), "comment"));
            if (user != null) {
                var userReaction = likeService.getUserReaction("comment", comment.getId(), user.getUsername());
                comment.setUserReaction(userReaction);
            }
            if (comment.isHasReplies()){
                comment.setReplies(commentService.getCommentsByEntity(CommentEntityType.COMMENT, comment.getId()));
                for (Comment reply : comment.getReplies()) {
                    if (reply.getCreatedAt() != null) {
                        reply.setTimeAgo(getTimeDifference(reply.getCreatedAt()));
                    }
                    reply.setLikeCount(likeService.getLikesCount(reply.getId(), "comment"));
                    reply.setDislikeCount(likeService.getDislikesCount(reply.getId(), "comment"));
                    if (user != null) {
                        var userReaction = likeService.getUserReaction("comment", reply.getId(), user.getUsername());
                        reply.setUserReaction(userReaction);
                    }

                }
            }
        }
        model.addAttribute("comments", comments);

        return "announcement-details";
    }



    private String getTimeDifference(LocalDateTime pastTime) {
	    ZonedDateTime now = ZonedDateTime.now();
	    Duration duration = Duration.between(pastTime, now);

	    if (duration.toMinutes() < 1) {
	        return "Just now";
	    } else if (duration.toMinutes() < 60) {
	        return duration.toMinutes() + " minutes ago";
	    } else if (duration.toHours() < 24) {
	        return duration.toHours() + " hours ago";
	    } else if (duration.toDays() < 7) {
	        return duration.toDays() + " days ago";
	    } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
            return formatter.format(pastTime).toString(); // Show full date if older than a week
        }
	    
	}

    private String getTimeDifference(ZonedDateTime pastTime) {
	    ZonedDateTime now = ZonedDateTime.now();
	    Duration duration = Duration.between(pastTime, now);

	    if (duration.toMinutes() < 1) {
	        return "Just now";
	    } else if (duration.toMinutes() < 60) {
	        return duration.toMinutes() + " minutes ago";
	    } else if (duration.toHours() < 24) {
	        return duration.toHours() + " hours ago";
	    } else if (duration.toDays() < 7) {
	        return duration.toDays() + " days ago";
	    } else {
	    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
	        return formatter.format(pastTime).toString(); // Show full date if older than a week
	    }
	    
	}
}

