package com.wordsmith.Entity;

import com.wordsmith.Enum.CommentEntityType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Views {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long viewId;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private CommentEntityType entityType;

	@Column
	private Long entityId;
	
	@Column
	private Long views;

	public Long getViewId() {
		return viewId;
	}

	public void setViewId(Long viewId) {
		this.viewId = viewId;
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

	public Long getViews() {
		return views;
	}

	public void setViews(Long views) {
		this.views = views;
	}

	
	
	

}
