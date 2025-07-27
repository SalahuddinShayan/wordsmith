package com.wordsmith.Entity;

import java.time.ZonedDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.wordsmith.Enum.CommentEntityType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "comments")
public class Comment {
    
	@Id
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CommentEntityType entityType; // CHAPTER, NOVEL, ANNOUNCEMENT, COMMENT

    @Column(nullable = false)
    private Long entityId;

    @Column(name = "user_name", nullable = false)
    private String userName;


    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(nullable = false)
    private boolean hasReplies = false;

    @Column(nullable = false)
    private boolean flagged = false; // For moderation

    @CreationTimestamp
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    private ZonedDateTime updatedAt;
    
    @Transient // ✅ Not stored in the database
    private String timeAgo;

	@Transient
	private List<Comment> replies; // For nested comments

	public Comment() {
		// Default constructor
	}

	public Comment(CommentEntityType entityType, Long entityId, String userName, String content) {
		this.entityType = entityType;
		this.entityId = entityId;
		this.userName = userName;
		this.content = content;
	}

	// Getters and Setters



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CommentEntityType getEntityType() {
		return entityType;
	}

	public void setEntityType(CommentEntityType entityType) {
		this.entityType = entityType;
	}

	public Long getEntityId() {
		return entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isHasReplies() {
		return hasReplies;
	}

	public void setHasReplies(boolean hasReplies) {
		this.hasReplies = hasReplies;
	}

	public boolean isFlagged() {
		return flagged;
	}

	public void setFlagged(boolean flagged) {
		this.flagged = flagged;
	}

	public ZonedDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(ZonedDateTime instant) {
		this.createdAt = instant;
	}

	public ZonedDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(ZonedDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getTimeAgo() {
		return timeAgo;
	}

	public void setTimeAgo(String timeAgo) {
		this.timeAgo = timeAgo;
	}

	public List<Comment> getReplies() {
		return replies;
	}

	public void setReplies(List<Comment> replies) {
		this.replies = replies;
	}
    
    
}