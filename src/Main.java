import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Connection connection = DatabaseConfig.getConnection();

        try (connection) {

            VideoGameService videoGameService = new VideoGameService(connection);
            EmployeeService employeeService = new EmployeeService(connection);
            SaleService saleService = new SaleService(connection);

            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter employee email:");
            String employeeEmail = scanner.nextLine();
            int employeeId = employeeService.getEmployeeId(employeeEmail);
            if (employeeId == -1) {
                System.out.println("Employee not found. Exiting.");
                return;
            }

            System.out.println("Enter game title:");
            String gameTitle = scanner.nextLine();
            System.out.println("Enter game platform:");
            String gamePlatform = scanner.nextLine();
            int gameId = videoGameService.getGameId(gameTitle, gamePlatform);
            if (gameId == 1) {
                System.out.println("Game not found. Exiting.");
                return;
            }

            // fetch game details (price and stock)
            System.out.println("Enter quantity to purchase");
            int quantity = scanner.nextInt();

            int stock = getGameStock(connection, gameId);
            double price = getGamePrice(connection, gameId);

            if (stock < quantity) {
                System.out.println("Not enough stock available. Exiting");
                return;
            }

            double totalPrice = price * quantity;

            updateGameStock(connection, gameId, stock - quantity);

            // Create a Sale object and add to the database
            Sale sale = new Sale(gameId, employeeId, quantity, totalPrice);
            saleService.addSale(sale);

            System.out.println("Sale completed successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }


        /*Employee newEmployee = new Employee("Joe", "Dirt", "joedirt@gmail.com",
                "312-777-8888", "2022-05-15", 21.00);

        EmployeeService employeeService = new EmployeeService();
        employeeService.addEmployee(newEmployee);*/

       /* try (Connection connection = DatabaseConfig.getConnection()) {
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
        }*/
    }

    private static int getGameStock(Connection connection, int gameId) throws SQLException {
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

    private static double getGamePrice(Connection connection, int gameId) throws SQLException {
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

    private static void updateGameStock(Connection connection, int gameId, int newStock) throws SQLException {
        String query = "UPDATE games SET stock = ? WHERE game_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, newStock);
            preparedStatement.setInt(2, gameId);
            preparedStatement.executeUpdate();
        }
    }
}