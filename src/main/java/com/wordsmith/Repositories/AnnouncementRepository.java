package com.wordsmith.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wordsmith.Entity.Announcement;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    List<Announcement> findByVisibleTrueOrderByCreatedAtDesc();

    Announcement findFirstByOrderByCreatedAtDesc();
}



