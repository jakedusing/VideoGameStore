import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeService {
    private final Connection connection;

    public EmployeeService(Connection connection) {
        this.connection = connection;
    }

    public void addEmployee(Employee employee) {

        // sql query to insert the employee to the employee table
        String query = "INSERT INTO employee (first_name, last_name, email, phone_number, hire_date, salary) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // set parameters based on Employee object
            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setString(3, employee.getEmail());
            preparedStatement.setString(4, employee.getPhoneNumber());
            preparedStatement.setString(5, employee.getHireDate());
            preparedStatement.setDouble(6, employee.getSalary());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Successfully added new employee "
                        + employee.getFirstName() + " " + employee.getLastName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getEmployeeId(String email) {
        String query = "SELECT employee_id FROM employee WHERE email = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("employee_id");
                } else {
                    System.out.println("No employee found with the given email");
                    return -1;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
