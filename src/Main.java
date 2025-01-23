import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) {

       /* VideoGame newGame = new VideoGame("The Legend of Zelda: Tears of the Kingdom", "Action-Adventure",
                "Nintendo Switch", 59.99, 20, "2023-05-12", "Nintendo", "Nintendo");

        // Create a VideoGameService instance to add the game
        VideoGameService videoGameService = new VideoGameService();
        videoGameService.addVideoGame(newGame);*/

        try (Connection connection = DatabaseConfig.getConnection()) {
            System.out.println("Connected to the database from Main!");
            Statement statement = connection.createStatement();

            String query = "SELECT * FROM games";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                System.out.println("Game ID: " + resultSet.getInt("game_id"));
                System.out.println("Title: " + resultSet.getString("title"));
            }
        } catch (Exception e) {
            System.err.println("Failed to connect to the database.");
            e.printStackTrace();
        }


    }
}