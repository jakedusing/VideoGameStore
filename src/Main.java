import java.sql.Connection;

public class Main {

    public static void main(String[] args) {

        Connection connection = DatabaseConfig.getConnection();
        EmployeeService employeeService = new EmployeeService(connection);

        String email = "joedirt@gmail.com";
        int employeeId = employeeService.getEmployeeId(email);

        if (employeeId != -1) {
            System.out.println("Employee ID for " + email + ": " + employeeId);
        } else {
            System.out.println("Employee not found.");
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
}