package com.wordsmith.Entity;

import java.time.ZonedDateTime;

import com.wordsmith.Enum.LikeEnum;
import com.wordsmith.Enum.ReleaseStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

@Entity
public class Chapter {
	
	@Id
	@Column
	private long chapterId;
	
	@Column
	private String novelName;
	
	@Column
	private String chapterNo;
	
	@Column
	private String title;
	
	@Column(columnDefinition = "text")
	private String keywords;
	
	@Column
	private ZonedDateTime postedOn;
	
	@Column(columnDefinition="longtext")
	private String content;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "release_status", nullable = false)
	private ReleaseStatus releaseStatus = ReleaseStatus.STOCKPILE;

	@Column
	private ZonedDateTime releasedOn;
	
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
	
	public long getChapterId() {
		return chapterId;
	}

	public void setChapterId(long chapterId) {
		this.chapterId = chapterId;
	}

	public String getNovelName() {
		return novelName;
	}

	public void setNovelName(String novelName) {
		this.novelName = novelName;
	}

	public String getChapterNo() {
		return chapterNo;
	}

	public void setChapterNo(String chapterNo) {
		this.chapterNo = chapterNo;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
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

	public ZonedDateTime getPostedOn() {
		return postedOn;
	}

	public void setPostedOn(ZonedDateTime postedOn) {
		this.postedOn = postedOn;
	}

	public ReleaseStatus getReleaseStatus() {
		return releaseStatus;
	}

	public void setReleaseStatus(ReleaseStatus releaseStatus) {
		this.releaseStatus = releaseStatus;
	}
	
	public ZonedDateTime getReleasedOn() {
		return releasedOn;
	}

	public void setReleasedOn(ZonedDateTime releasedOn) {
		this.releasedOn = releasedOn;
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
