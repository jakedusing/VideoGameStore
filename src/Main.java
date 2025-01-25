import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {

        Connection connection = DatabaseConfig.getConnection();

        try (connection) {


            VideoGameService videoGameService = new VideoGameService(connection);
            EmployeeService employeeService = new EmployeeService(connection);
            SaleService saleService = new SaleService(connection);
            CustomerService customerService = new CustomerService(connection);

            /*List<Customer> customers = new ArrayList<>(List.of(
                    new Customer("Chris", "Evans", "chrisevans@marvel.com", "555-123-4567"),
                    new Customer("Emma", "Stone", "emmastone@hollywood.com", "555-234-5678"),
                    new Customer("LeBron", "James", "lebronjames@nba.com", "555-345-7890"),
                    new Customer("Zendaya", "Coleman", "zendaya@hollywood.com", "555-567-8901"),
                    new Customer("Ryan", "Reynolds", "ryanreynolds@deadpool.com", "555-678-9012"),
                    new Customer("Simone", "Biles", "simonebiles@gymnastics.com", "555-789-0123"),
                    new Customer("Tom", "Holland", "tomholland@spiderman.com", "555-890-1234"),
                    new Customer("Adele", "Laurie", "adelelaurie@music.com", "555-901-2345"),
                    new Customer("Serena", "Williams", "serenawilliams@tennis.com", "555-012-3456"),
                    new Customer("Olivia", "Rodrigo", "oliviar@music.com", "555-831-3389")));

            for (Customer customer : customers) {
                customerService.addCustomer(customer);
            }*/

            // customerService.getAllCustomers().forEach(System.out::println);
            // videoGameService.getAllGames().forEach(System.out::println);

            /*Customer newCustomer = new Customer("Taylor", "Swift", "tswift@gmail.com", "111-234-5678");
            customerService.addCustomer(newCustomer);*/

            /*Employee newEmployee = new Employee("Joe", "Dirt", "joedirt@gmail.com",
                "312-777-8888", "2022-05-15", 21.00);

            EmployeeService employeeService = new EmployeeService();
            employeeService.addEmployee(newEmployee);*/




            /*Scanner scanner = new Scanner(System.in);

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

            System.out.println("Sale completed successfully!");*/
        } catch (SQLException e) {
            e.printStackTrace();
        }

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