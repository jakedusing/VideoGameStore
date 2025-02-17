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

    public List<String> getLeastSellingGames(int limit) throws SQLException {
        List<String> leastSellingGames = new ArrayList<>();
        String query = """
                SELECT games.game_id, games.title, COALESCE(SUM(sales.quantity), 0) AS total_sold
                FROM games
                LEFT JOIN sales ON games.game_id = sales.game_id
                GROUP BY games.game_id, games.title
                ORDER BY total_sold ASC
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
                    leastSellingGames.add(gameInfo);
                }
            }
        }
        return leastSellingGames;
    }

    public void getDailySalesSummary() {
        String query = "SELECT DATE(order_date) AS sale_date, COUNT(*) AS total_sales, SUM(total_price) AS total_revenue " +
                "FROM orders " +
                "GROUP BY DATE(order_date) " +
                "ORDER BY sale_date DESC";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery()) {

            System.out.println("Daily Sales Summary:");
            while (resultSet.next()) {
                String saleDate = resultSet.getString("sale_date");
                int totalSales = resultSet.getInt("total_sales");
                double totalRevenue = resultSet.getDouble("total_revenue");

                System.out.println("Date: " + saleDate + " | Total Sales: " + totalSales + " | Revenue: $" + totalRevenue);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getMonthlySalesSummary() {
        String query = "SELECT DATE_FORMAT(order_date, '%Y-%m') AS sale_month, COUNT(*) AS total_sales, SUM(total_price) AS total_revenue " +
                "FROM orders " +
                "GROUP BY sale_month " +
                "ORDER BY sale_month DESC";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery()) {

            System.out.println("Monthly Sales Summary");
            while (resultSet.next()) {
                String saleMonth = resultSet.getString("sale_month");
                int totalSales = resultSet.getInt("total_sales");
                double totalRevenue = resultSet.getDouble("total_revenue");

                System.out.println("Month: " + saleMonth + " | Total Sales: " + totalSales + " | Revenue: $" + totalRevenue);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
