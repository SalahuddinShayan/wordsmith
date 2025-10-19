package com.wordsmith.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wordsmith.Entity.Announcement;
import com.wordsmith.Repositories.AnnouncementRepository;

@Service
public class AnnouncementService {

    @Autowired
    private AnnouncementRepository announcementRepository;

    public List<Announcement> getAllVisibleAnnouncements() {
        return announcementRepository.findByVisibleTrueOrderByCreatedAtDesc();
    }

    public Announcement getAnnouncementById(Long id) {
        return announcementRepository.findById(id).orElse(null);
    }

    public void createAnnouncement(String title, String content, String postedBy) {
        Announcement a = new Announcement();
        a.setTitle(title);
        a.setContent(content);
        a.setPostedBy(postedBy);
        announcementRepository.save(a);
    }

    public void updateAnnouncement(Long id, String title, String content) {
        Announcement a = announcementRepository.findById(id).orElse(null);
        if (a != null) {
            a.setTitle(title);
            a.setContent(content);
            announcementRepository.save(a);
        }
    }

    public void deleteAnnouncement(Long id) {
        announcementRepository.deleteById(id);
    }

    public void toggleVisibility(Long id) {
        Announcement a = announcementRepository.findById(id).orElse(null);
        if (a != null) {
            a.setVisible(!a.isVisible());
            announcementRepository.save(a);
        }
    }

    public Announcement getLatestAnnouncement() {
        return announcementRepository.findFirstByOrderByCreatedAtDesc();
    }
}
