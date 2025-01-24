import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
