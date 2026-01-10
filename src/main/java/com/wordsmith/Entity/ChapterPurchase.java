package com.wordsmith.Entity;

import jakarta.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(
    name = "chapter_purchases",
    uniqueConstraints = @UniqueConstraint(columnNames = {"username", "chapter_id"})
)
public class ChapterPurchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(name = "chapter_id", nullable = false)
    private Long chapterId;

    @Column(nullable = false)
    private Long price; // coins spent

    @Column(nullable = false)
    private ZonedDateTime purchasedAt = ZonedDateTime.now();

    @Column(nullable = false)
    private String novelName;

    @Column(nullable = false)
    private String chapterNo;

    @Transient
    private String timeAgo;
    // --------------------
    // Getters & Setters
    // --------------------

    public Long getId() { return id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public Long getChapterId() { return chapterId; }
    public void setChapterId(Long chapterId) { this.chapterId = chapterId; }

    public Long getPrice() { return price; }
    public void setPrice(Long price) { this.price = price; }

    public ZonedDateTime getPurchasedAt() { return purchasedAt; }
    public void setPurchasedAt(ZonedDateTime purchasedAt) { this.purchasedAt = purchasedAt; }

    public String getNovelName() { return novelName; }
    public void setNovelName(String novelName) { this.novelName = novelName; }

    public String getChapterNo() { return chapterNo; }
    public void setChapterNo(String chapterNo) { this.chapterNo = chapterNo; }

    public String getTimeAgo() { return timeAgo; }
    public void setTimeAgo(String timeAgo) { this.timeAgo = timeAgo; }
}
