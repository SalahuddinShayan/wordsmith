package com.wordsmith.Repositories;

import com.wordsmith.Entity.WebhookFailure;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WebhookFailureRepository extends JpaRepository<WebhookFailure, Long> {

    List<WebhookFailure> findByCreatedAtAfterOrderByCreatedAtDesc(LocalDateTime since);

    List<WebhookFailure> findAllByOrderByCreatedAtDesc();
}
