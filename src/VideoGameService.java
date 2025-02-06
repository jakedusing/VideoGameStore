import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public int getGameId(String title) {
        String query = "SELECT game_id FROM games WHERE title = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, title);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("game_id");
                } else {
                    System.out.println("No game found with that title");
                    return -1;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public String getGameTitle(int gameId) {
        String query = "SELECT title FROM games WHERE game_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, gameId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("title");
                } else {
                    System.out.println("No game found with that ID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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

    public List<VideoGame> getAllGames() throws SQLException {
        List<VideoGame> games = new ArrayList<>();
        String query = "SELECT * from games";
        try (Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                games.add(new VideoGame(
                        resultSet.getInt("game_id"),
                        resultSet.getString("title"),
                        resultSet.getString("genre"),
                        resultSet.getString("platform"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("stock"),
                        resultSet.getString("release_date"),
                        resultSet.getString("developer"),
                        resultSet.getString("publisher"),
                        resultSet.getTimestamp("created_at")
                ));
            }
        }
        return games;
    }

    public List<VideoGame> getGamesPerPlatform(String platform) throws SQLException {
        List<VideoGame> games = new ArrayList<>();
        String query = "SELECT * FROM games WHERE platform = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, platform);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    games.add(new VideoGame(
                            resultSet.getInt("game_id"),
                            resultSet.getString("title"),
                            resultSet.getString("genre"),
                            resultSet.getString("platform"),
                            resultSet.getDouble("price"),
                            resultSet.getInt("stock"),
                            resultSet.getString("release_date"),
                            resultSet.getString("developer"),
                            resultSet.getString("publisher"),
                            resultSet.getTimestamp("created_at")

                    ));
                }
            }
            return games;
        }
    }

    public int getGameStock(int gameId) throws SQLException {
        String query = "SELECT stock from games WHERE game_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, gameId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("stock");
                }
            }
        }
        return 0;
    }

    public double getGamePrice(int gameId) throws SQLException {
        String query = "SELECT price FROM games WHERE game_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, gameId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDouble("price");
                }
            }
        }
        return 0.0;
    }

    public double getGamePrice(String gameTitle) throws SQLException {
        String query = "SELECT price FROM games WHERE title = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, gameTitle);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDouble("price");
                }
            }
        }
        return 0.0;
    }

    public void updateGameStock(int gameId, int newStock) throws SQLException {
        String query = "UPDATE games SET stock = ? WHERE game_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, newStock);
            preparedStatement.setInt(2, gameId);
            preparedStatement.executeUpdate();
        }
    }
}
