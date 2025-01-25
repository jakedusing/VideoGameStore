import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerService {

    private final Connection connection;

    public CustomerService(Connection connection) {
        this.connection = connection;
    }

    public void addCustomer(Customer customer) {
        String query = "INSERT INTO customer (first_name, last_name, email, phone_number) " +
                "VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // set parameters based on Customer object
            preparedStatement.setString(1, customer.getFirstName());
            preparedStatement.setString(2, customer.getLastName());
            preparedStatement.setString(3, customer.getEmail());
            preparedStatement.setString(4, customer.getPhoneNumber());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Successfully added new customer: " +
                        customer.getFirstName() + " " + customer.getLastName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Customer getCustomerById(int customerId) throws SQLException {
        String query = "SELECT * FROM customer WHERE customer_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, customerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Customer(
                        resultSet.getInt("customer_id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("email"),
                        resultSet.getString("phone_number"),
                        resultSet.getTimestamp("created_at")
                );
            }
        }
        return null;
    }

    public List<Customer> getAllCustomers() throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM customer";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                customers.add(new Customer(
                        resultSet.getInt("customer_id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("email"),
                        resultSet.getString("phone_number"),
                        resultSet.getTimestamp("created_at")
                ));
            }
        }
        return customers;
    }
}
