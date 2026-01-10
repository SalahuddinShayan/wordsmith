package com.wordsmith.Entity;

import jakarta.persistence.*;
import java.time.LocalTime;
import java.time.ZonedDateTime;

import com.wordsmith.Enum.ReleaseType;

@Entity
@Table(
    name = "novel_release_policy",
    uniqueConstraints = @UniqueConstraint(columnNames = "novel_id")
)
public class NovelReleasePolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String novelName;

    @Column(nullable = false)
    private boolean active = true;

    @Column(nullable = false)
    private int chaptersPerRelease;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReleaseType releaseType;
    // DAILY | WEEKLY | CUSTOM

    @Column
    private Integer customIntervalDays;
    // Used only if releaseType == CUSTOM

    @Column(nullable = false)
    private int releaseHour;

    @Column(nullable = false)
    private int minStockpileRequired;

    @Column
    private ZonedDateTime lastReleaseAt;

    @Column(nullable = false, updatable = false)
    private ZonedDateTime createdAt = ZonedDateTime.now();

    @Column(nullable = false)
    private ZonedDateTime updatedAt = ZonedDateTime.now();

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = ZonedDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNovelName() {
        return novelName;
    }

    public void setNovelName(String novelName) {
        this.novelName = novelName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getChaptersPerRelease() {
        return chaptersPerRelease;
    }

    public void setChaptersPerRelease(int chaptersPerRelease) {
        this.chaptersPerRelease = chaptersPerRelease;
    }

    public ReleaseType getReleaseType() {
        return releaseType;
    }

    public void setReleaseType(ReleaseType releaseType) {
        this.releaseType = releaseType;
    }

    public Integer getCustomIntervalDays() {
        return customIntervalDays;
    }

    public void setCustomIntervalDays(Integer customIntervalDays) {
        this.customIntervalDays = customIntervalDays;
    }

    public int getReleaseHour() {
        return releaseHour;
    }

    public void setReleaseHour(int releaseHour) {
        this.releaseHour = releaseHour;
    }

    public int getMinStockpileRequired() {
        return minStockpileRequired;
    }

    public void setMinStockpileRequired(int minStockpileRequired) {
        this.minStockpileRequired = minStockpileRequired;
    }

    public ZonedDateTime getLastReleaseAt() {
        return lastReleaseAt;
    }

    public void setLastReleaseAt(ZonedDateTime lastReleaseAt) {
        this.lastReleaseAt = lastReleaseAt;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // getters & setters
    
}
