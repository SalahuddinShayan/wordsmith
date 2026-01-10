package com.wordsmith.Dto;

public class StockpileChapterDTO {

    private Long chapterId;
    private String chapterNo;
    private String title;
    private String timeAgo;
    private Long views;
    private boolean owned;
    private int priceCoins;
    
    
    
    
    
    
    public Long getChapterId() {
        return chapterId;
    }
    public void setChapterId(Long chapterId) {
        this.chapterId = chapterId;
    }
    public String getChapterNo() {
        return chapterNo;
    }
    public void setChapterNo(String chapterNo) {
        this.chapterNo = chapterNo;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
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
    public boolean isOwned() {
        return owned;
    }
    public void setOwned(boolean owned) {
        this.owned = owned;
    }
    public int getPriceCoins() {
        return priceCoins;
    }
    public void setPriceCoins(int priceCoins) {
        this.priceCoins = priceCoins;
    }

}
