package com.akshay.urlshortner.dto;

import java.time.LocalDateTime;

public class UrlMappingDTO {

    private Long id;
    private String OriginalUrl;
    private String shortUrl;
    private int clickCount;
    private LocalDateTime createdDate;
    private String username;

    public UrlMappingDTO(){

    }

    public UrlMappingDTO(Long id, String originalUrl, String shortUrl, int clickCount, LocalDateTime createdDate, String username) {
        this.id = id;
        this.OriginalUrl = originalUrl;
        this.shortUrl = shortUrl;
        this.clickCount = clickCount;
        this.createdDate = createdDate;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginalUrl() {
        return OriginalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        OriginalUrl = originalUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }



}
