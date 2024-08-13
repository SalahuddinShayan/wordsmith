package com.wordsmith.Entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Chapter {
	
	@Id
	@Column
	@GeneratedValue
	private long ChapterId;
	
	@Column
	private String NovelName;
	
	@Column
	private String ChapterNo;
	
	@Column
	private String Title;
	
	@Column
	private String Keywords;
	
	@Column
	private Date PostedOn;
	
	@Column(columnDefinition="longtext")
	private String Content;

	public long getChapterId() {
		return ChapterId;
	}

	public void setChapterId(long chapterId) {
		ChapterId = chapterId;
	}

	public String getNovelName() {
		return NovelName;
	}

	public void setNovelName(String novelName) {
		NovelName = novelName;
	}

	public String getChapterNo() {
		return ChapterNo;
	}

	public void setChapterNo(String chapterNo) {
		ChapterNo = chapterNo;
	}

	public String getKeywords() {
		return Keywords;
	}

	public void setKeywords(String keywords) {
		Keywords = keywords;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	public Date getPostedOn() {
		return PostedOn;
	}

	public void setPostedOn(Date postedOn) {
		PostedOn = postedOn;
	}
	
	

}
