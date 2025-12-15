package com.wordsmith.Controllers;

import com.wordsmith.Entity.*;
import com.wordsmith.Services.*;
import com.wordsmith.Enum.LikeEnum;
import com.wordsmith.Enum.CommentEntityType;
import com.wordsmith.Util.EmailMasker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/announcements")
public class AnnouncementController {

    private static final Logger appLogger = LoggerFactory.getLogger("application");
    private static final Logger userLogger = LoggerFactory.getLogger("users");
    private static final Logger errorLogger = LoggerFactory.getLogger("errors");

    @Autowired
    private AnnouncementService announcementService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private CommentService commentService;

    // View all announcements
    @GetMapping
    public String viewAnnouncements(Model model) {
        appLogger.info("Fetching all visible announcements");

        model.addAttribute("announcements", announcementService.getAllVisibleAnnouncements());
        return "announcements";
    }

    // Admin panel
    @GetMapping("/manage")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String manageAnnouncements(Model model) {
        appLogger.info("ADMIN accessed announcement management panel");

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

        appLogger.info("Admin creating announcement | title='{}' | postedBy='{}'",
                title, EmailMasker.mask(postedBy));

        announcementService.createAnnouncement(title, content, postedBy);

        userLogger.info("Announcement created by admin | title='{}' | user='{}'",
                title, EmailMasker.mask(postedBy));

        return "redirect:/announcements/manage";
    }

    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String editAnnouncement(@RequestParam Long id,
                                   @RequestParam String title,
                                   @RequestParam String content) {

        appLogger.info("Admin editing announcement | id={} | newTitle='{}'", id, title);

        announcementService.updateAnnouncement(id, title, content);
        return "redirect:/announcements/manage";
    }

    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteAnnouncement(@RequestParam Long id) {

        appLogger.warn("Admin deleting announcement | id={}", id);

        announcementService.deleteAnnouncement(id);
        return "redirect:/announcements/manage";
    }

    @PostMapping("/toggle-visibility")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String toggleVisibility(@RequestParam Long id) {

        appLogger.info("Admin toggled visibility for announcement | id={}", id);

        announcementService.toggleVisibility(id);
        return "redirect:/announcements/manage";
    }

    @GetMapping("/{id}")
    public String viewAnnouncementDetails(@PathVariable Long id,
                                          Model model,
                                          HttpServletRequest request) {

        appLogger.info("User viewing announcement details | id={}", id);

        Announcement announcement = announcementService.getAnnouncementById(id);

        announcement.setLoveCount(likeService.getReactionCount(id, "announcement", LikeEnum.LOVE));
        announcement.setAngryCount(likeService.getReactionCount(id, "announcement", LikeEnum.ANGRY));
        announcement.setLaughCount(likeService.getReactionCount(id, "announcement", LikeEnum.LAUGH));
        announcement.setSadCount(likeService.getReactionCount(id, "announcement", LikeEnum.SAD));
        announcement.setWowCount(likeService.getReactionCount(id, "announcement", LikeEnum.WOW));
        announcement.setFireCount(likeService.getReactionCount(id, "announcement", LikeEnum.FIRE));

        var user = (User) request.getSession().getAttribute("loggedInUser");

        if (user != null) {
            var userReaction = likeService.getUserReaction("chapter", id, user.getUsername());
            announcement.setUserReaction(userReaction);

            userLogger.info("User viewed announcement details | id={} | user='{}'",
                    id, EmailMasker.mask(user.getUsername()));
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
                var reaction = likeService.getUserReaction("comment", comment.getId(), user.getUsername());
                comment.setUserReaction(reaction);
            }

            if (comment.isHasReplies()) {
                comment.setReplies(commentService.getCommentsByEntity(CommentEntityType.COMMENT, comment.getId()));

                for (Comment reply : comment.getReplies()) {
                    if (reply.getCreatedAt() != null) {
                        reply.setTimeAgo(getTimeDifference(reply.getCreatedAt()));
                    }
                    reply.setLikeCount(likeService.getLikesCount(reply.getId(), "comment"));
                    reply.setDislikeCount(likeService.getDislikesCount(reply.getId(), "comment"));

                    if (user != null) {
                        var reaction = likeService.getUserReaction("comment", reply.getId(), user.getUsername());
                        reply.setUserReaction(reaction);
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

        if (duration.toMinutes() < 1) return "Just now";
        else if (duration.toMinutes() < 60) return duration.toMinutes() + " minutes ago";
        else if (duration.toHours() < 24) return duration.toHours() + " hours ago";
        else if (duration.toDays() < 7) return duration.toDays() + " days ago";
        else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
            return formatter.format(pastTime);
        }
    }

    private String getTimeDifference(ZonedDateTime pastTime) {
        ZonedDateTime now = ZonedDateTime.now();
        Duration duration = Duration.between(pastTime, now);

        if (duration.toMinutes() < 1) return "Just now";
        else if (duration.toMinutes() < 60) return duration.toMinutes() + " minutes ago";
        else if (duration.toHours() < 24) return duration.toHours() + " hours ago";
        else if (duration.toDays() < 7) return duration.toDays() + " days ago";
        else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
            return formatter.format(pastTime);
        }
    }
}

