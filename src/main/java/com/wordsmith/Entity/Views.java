package com.wordsmith.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Views {
	
	@Id
	@Column
	private long ChapterId;
	
	@Column
	private long views;

	public long getChapterId() {
		return ChapterId;
	}

	public void setChapterId(long chapterId) {
		ChapterId = chapterId;
	}

	public long getViews() {
		return views;
	}

	public void setViews(long views) {
		this.views = views;
	}
	
	

}
