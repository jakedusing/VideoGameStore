import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SaleService {
    private final Connection connection;

    public SaleService(Connection connection) {
        this.connection = connection;
    }

    public void addSale(Order order, VideoGameService videoGameService) throws SQLException {
        try {
            // first calulate total cost of the order
            for (Sale sale : order.getSales()) {
                int currentStock = videoGameService.getGameStock(sale.getGameId());

                if (currentStock < sale.getQuantity()) {
                    System.out.println("Not enough stock available for this game");
                    return; // exit if not enough stock
                }

                double price = videoGameService.getGamePrice(sale.getGameId());
                if (price <= 0) {
                    System.out.println("Error: Could not retrieve game price");
                    return;
                }

                double totalPrice = price * sale.getQuantity();
                order.addToOrderTotal(totalPrice);
            }
                // Insert the order into the database
                String orderQuery = "INSERT INTO orders (customer_id, employee_id, total_price) VALUES (?, ?, ?)";
                int orderId = -1;

                try (PreparedStatement orderStatement = connection.prepareStatement(orderQuery, Statement.RETURN_GENERATED_KEYS)) {
                    orderStatement.setInt(1, order.getCustomerId());
                    orderStatement.setInt(2, order.getEmployeeId());
                    orderStatement.setDouble(3, order.getOrderTotal());
                    int orderRows = orderStatement.executeUpdate();

                    if (orderRows > 0) {
                        try (ResultSet generatedKeys = orderStatement.getGeneratedKeys()) {
                            if (generatedKeys.next()) {
                                orderId = generatedKeys.getInt(1);
                            }
                        }
                    }
                }

                if (orderId == -1) {
                    System.out.println("Failed to insert order");
                    return;
                }

                // Insert each sale that was in the order
                for (Sale sale : order.getSales()) {
                    double price = videoGameService.getGamePrice(sale.getGameId());
                    double totalPrice = price * sale.getQuantity();

                    String saleQuery = "INSERT INTO sales (sale_id, order_id, game_id, quantity, price) " +
                            "VALUES (?, ?, ?, ?, ?)";

                    try (PreparedStatement saleStatement = connection.prepareStatement(saleQuery)) {
                        saleStatement.setInt(1, sale.getSaleId());
                        saleStatement.setInt(2, orderId);
                        saleStatement.setInt(3, sale.getGameId());
                        saleStatement.setInt(4, sale.getQuantity());
                        saleStatement.setDouble(5, totalPrice);

                        int saleRows = saleStatement.executeUpdate();
                        if (saleRows > 0) {
                            System.out.println("Successfully added new sale!");

                            // Deduct stock
                            int currentStock = videoGameService.getGameStock(sale.getGameId());
                            int newStock = currentStock - sale.getQuantity();
                            videoGameService.updateGameStock(sale.getGameId(), newStock);
                        }
                    }
                }

            System.out.println("Total order cost: $" + order.getOrderTotal());
            } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*public void addSale(Order order, VideoGameService videoGameService) {
        try {
            // Iterate over each sale in the order
            for (Sale sale : order.getSales()) {
                // Check available stock for each game
                int currentStock = videoGameService.getGameStock(sale.getGameId());

                if (currentStock < sale.getQuantity()) {
                    System.out.println("Not enough stock available for this game.");
                    return; // exit the function if not enough stock
                }

                // Retrieve the price of the game
                double price = videoGameService.getGamePrice(sale.getGameId());
                if (price <= 0) {
                    System.out.println("Error: Could not retrieve game price.");
                    return;
                }

                // calculate total price
                double totalPrice = price * sale.getQuantity();
                order.addToOrderTotal(totalPrice);

                // SQL query to insert the sale into the sales table
                String query = "INSERT INTO sales (game_id, employee_id, quantity, total_price, customer_id) " +
                        "VALUES (?, ?, ?, ?, ?)";

                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setInt(1, sale.getGameId());
                    preparedStatement.setInt(2, sale.getEmployeeId());
                    preparedStatement.setInt(3, sale.getQuantity());
                    preparedStatement.setDouble(4, totalPrice);
                    preparedStatement.setInt(5, sale.getCustomerId());

                    int rowsAffected = preparedStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Successfully added new sale!");

                        // deduct the sold quantity from stock
                        int newStock = currentStock - sale.getQuantity();
                        videoGameService.updateGameStock(sale.getGameId(), newStock);
                    }
                }
            }

            System.out.println("Total order cost: $" + order.getOrderTotal());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    } */

    public List<Sale> getAllSales() throws SQLException {
        List<Sale> sales = new ArrayList<>();
        String query = "SELECT * from sales";
        try (Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                sales.add(new Sale(
                        resultSet.getInt("sale_id"),
                        resultSet.getInt("game_id"),
                        resultSet.getInt("employee_id"),
                        resultSet.getInt("quantity"),
                        resultSet.getDouble("total_price"),
                        resultSet.getTimestamp("sale_date"),
                        resultSet.getInt("customer_id")
                ));
            }
        }
        return sales;
    }

    public List<Sale> getSalesByCustomer(int customerId) throws SQLException{
        List<Sale> sales = new ArrayList<>();
        String query = "SELECT * FROM sales WHERE customer_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, customerId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()) {
                    sales.add(new Sale(
                            resultSet.getInt("sale_id"),
                            resultSet.getInt("game_id"),
                            resultSet.getInt("employee_id"),
                            resultSet.getInt("quantity"),
                            resultSet.getDouble("total_price"),
                            resultSet.getTimestamp("sale_date"),
                            resultSet.getInt("customer_id")
                    ));
                }
            }
        }
        return sales;
    }

    public List<Sale> getSalesByEmployee(int employeeId) throws SQLException {
        List<Sale> sales = new ArrayList<>();
        String query = "SELECT * FROM sales WHERE employee_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, employeeId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()) {
                    sales.add(new Sale(
                            resultSet.getInt("game_id"),
                            resultSet.getInt("employee_id"),
                            resultSet.getInt("quantity"),
                            resultSet.getDouble("total_price"),
                            resultSet.getInt("customer_id")
                    ));

                }
            }
        }
        return sales;
    }

    public List<String> getTopSellingGames(int limit) throws SQLException {
        List<String> topSellingGames = new ArrayList<>();
        String query = """
                SELECT games.game_id, games.title, SUM(sales.quantity) AS total_sold
                FROM sales
                JOIN games ON sales.game_id = games.game_id
                GROUP BY games.game_id, games.title
                ORDER BY total_sold DESC
                LIMIT ?
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, limit);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String gameInfo = String.format(
                            "Game ID: %d, Title: %s, Total Sold: %d",
                            resultSet.getInt("game_id"),
                            resultSet.getString("title"),
                            resultSet.getInt("total_sold")
                    );
                    topSellingGames.add(gameInfo);
                }
            }
        }
        return topSellingGames;
    }
}
