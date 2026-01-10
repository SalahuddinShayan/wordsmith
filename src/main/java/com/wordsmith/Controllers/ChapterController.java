package com.wordsmith.Controllers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.wordsmith.Entity.Announcement;
import com.wordsmith.Entity.Chapter;
import com.wordsmith.Entity.Comment;
import com.wordsmith.Entity.User;
import com.wordsmith.Enum.ChapterAccessResult;
import com.wordsmith.Enum.CommentEntityType;
import com.wordsmith.Enum.LikeEnum;
import com.wordsmith.Enum.ReleaseStatus;
import com.wordsmith.Repositories.ChapterPurchaseRepository;
import com.wordsmith.Repositories.ChapterRepository;
import com.wordsmith.Repositories.NovelRepository;
import com.wordsmith.Services.AnnouncementService;
import com.wordsmith.Services.ChapterAccessService;
import com.wordsmith.Services.ChapterPurchaseService;
import com.wordsmith.Services.CommentService;
import com.wordsmith.Services.LikeService;
import com.wordsmith.Services.ViewsService;
import com.wordsmith.Services.WalletService;
import com.wordsmith.Util.EmailMasker;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Controller
public class ChapterController {

    private static final Logger logger = LoggerFactory.getLogger(ChapterController.class);

    private final ChapterRepository cr;
    private final CommentService commentService;
    private final ViewsService viewsService;
    private final LikeService likeService;
    private final AnnouncementService announcementService;
    private final NovelRepository novelRepository;
    private final ChapterPurchaseService chapterPurchaseService;
    private final ChapterAccessService chapterAccessService;
    private final WalletService walletService;
    private final ChapterPurchaseRepository chapterPurchaseRepository;

    public ChapterController(CommentService commentService, ChapterRepository cr, ViewsService viewsService, LikeService likeService,
                             AnnouncementService announcementService, NovelRepository novelRepository, ChapterPurchaseService chapterPurchaseService, 
                             ChapterAccessService chapterAccessService, WalletService walletService, ChapterPurchaseRepository chapterPurchaseRepository) {
        this.commentService = commentService;
        this.cr = cr;
        this.viewsService = viewsService;
        this.likeService = likeService;
        this.announcementService = announcementService;
        this.novelRepository = novelRepository;
        this.chapterPurchaseService = chapterPurchaseService;
        this.chapterAccessService = chapterAccessService;
        this.walletService = walletService;
        this.chapterPurchaseRepository = chapterPurchaseRepository;
        logger.info("ChapterController initialized");
    }

    @RequestMapping("/chapterlist")
    public String chapterList(@RequestParam("NovelName") String novelname, Model model) {
        logger.info("GET /chapterlist - novelName={}", novelname);

        model.addAttribute("Chapters", cr.byNovelName(novelname));
        model.addAttribute("command", new Chapter());
        model.addAttribute("novelname", novelname);
        model.addAttribute("novelStatus", novelRepository.novelStatus(novelname));
        model.addAttribute("stock", cr.countByReleaseStatusAndNovelName(ReleaseStatus.STOCKPILE, novelname));

        logger.debug("chapterList loaded for novelName={}", novelname);
        return "chapterlist";
    }

    @RequestMapping("/savechapter")
    public RedirectView save(@ModelAttribute Chapter chapter,
                             RedirectAttributes redirectAttributes) {

        Long cid = chapter.getChapterId();
        logger.info("POST /savechapter - incoming chapterId={}, novelName={}",
                cid, chapter.getNovelName());

        if (cid == 0) {
            long id = cr.Last() + 1;
            chapter.setChapterId(id);
            ZonedDateTime serverTime = ZonedDateTime.now(ZoneId.systemDefault());
            chapter.setPostedOn(serverTime);
            logger.debug("Creating NEW chapter: id={}, novelName={}", id, chapter.getNovelName());
        } else {
            Chapter chapter2 = cr.getReferenceById(cid);
            chapter.setPostedOn(chapter2.getPostedOn());
            chapter.setReleaseStatus(chapter2.getReleaseStatus()); // Preserve original
            if (chapter.getReleaseStatus() == ReleaseStatus.RELEASED) {
                chapter.setReleasedOn(chapter2.getReleasedOn());
            }
            logger.debug("Updating EXISTING chapter: id={}, novelName={}", cid, chapter.getNovelName());
        }

        cr.save(chapter);
        logger.info("Chapter saved - id={}, novelName={}", chapter.getChapterId(), chapter.getNovelName());

        RedirectView redirectView = new RedirectView("/chapterlist", true);
        redirectAttributes.addAttribute("NovelName", chapter.getNovelName());
        return redirectView;
    }

    @RequestMapping("/savechapter2")
    public RedirectView save2(@ModelAttribute("chapter") Chapter chapter,
                              RedirectAttributes redirectAttributes) {

        Long cid = chapter.getChapterId();
        logger.info("POST /savechapter2 - incoming chapterId={}, novelName={}",
                cid, chapter.getNovelName());

        if (cid == 0) {
            long id = cr.Last() + 1;
            chapter.setChapterId(id);
            ZonedDateTime serverTime = ZonedDateTime.now(ZoneId.systemDefault());
            chapter.setPostedOn(serverTime);
            logger.debug("Creating NEW chapter (save2): id={}, novelName={}", id, chapter.getNovelName());
        } else {
            Chapter chapter2 = cr.getReferenceById(cid);
            chapter.setPostedOn(chapter2.getPostedOn());
            logger.debug("Updating EXISTING chapter (save2): id={}, novelName={}", cid, chapter.getNovelName());
        }

        cr.save(chapter);
        logger.info("Chapter saved via savechapter2 - id={}, novelName={}",
                chapter.getChapterId(), chapter.getNovelName());

        RedirectView redirectView = new RedirectView("/allchapters", true);
        return redirectView;
    }

    @RequestMapping(value = "/deletechapter")
    public RedirectView deletemember(@RequestParam("ChapterId") long id,
                                     RedirectAttributes redirectAttributes) {

        logger.info("DELETE /deletechapter - chapterId={}", id);
        Chapter chapter = cr.getReferenceById(id);
        String novelName = chapter.getNovelName();

        RedirectView redirectView = new RedirectView("/chapterlist", true);
        redirectAttributes.addAttribute("NovelName", novelName);
        cr.deleteById(id);

        logger.info("Chapter deleted - id={}, novelName={}", id, novelName);
        return redirectView;
    }

    @RequestMapping(value = "/deletechapter2")
    public RedirectView deletechapter(@RequestParam("ChapterId") long id) {
        logger.info("DELETE /deletechapter2 - chapterId={}", id);

        cr.deleteById(id);

        logger.info("Chapter deleted via deletechapter2 - id={}", id);
        RedirectView redirectView = new RedirectView("/allchapters", true);
        return redirectView;
    }

    // ==========================================================
    // CHAPTER PAGE
    // ==========================================================    
    @RequestMapping("/chapter/{chapterId}")
    public String chapter(@PathVariable long chapterId, Model m, HttpServletRequest request) {
        logger.info("GET /chapter/{} - start", chapterId);

        Chapter chapter = cr.findById(chapterId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var user = (User)request.getSession().getAttribute("loggedInUser");

        String username = (user != null) ? user.getUsername() : null;

        // --------------------------------------------------
        // 🔐 ACCESS DECISION (SINGLE SOURCE OF TRUTH)
        // --------------------------------------------------
        ChapterAccessResult accessResult =
                chapterAccessService.decideAccess(chapter, username);

        switch (accessResult) {

            case ALLOWED:
                break; // continue rendering

            case LOGIN_REQUIRED:
                logger.warn(
                    "LOGIN_REQUIRED chapterId={}",
                    chapterId
                );
                return "redirect:/auth/loginpage";

            case PURCHASE_REQUIRED:
                logger.warn(
                    "PURCHASE_REQUIRED chapterId={} user={}",
                    chapterId,
                    (username != null ? EmailMasker.mask(username) : "anonymous")
                );

                int price = chapterAccessService.getChapterPrice(chapterId);
                long balance = walletService.getBalance(username);
                boolean canAfford = balance >= price;

                // Minimal data for purchase-required page
                m.addAttribute("chapterId", chapterId);
                m.addAttribute("novelName", chapter.getNovelName());
                m.addAttribute("chapterNo", chapter.getChapterNo());
                m.addAttribute("chapterTitle", chapter.getTitle());
                m.addAttribute("price", price);
                m.addAttribute("walletBalance", balance);
                m.addAttribute("canAfford", canAfford);
                return "chapter-purchase-required";
        }

        // --------------------------------------------------
        // 👁️ VIEWS (only after access granted)
        // --------------------------------------------------
        viewsService.incrementViews(chapterId, CommentEntityType.CHAPTER);
        logger.debug("Views incremented for chapterId={}", chapterId);

        // --------------------------------------------------
        // ❤️ REACTIONS
        // --------------------------------------------------
        chapter.setLoveCount(likeService.getReactionCount(chapterId, "chapter", LikeEnum.LOVE));
        chapter.setAngryCount(likeService.getReactionCount(chapterId, "chapter", LikeEnum.ANGRY));
        chapter.setLaughCount(likeService.getReactionCount(chapterId, "chapter", LikeEnum.LAUGH));
        chapter.setSadCount(likeService.getReactionCount(chapterId, "chapter", LikeEnum.SAD));
        chapter.setWowCount(likeService.getReactionCount(chapterId, "chapter", LikeEnum.WOW));
        chapter.setFireCount(likeService.getReactionCount(chapterId, "chapter", LikeEnum.FIRE));

        if (user != null) {
            String maskedUser = EmailMasker.mask(username);
            logger.debug(
                "User viewing chapter - usernameMasked={} chapterId={}",
                maskedUser,
                chapterId
            );

            var userReaction =
                    likeService.getUserReaction("chapter", chapterId, username);
            chapter.setUserReaction(userReaction);
        }


        m.addAttribute("chapter", chapter);

        // --------------------------------------------------
        // 💬 COMMENTS
        // --------------------------------------------------
        CommentEntityType type = CommentEntityType.CHAPTER;
        List<Comment> comments =
                commentService.getCommentsByEntity(type, chapterId);

        for (Comment comment : comments) {

            if (comment.getCreatedAt() != null) {
                comment.setTimeAgo(getTimeDifference(comment.getCreatedAt()));
            }

            comment.setLikeCount(
                    likeService.getLikesCount(comment.getId(), "comment"));
            comment.setDislikeCount(
                    likeService.getDislikesCount(comment.getId(), "comment"));

            if (user != null) {
                var userReaction =
                        likeService.getUserReaction("comment", comment.getId(), username);
                comment.setUserReaction(userReaction);
            }

            if (comment.isHasReplies()) {
                comment.setReplies(
                        commentService.getCommentsByEntity(
                                CommentEntityType.COMMENT,
                                comment.getId()
                        )
                );

                for (Comment reply : comment.getReplies()) {
                    if (reply.getCreatedAt() != null) {
                        reply.setTimeAgo(getTimeDifference(reply.getCreatedAt()));
                    }

                    reply.setLikeCount(
                            likeService.getLikesCount(reply.getId(), "comment"));
                    reply.setDislikeCount(
                            likeService.getDislikesCount(reply.getId(), "comment"));

                    if (user != null) {
                        var replyReaction =
                                likeService.getUserReaction(
                                        "comment",
                                        reply.getId(),
                                        username
                                );
                        reply.setUserReaction(replyReaction);
                    }
                }
            }
        }

        m.addAttribute("comments", comments);
        m.addAttribute("newComment", new Comment());

        // --------------------------------------------------
        // 📢 ANNOUNCEMENT
        // --------------------------------------------------
        Announcement announcement =
                announcementService.getLatestAnnouncement();

        if (announcement != null && announcement.isVisible()) {
            announcement.setTimeAgo(
                    getTimeDifference(announcement.getCreatedAt()));
        }

        m.addAttribute("announcement", announcement);

        logger.info("Rendering chaptertemplate for chapterId={}", chapterId);
        return "chaptertemplate";
    }


    @RequestMapping("chapter-next/{chapterId}")
    public RedirectView nextChapter(@PathVariable long chapterId) {
        logger.info("GET /chapter-next/{} - start", chapterId);

        Chapter current = cr.getReferenceById(chapterId);
        String novelName = current.getNovelName();

        try {
            long nId = cr.NextChapterId(novelName, chapterId);
            String nextId = Long.toString(nId);
            logger.info("Next chapter found - currentId={}, nextId={}, novelName={}",
                    chapterId, nextId, novelName);

            RedirectView redirectView = new RedirectView("/chapter/" + nextId, true);
            return redirectView;
        } catch (Exception e) {
            logger.warn("No next chapter found for chapterId={}, novelName={}, redirecting to novel page",
                    chapterId, novelName, e);
            RedirectView redirectView = new RedirectView("/novel/" + novelName, true);
            return redirectView;
        }
    }

    @RequestMapping("chapter-previous/{chapterId}")
    public RedirectView previousChapter(@PathVariable long chapterId) {
        logger.info("GET /chapter-previous/{} - start", chapterId);

        Chapter current = cr.getReferenceById(chapterId);
        String novelName = current.getNovelName();

        try {
            String nextId = Long.toString(cr.PreviousChapterId(novelName, chapterId));
            logger.info("Previous chapter found - currentId={}, previousId={}, novelName={}",
                    chapterId, nextId, novelName);

            RedirectView redirectView = new RedirectView("/chapter/" + nextId, true);
            return redirectView;
        } catch (Exception e) {
            logger.warn("No previous chapter found for chapterId={}, novelName={}, redirecting to novel page",
                    chapterId, novelName, e);
            RedirectView redirectView = new RedirectView("/novel/" + novelName, true);
            return redirectView;
        }
    }

    @RequestMapping("/latest/{novelname}")
    public String latestChapters(@PathVariable String novelname, Model model) {
        logger.info("GET /latest/{} - start", novelname);

        List<Chapter> chapters = cr.Latest(novelname);
        for (Chapter chapter : chapters) {
            if (chapter.getReleasedOn() != null) {
                chapter.setTimeAgo(getTimeDifference(chapter.getReleasedOn()));
            } else {
                chapter.setTimeAgo(getTimeDifference(chapter.getPostedOn()));
            }
        }

        logger.debug("Loaded {} latest chapters for novelName={}", chapters.size(), novelname);
        model.addAttribute("Chapters", chapters);
        return "lc";
    }

    

    @PostMapping("/ReleaseChapter")
    public RedirectView releaseChapter(@RequestParam("ChapterId") long id,
                                       RedirectAttributes redirectAttributes) {
        logger.info("POST /ReleaseChapter - chapterId={}", id);

        Chapter chapter = cr.getReferenceById(id);
        ZonedDateTime serverTime = ZonedDateTime.now(ZoneId.systemDefault());
        chapter.setReleasedOn(serverTime);
        chapter.setReleaseStatus(ReleaseStatus.RELEASED);
        cr.save(chapter);

        logger.info("Chapter released - id={}, novelName={}, releasedOn={}",
                id, chapter.getNovelName(), serverTime);

        RedirectView redirectView = new RedirectView("/chapterlist", true);
        redirectAttributes.addAttribute("NovelName", chapter.getNovelName());
        return redirectView;
    }

    // ===== Time formatting helpers (unchanged) =====

    private String getTimeDifference(LocalDateTime pastTime) {
        LocalDateTime now = LocalDateTime.now();
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
            return formatter.format(pastTime);
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
            return formatter.format(pastTime).toString();
        }
    }


    @PostMapping("/chapter/{chapterId}/purchase")
    @Transactional
    public String purchaseChapter(
            @PathVariable long chapterId,
            HttpSession session
    ) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/auth/loginpage";
        }

        if(cr.findById(chapterId).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Chapter not found");
        }

        // Safety re-check
        boolean alreadyOwned = chapterPurchaseRepository.existsByUsernameAndChapterId(user.getUsername(), chapterId);

        if (alreadyOwned) {
            return "redirect:/chapter/" + chapterId;
        }


        // 🔒 Atomic operation
        chapterPurchaseService.purchaseChapter(
                user.getUsername(),
                chapterId
        );

        return "redirect:/chapter/" + chapterId;
    }


}
