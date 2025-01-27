import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public List<Employee> getAllEmployees() throws SQLException {
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT * FROM employee";
        try (Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                employees.add(new Employee(
                        resultSet.getInt("employee_id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("email"),
                        resultSet.getString("phone_number"),
                        resultSet.getString("hire_date"),
                        resultSet.getDouble("salary"),
                        resultSet.getTimestamp("created_at")
                ));
            }
        }
        return employees;
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

    // Using the object version of double (Double) so we can check if it's null
    // causes an error if using the primitive type (double)
    public void updateEmployee(int employeeId, String firstName, String lastName,
                               String email, String phone_number, Double salary) throws SQLException {

        // COALESCE ensures that only non-null fields are updated
        String query = "UPDATE employee SET " +
                "first_name = COALESCE(?, first_name), " +
                "last_name = COALESCE(?, last_name), " +
                "email = COALESCE(?, email), " +
                "phone_number = COALESCE(?, phone_number), " +
                "salary = COALESCE(?, salary) " +
                "WHERE employee_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, phone_number);

            if (salary != null) {
                preparedStatement.setDouble(5, salary);
            } else {
                preparedStatement.setNull(5, java.sql.Types.DOUBLE);
            }

            preparedStatement.setInt(6, employeeId);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Employee information updated successfully!");
            } else {
                System.out.println("No employee found with the given ID");
            }
        }
    }
}
