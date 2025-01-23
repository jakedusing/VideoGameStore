public class Main {

    public static void main(String[] args) {

        Employee newEmployee = new Employee("Joe", "Dirt", "joedirt@gmail.com",
                "312-777-8888", "2022-05-15", 21.00);

        EmployeeService employeeService = new EmployeeService();
        employeeService.addEmployee(newEmployee);

       /* VideoGame newGame = new VideoGame("Animal Crossing: New Horizons", "Simulation",
                "Nintendo Switch", 49.99, 6, "2020-03-20", "Nintendo", "Nintendo");

        // Create a VideoGameService instance to add the game
        VideoGameService videoGameService = new VideoGameService();
        videoGameService.addVideoGame(newGame);*/

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