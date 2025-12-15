package com.wordsmith.Services;

import com.wordsmith.Entity.WebhookFailure;
import com.wordsmith.Repositories.WebhookFailureRepository;
import com.wordsmith.Util.EmailMasker;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class WebhookFailureService {

    private static final Logger log = LoggerFactory.getLogger(WebhookFailureService.class);

    private final WebhookFailureRepository repo;

    public WebhookFailureService(WebhookFailureRepository repo) {
        this.repo = repo;
    }

    // --------------------------------------------------------------------
    // MAIN LOGGER (full 6-argument version)
    // --------------------------------------------------------------------
    public void logFailure(String eventId,
                           String eventType,
                           String subscriptionId,
                           String payloadJson,
                           String headersJson,
                           String errorMessage) {

        // Mask emails inside payload or headers for safety
        String maskedPayload = payloadJson != null ? EmailMasker.mask(payloadJson) : null;
        String maskedHeaders = headersJson != null ? EmailMasker.mask(headersJson) : null;

        log.error("WEBHOOK_FAILURE eventId={} eventType={} subscriptionId={} errorMessage={}",
                eventId, eventType, subscriptionId, errorMessage);

        WebhookFailure failure = new WebhookFailure();
        failure.setEventId(eventId);
        failure.setEventType(eventType);
        failure.setSubscriptionId(subscriptionId);
        failure.setPayloadJson(maskedPayload);
        failure.setHeadersJson(maskedHeaders);
        failure.setErrorMessage(errorMessage);

        repo.save(failure);
    }

    // --------------------------------------------------------------------
    // CONVENIENCE OVERLOAD (simple version)
    // For: logFailure(reason, details, rawPayload)
    // --------------------------------------------------------------------
    public void logFailure(String reason,
                           String details,
                           String rawPayload) {

        log.warn("WEBHOOK_FAILURE_SIMPLE reason={} details={}", reason, details);

        logFailure(
                "UNKNOWN",         // eventId
                reason,            // eventType
                "N/A",             // subscriptionId
                rawPayload,        // payloadJson
                "N/A",             // headersJson
                details            // errorMessage
        );
    }


    public List<WebhookFailure> findRecentFailures(int hours) {
        LocalDateTime since = LocalDateTime.now().minusHours(hours);
        return repo.findByCreatedAtAfterOrderByCreatedAtDesc(since);
    }

    public List<WebhookFailure> findAll() {
        return repo.findAllByOrderByCreatedAtDesc();
    }

}
