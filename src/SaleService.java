import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SaleService {
    private final Connection connection;

    public SaleService(Connection connection) {
        this.connection = connection;
    }

    public void addSale(Sale sale) {

        // SQL query to insert the sale into the sales table
        String query = "INSERT INTO sales (game_id, employee_id, quantity, total_price) " +
                "VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // set parameters based on Sale object
            preparedStatement.setInt(1, sale.getGameId());
            preparedStatement.setInt(2, sale.getEmployeeId());
            preparedStatement.setInt(3, sale.getQuantity());
            preparedStatement.setDouble(4, sale.getTotalPrice());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Successfully added new sale!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
                            resultSet.getDouble("total_price")
                    ));

                }
            }
        }
        return sales;
    }
}
