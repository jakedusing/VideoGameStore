import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class VideoGameService {

    public void addVideoGame(VideoGame videoGame) {
        // SQL query to insert the video game
        String query = "INSERT INTO games (title, genre, platform, price, stock, release_date, developer, publisher) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // set parameters based on the VideoGame object
            preparedStatement.setString(1, videoGame.getTitle());
            preparedStatement.setString(2, videoGame.getGenre());
            preparedStatement.setString(3, videoGame.getPlatform());
            preparedStatement.setDouble(4, videoGame.getPrice());
            preparedStatement.setInt(5, videoGame.getStock());
            preparedStatement.setString(6, videoGame.getReleaseDate());
            preparedStatement.setString(7, videoGame.getDeveloper());
            preparedStatement.setString(8, videoGame.getPublisher());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Video game added successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
