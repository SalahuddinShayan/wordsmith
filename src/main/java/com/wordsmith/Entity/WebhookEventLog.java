package com.wordsmith.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import java.time.ZonedDateTime;

@Entity
@Table(name = "webhook_event_log")
public class WebhookEventLog {

    @Id
    @Column(nullable = false, unique = true)
    private String eventId;

    private String eventType;

    private ZonedDateTime receivedAt;

    private boolean processedSuccessfully;

    public WebhookEventLog() {}

    public WebhookEventLog(String eventId, String eventType) {
        this.eventId = eventId;
        this.eventType = eventType;
        this.receivedAt = ZonedDateTime.now();
    }

    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }

    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }

    public ZonedDateTime getReceivedAt() { return receivedAt; }
    public void setReceivedAt(ZonedDateTime receivedAt) { this.receivedAt = receivedAt; }
    public boolean isProcessedSuccessfully() { return processedSuccessfully; }
    public void setProcessedSuccessfully(boolean processedSuccessfully) { this.processedSuccessfully = processedSuccessfully; } 
}
