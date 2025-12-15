package com.wordsmith.Services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wordsmith.Entity.Announcement;
import com.wordsmith.Repositories.AnnouncementRepository;

@Service
public class AnnouncementService {

    private static final Logger log = LoggerFactory.getLogger(AnnouncementService.class);

    @Autowired
    private AnnouncementRepository announcementRepository;

    public List<Announcement> getAllVisibleAnnouncements() {

        log.info("ANNOUNCEMENT_FETCH_VISIBLE — Fetching all visible announcements");

        List<Announcement> list = announcementRepository.findByVisibleTrueOrderByCreatedAtDesc();

        log.info("ANNOUNCEMENT_FETCH_VISIBLE_SUCCESS — count={}", list.size());

        return list;
    }

    public Announcement getAnnouncementById(Long id) {

        log.info("ANNOUNCEMENT_FETCH_BY_ID — id={}", id);

        Announcement a = announcementRepository.findById(id).orElse(null);

        if (a == null) {
            log.warn("ANNOUNCEMENT_NOT_FOUND — id={}", id);
        } else {
            log.info("ANNOUNCEMENT_FETCH_SUCCESS — id={}", id);
        }

        return a;
    }

    public void createAnnouncement(String title, String content, String postedBy) {

        log.info("ANNOUNCEMENT_CREATE — postedBy={}, titleLength={}, contentLength={}",
                postedBy,
                (title != null ? title.length() : 0),
                (content != null ? content.length() : 0)
        );

        try {
            Announcement a = new Announcement();
            a.setTitle(title);
            a.setContent(content);
            a.setPostedBy(postedBy);

            announcementRepository.save(a);

            log.info("ANNOUNCEMENT_CREATE_SUCCESS — id={}", a.getId());

        } catch (Exception ex) {
            log.error("ANNOUNCEMENT_CREATE_ERROR — postedBy={}, error={}",
                    postedBy, ex.getMessage(), ex);
        }
    }

    public void updateAnnouncement(Long id, String title, String content) {

        log.info("ANNOUNCEMENT_UPDATE — id={}, titleLength={}, contentLength={}",
                id,
                (title != null ? title.length() : 0),
                (content != null ? content.length() : 0)
        );

        try {
            Announcement a = announcementRepository.findById(id).orElse(null);

            if (a == null) {
                log.warn("ANNOUNCEMENT_UPDATE_FAILED_NOT_FOUND — id={}", id);
                return;
            }

            a.setTitle(title);
            a.setContent(content);

            announcementRepository.save(a);

            log.info("ANNOUNCEMENT_UPDATE_SUCCESS — id={}", id);

        } catch (Exception ex) {
            log.error("ANNOUNCEMENT_UPDATE_ERROR — id={}, error={}", id, ex.getMessage(), ex);
        }
    }

    public void deleteAnnouncement(Long id) {

        log.info("ANNOUNCEMENT_DELETE — id={}", id);

        try {
            announcementRepository.deleteById(id);
            log.info("ANNOUNCEMENT_DELETE_SUCCESS — id={}", id);
        } catch (Exception ex) {
            log.error("ANNOUNCEMENT_DELETE_ERROR — id={}, error={}", id, ex.getMessage(), ex);
        }
    }

    public void toggleVisibility(Long id) {

        log.info("ANNOUNCEMENT_TOGGLE_VISIBILITY — id={}", id);

        try {
            Announcement a = announcementRepository.findById(id).orElse(null);

            if (a == null) {
                log.warn("ANNOUNCEMENT_TOGGLE_FAILED_NOT_FOUND — id={}", id);
                return;
            }

            boolean newVisibility = !a.isVisible();
            a.setVisible(newVisibility);
            announcementRepository.save(a);

            log.info("ANNOUNCEMENT_TOGGLE_SUCCESS — id={}, newVisible={}", id, newVisibility);

        } catch (Exception ex) {
            log.error("ANNOUNCEMENT_TOGGLE_ERROR — id={}, error={}", id, ex.getMessage(), ex);
        }
    }

    public Announcement getLatestAnnouncement() {

        log.info("ANNOUNCEMENT_FETCH_LATEST");

        Announcement a = announcementRepository.findFirstByOrderByCreatedAtDesc();

        if (a == null) {
            log.warn("ANNOUNCEMENT_NO_LATEST_FOUND");
        } else {
            log.info("ANNOUNCEMENT_FETCH_LATEST_SUCCESS — id={}", a.getId());
        }

        return a;
    }
}
