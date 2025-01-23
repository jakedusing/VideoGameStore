import java.sql.Connection;

public class Main {

    public static void main(String[] args) {

        try (Connection connection = DatabaseConfig.getConnection()) {
            System.out.println("Connected to the database from Main!");
        } catch (Exception e) {
            System.err.println("Failed to connect to the database.");
            e.printStackTrace();
        }
    }
}