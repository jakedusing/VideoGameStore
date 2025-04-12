package com.jd.dto;

public class TopGameDTO {
    private String title;
    private int totalSold;

    public TopGameDTO(String title, int totalSold) {
        this.title = title;
        this.totalSold = totalSold;
    }

    public String getTitle() {
        return title;
    }

    public int getTotalSold() {
        return totalSold;
    }
}
