import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EmployeeService {

    public void addEmployee(Employee employee) {

        // sql query to insert the employee to the employee table
        String query = "INSERT INTO employee (first_name, last_name, email, phone_number, hire_date, salary) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {

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
}
