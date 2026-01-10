package com.wordsmith.Entity;

import java.util.Date;
import java.util.List;

import com.wordsmith.Dto.ReleasedChapterDTO;
import com.wordsmith.Dto.StockpileChapterDTO;
import com.wordsmith.Enum.LikeEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Transient;

@Entity
public class Novel {
	
	@Id
	@Column
	private int NovelId;
	
	@Column(unique=true)
	private String NovelName;
	
	@Lob
	@Column(columnDefinition = "MEDIUMBLOB")
	private byte[] NovelImage;
	
	@Column
	private String OriginalLanguage;
	
	@Column
	private String Genre;
	
	@Column
	private String Author;
	
	@Column(columnDefinition = "text")
	private String Description;
	
	@Column
	private Date AddedOn;
	
	@Column(columnDefinition = "text")
	private String Keywords;
	
	@Column
	private String Status;

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

	@Transient
	private long favoriteCount; // For storing the number of times favorited

	@Transient
	private boolean favorited; // For storing if the current user has favorited this novel

	@Transient
	private long latestChapterId; // For storing the ID of the latest chapter

	@Transient
	private String latestChapterNo; // For storing the chapter number of the latest chapter

	@Transient
	private List<ReleasedChapterDTO> recentChapters; // For storing recent chapters as DTOs

	@Transient
	private List<StockpileChapterDTO> stockpileChapters; // For storing stockpiled chapters as DTOs

	public int getNovelId() {
		return NovelId;
	}

	public void setNovelId(int novelId) {
		NovelId = novelId;
	}

	public String getNovelName() {
		return NovelName;
	}

	public void setNovelName(String novelName) {
		NovelName = novelName;
	}

	public byte[] getNovelImage() {
		return NovelImage;
	}

	public void setNovelImage(byte[] novelImage) {
		NovelImage = novelImage;
	}

	public String getOriginalLanguage() {
		return OriginalLanguage;
	}

	public void setOriginalLanguage(String originalLanguage) {
		OriginalLanguage = originalLanguage;
	}

	public String getGenre() {
		return Genre;
	}

	public void setGenre(String genre) {
		Genre = genre;
	}


	public String getAuthor() {
		return Author;
	}

	public void setAuthor(String author) {
		Author = author;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public Date getAddedOn() {
		return AddedOn;
	}

	public void setAddedOn(Date addedOn) {
		AddedOn = addedOn;
	}

	public String getKeywords() {
		return Keywords;
	}

	public void setKeywords(String keywords) {
		Keywords = keywords;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
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

	public long getFavoriteCount() {
		return favoriteCount;
	}

	public void setFavoriteCount(long favoriteCount) {
		this.favoriteCount = favoriteCount;
	}

	public boolean isFavorited() {
		return favorited;
	}

	public void setFavorited(boolean favorited) {
		this.favorited = favorited;
	}

	public long getLatestChapterId() {
		return latestChapterId;
	}

	public void setLatestChapterId(long latestChapterId) {
		this.latestChapterId = latestChapterId;
	}

	public String getLatestChapterNo() {
		return latestChapterNo;
	}

	public void setLatestChapterNo(String latestChapterNo) {
		this.latestChapterNo = latestChapterNo;
	}

	public List<ReleasedChapterDTO> getRecentChapters() {
		return recentChapters;
	}

	public void setRecentChapters(List<ReleasedChapterDTO> recentChapters) {
		this.recentChapters = recentChapters;
	}

	public List<StockpileChapterDTO> getStockpileChapters() {
		return stockpileChapters;
	}

	public void setStockpileChapters(List<StockpileChapterDTO> stockpileChapters) {
		this.stockpileChapters = stockpileChapters;
	}
	
	

}
