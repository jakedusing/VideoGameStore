package com.jd;

public class VideoGame {

    private int gameId;
    private String title;
    private String genre;
    private String platform;
    private double price;
    private int stock;
    private String releaseDate;
    private String developer;
    private String publisher;
    private java.sql.Timestamp createdAt;

    public VideoGame(int gameId, String title, String genre, String platform, double price,
                     int stock, String releaseDate, String developer, String publisher, java.sql.Timestamp createdAt) {

        this.gameId = gameId;
        this.title = title;
        this.genre = genre;
        this.platform = platform;
        this.price = price;
        this.stock = stock;
        this.releaseDate = releaseDate;
        this.developer = developer;
        this.publisher = publisher;
        this.createdAt = createdAt;
    }

    public VideoGame(String title, String genre, String platform, double price,
                     int stock, String releaseDate, String developer, String publisher) {

        this.title = title;
        this.genre = genre;
        this.platform = platform;
        this.price = price;
        this.stock = stock;
        this.releaseDate = releaseDate;
        this.developer = developer;
        this.publisher = publisher;
    }

    public int getGameId() {
        return gameId;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public String getPlatform() {
        return platform;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getDeveloper() {
        return developer;
    }

    public String getPublisher() {
        return publisher;
    }

    @Override
    public String toString() {
        return "com.jd.VideoGame{" +
                "gameID=" + gameId +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", platform='" + platform + '\'' +
                ", price=$'" + price + '\'' +
                ", stock='" + stock + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", developer='" + developer + '\'' +
                ", publisher='" + publisher + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
