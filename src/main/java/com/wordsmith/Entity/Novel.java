package com.wordsmith.Entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

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
	
	

}
