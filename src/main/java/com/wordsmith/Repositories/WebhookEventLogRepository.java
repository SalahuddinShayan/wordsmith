package com.wordsmith.Repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wordsmith.Entity.WebhookEventLog;

public interface WebhookEventLogRepository extends JpaRepository<WebhookEventLog, String> {

    @Query("SELECT MAX(w.receivedAt) FROM WebhookEventLog w")
    Optional<LocalDateTime> findLatestEventTime();

    List<WebhookEventLog> findAllByOrderByReceivedAtDesc();
}
