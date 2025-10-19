package com.wordsmith.Entity;

import java.time.LocalDateTime;

import com.wordsmith.Enum.LikeEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "announcements")
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String postedBy;

    private boolean visible = true;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = LocalDateTime.now();

    @Transient // ✅ Not stored in the database
    private String timeAgo;

	@Transient
	private Long views;

	@Transient
	private LikeEnum userReaction;
	
	@Transient
	private long loveCount; // For storing the number of LOVE reactions

	@Transient
	private long angryCount; // For storing the number of ANGRY reactions

	@Transient
	private long laughCount; // For storing the number of LAUGH reactions

	@Transient
	private long sadCount; // For storing the number of SAD reactions

	@Transient
	private long wowCount; // For storing the number of WOW reactions

	@Transient
	private long fireCount; // For storing the number of FIRE reactions

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getTimeAgo() {
        return timeAgo;
    }

    public void setTimeAgo(String timeAgo) {
        this.timeAgo = timeAgo;
    }

    public Long getViews() {
        return views;
    }

    public void setViews(Long views) {
        this.views = views;
    }

    public LikeEnum getUserReaction() {
        return userReaction;
    }

    public void setUserReaction(LikeEnum userReaction) {
        this.userReaction = userReaction;
    }

    public long getLoveCount() {
        return loveCount;
    }

    public void setLoveCount(long loveCount) {
        this.loveCount = loveCount;
    }

    public long getAngryCount() {
        return angryCount;
    }

    public void setAngryCount(long angryCount) {
        this.angryCount = angryCount;
    }

    public long getLaughCount() {
        return laughCount;
    }

    public void setLaughCount(long laughCount) {
        this.laughCount = laughCount;
    }

    public long getSadCount() {
        return sadCount;
    }

    public void setSadCount(long sadCount) {
        this.sadCount = sadCount;
    }

    public long getWowCount() {
        return wowCount;
    }

    public void setWowCount(long wowCount) {
        this.wowCount = wowCount;
    }

    public long getFireCount() {
        return fireCount;
    }

    public void setFireCount(long fireCount) {
        this.fireCount = fireCount;
    }

    
    
}

