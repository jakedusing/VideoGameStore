package com.jd;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Connection connection = DatabaseConfig.getConnection();
        Scanner scanner = new Scanner(System.in);

        //javax.swing.SwingUtilities.invokeLater(() -> new GameStoreGUI(connection));

        try (connection) {


            VideoGameService videoGameService = new VideoGameService(connection);
            EmployeeService employeeService = new EmployeeService(connection);
            SaleService saleService = new SaleService(connection);
            CustomerService customerService = new CustomerService(connection);

            saleService.getDailySalesSummary();
            saleService.getMonthlySalesSummary();
            List<String> games = saleService.getTopSellingGames(5);
            games.forEach(System.out::println);
            System.out.println("-------------------------------");
            List<String> leastSellingGames = saleService.getLeastSellingGames(10);
            leastSellingGames.forEach(System.out::println);

            //employeeService.getEmployeeSalesReport();

            /*List<com.jd.Sale> history = customerService.getCustomerPurchaseHistory(1);
            for (com.jd.Sale sale : history) {
                System.out.println(sale);
            }*/

            /*List<com.jd.Sale> sales = new ArrayList<>();
            sales.add(new com.jd.Sale(3, 1, 1, 1));
            sales.add(new com.jd.Sale(12, 1, 1, 1));
            com.jd.Order order = new com.jd.Order(sales, 1, 1);
            saleService.addSale(order, videoGameService);*/
           // sales.add(new com.jd.Sale(13, 1, 3, 1));
           // sales.add(new com.jd.Sale(20, 1, 5, 1));
           // com.jd.Order order = new com.jd.Order(sales, 1);

            //saleService.addSale(order, videoGameService);

            /*System.out.print("Enter customer's email:");
            String email = scanner.nextLine();

            int customerId = customerService.getCustomerId(email);

            if (customerId == -1) {
                System.out.println("No customer found with that email.");
            } else {
                List<com.jd.Sale> sales = saleService.getSalesByCustomer(customerId);

                if (sales.isEmpty()) {
                    System.out.println("No sales found for this customer.");
                } else {
                    System.out.println("Sales for customer with email " + email + ":");
                    for (com.jd.Sale sale : sales) {
                        System.out.println("com.jd.Sale ID: " + sale.getSaleId() + ", Date: " + sale.getSaleDate() +
                                ", Amount: " + sale.getTotalPrice() + ", Game bought: " + videoGameService.getGameTitle(sale.getGameId()));
                    }
                }
            }*/


            //System.out.println(customerService.getCustomerByPhoneNumber("111-234-5678"));



            /*com.jd.Sale sale = new com.jd.Sale(7, 1, 8, 2);   // gameid, employeeid, quantity, customerid
            saleService.addSale(sale, videoGameService);*/

            /*List<com.jd.Sale> sales = saleService.getSalesByCustomer(2);
            sales.forEach(System.out::println);
            List<String> topGames = saleService.getTopSellingGames(3);
            topGames.forEach(System.out::println);*/
            /*List<com.jd.Employee> employees = employeeService.getAllEmployees();
            employees.forEach(System.out::println);

            employeeService.updateEmployee(1, "Bob", "Seger",
                    null, null, null);

            List<com.jd.Employee> employees1 = employeeService.getAllEmployees();
            employees.forEach(System.out::println);
            employees1.forEach(System.out::println);*/

            /*List<com.jd.Customer> customers = customerService.getAllCustomers();
            customers.forEach(System.out::println);

            boolean updated = customerService.updateCustomerInfo(
                    1,
                    null,
                    null,
                    "tayswift13@gmail.com",
                    null
            );
            if (updated) {
                System.out.println("com.jd.Customer info updated successfully!");
            } else {
                System.out.println("No updates were made, check the customer ID");
            }

            System.out.println(customers.get(0));
            List<com.jd.Customer> customers1 = customerService.getAllCustomers();
            System.out.println(customers1.get(0));*/

            /*List<com.jd.VideoGame> games = new ArrayList<>(List.of(
                    new com.jd.VideoGame("Final Fantasy XVI", "RPG", "PS5", 69.99, 12,
                            "2022-09-09", "Square Enix", "Square Enix"),
                    new com.jd.VideoGame("Red Dead Redemption 2", "Action-Adventure", "Xbox One", 39.99, 14,
                            "2018-10-26", "Rockstar Games", "Rockstar Games"),
                    new com.jd.VideoGame("Resident Evil Village", "Action-Adventure", "PS5", 59.99, 9,
                            "2021-05-05", "Capcom", "Capcom"),
                    new com.jd.VideoGame("Mortal Kombat 11", "Fighting", "Xbox Series X", 49.99, 6,
                            "2019-04-23", "NetherRealm Studios", "Warner Bros. Interactive Entertainment"),
                    new com.jd.VideoGame("Hollow Knight", "Action-Adventure", "PS4", 14.99, 20,
                            "2017-02-24", "Team Cherry", "Team Cherry")
            ));

            for (com.jd.VideoGame game : games) {
                videoGameService.addVideoGame(game);
            }*/

            /*List<com.jd.Customer> customers = new ArrayList<>(List.of(
                    new com.jd.Customer("Chris", "Evans", "chrisevans@marvel.com", "555-123-4567"),
                    new com.jd.Customer("Emma", "Stone", "emmastone@hollywood.com", "555-234-5678"),
                    new com.jd.Customer("LeBron", "James", "lebronjames@nba.com", "555-345-7890"),
                    new com.jd.Customer("Zendaya", "Coleman", "zendaya@hollywood.com", "555-567-8901"),
                    new com.jd.Customer("Ryan", "Reynolds", "ryanreynolds@deadpool.com", "555-678-9012"),
                    new com.jd.Customer("Simone", "Biles", "simonebiles@gymnastics.com", "555-789-0123"),
                    new com.jd.Customer("Tom", "Holland", "tomholland@spiderman.com", "555-890-1234"),
                    new com.jd.Customer("Adele", "Laurie", "adelelaurie@music.com", "555-901-2345"),
                    new com.jd.Customer("Serena", "Williams", "serenawilliams@tennis.com", "555-012-3456"),
                    new com.jd.Customer("Olivia", "Rodrigo", "oliviar@music.com", "555-831-3389")));

            for (com.jd.Customer customer : customers) {
                customerService.addCustomer(customer);
            }*/

            // customerService.getAllCustomers().forEach(System.out::println);
            // videoGameService.getAllGames().forEach(System.out::println);

            /*com.jd.Customer newCustomer = new com.jd.Customer("Taylor", "Swift", "tswift@gmail.com", "111-234-5678");
            customerService.addCustomer(newCustomer);*/

            /*com.jd.Employee newEmployee = new com.jd.Employee("Joe", "Dirt", "joedirt@gmail.com",
                "312-777-8888", "2022-05-15", 21.00);

            com.jd.EmployeeService employeeService = new com.jd.EmployeeService();
            employeeService.addEmployee(newEmployee);*/




            /*Scanner scanner = new Scanner(System.in);

            System.out.println("Enter employee email:");
            String employeeEmail = scanner.nextLine();
            int employeeId = employeeService.getEmployeeId(employeeEmail);
            if (employeeId == -1) {
                System.out.println("com.jd.Employee not found. Exiting.");
                return;
            }

            System.out.println("Enter game title:");
            String gameTitle = scanner.nextLine();
            System.out.println("Enter game platform:");
            String gamePlatform = scanner.nextLine();
            int gameId = videoGameService.getGameId(gameTitle, gamePlatform);
            if (gameId == 1) {
                System.out.println("Game not found. Exiting.");
                return;
            }

            // fetch game details (price and stock)
            System.out.println("Enter quantity to purchase");
            int quantity = scanner.nextInt();

            int stock = videoGameService.getGameStock(gameId);
            double price = videoGameService.getGamePrice(gameId);

            if (stock < quantity) {
                System.out.println("Not enough stock available. Exiting");
                return;
            }

            double totalPrice = price * quantity;

            videoGameService.updateGameStock(gameId, stock - quantity);

            // Create a com.jd.Sale object and add to the database
            com.jd.Sale sale = new com.jd.Sale(gameId, employeeId, quantity, totalPrice);
            saleService.addSale(sale);

            System.out.println("com.jd.Sale completed successfully!");*/
        } catch (SQLException e) {
            e.printStackTrace();
        }

       /* try (Connection connection = resources.DatabaseConfig.getConnection()) {
            System.out.println("Connected to the database from com.jd.Main!");
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