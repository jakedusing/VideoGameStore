public class VideoGame {

    private String title;
    private String genre;
    private String platform;
    private double price;
    private int stock;
    private String releaseDate;
    private String developer;
    private String publisher;

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
}
