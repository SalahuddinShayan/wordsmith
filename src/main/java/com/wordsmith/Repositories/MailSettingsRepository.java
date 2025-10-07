package com.wordsmith.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wordsmith.Entity.MailSettings;

@Repository
public interface MailSettingsRepository extends JpaRepository<MailSettings, Long> {
    Optional<MailSettings> findTopByOrderByIdDesc();
}
