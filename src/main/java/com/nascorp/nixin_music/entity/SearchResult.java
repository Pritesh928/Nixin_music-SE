package com.nascorp.nixin_music.entity;

public class SearchResult {
    private String videoId;
    private String title;
    private String thumbnail;

    public SearchResult(String videoId, String title, String thumbnail) {
        this.videoId = videoId;
        this.title = title;
        this.thumbnail = thumbnail;
    }

    public String getVideoId() {
        return videoId;
    }

    public String getTitle() {
        return title;
    }

    public String getThumbnail() {
        return thumbnail;
    }
    
}
