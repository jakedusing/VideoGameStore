import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VideoGameService {
    private final Connection connection;

    public VideoGameService(Connection connection) {
        this.connection = connection;
    }

    public void addVideoGame(VideoGame videoGame) {
        // SQL query to insert the video game
        String query = "INSERT INTO games (title, genre, platform, price, stock, release_date, developer, publisher) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

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

    public int getGameId(String title, String platform) {
        String query = "SELECT game_id FROM games WHERE title = ? AND platform = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, platform);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("game_id");
                } else {
                    System.out.println("No game found with the given title and platform");
                    return -1; // return -1 if no game is found
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1; // return -1 if an error occurs
        }
    }
}
