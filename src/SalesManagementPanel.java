import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class SalesManagementPanel extends JPanel {
    private JTable salesTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> gameDropdown;
    private JComboBox<String> employeeDropdown;
    private JComboBox<String> customerDropdown;
    private JTextField quantityField, priceField;
    private JButton addSaleButton;
    private VideoGameService videoGameService;
    private SaleService saleService;
    private CustomerService customerService;
    private EmployeeService employeeService;

    public SalesManagementPanel(VideoGameService videoGameService, SaleService saleService,
                                CustomerService customerService, EmployeeService employeeService) {
        this.videoGameService = videoGameService;
        this.saleService = saleService;
        this.customerService = customerService;
        this.employeeService = employeeService;

        setLayout(new BorderLayout());

        // Table setup
        String[] columnNames = {"Sale ID", "Game", "Quantity", "Total Price", "Date"};
        tableModel = new DefaultTableModel(columnNames, 0);
        salesTable = new JTable(tableModel);
        loadSalesData();
        add(new JScrollPane(salesTable), BorderLayout.CENTER);

        // form Panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(6, 2));

        formPanel.add(new JLabel("Employee:"));
        employeeDropdown = new JComboBox<>();
        loadEmployees();
        formPanel.add(employeeDropdown);

        formPanel.add(new JLabel("Customer:"));
        customerDropdown = new JComboBox<>();
        loadCustomers();
        formPanel.add(customerDropdown);

        formPanel.add(new JLabel("Game:"));
        gameDropdown = new JComboBox<>();
        loadGames();
        formPanel.add(gameDropdown);

        formPanel.add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        formPanel.add(quantityField);

        formPanel.add(new JLabel("Price:"));
        priceField = new JTextField();
        priceField.setEditable(false);
        formPanel.add(priceField);

        addSaleButton = new JButton("Add Sale");
        formPanel.add(addSaleButton);
        add(formPanel, BorderLayout.SOUTH);

        //Event Listeners
        gameDropdown.addActionListener(e -> updatePriceField());
        addSaleButton.addActionListener(e -> addSale());
    }

    private void loadSalesData() {
        try {
            List<Sale> sales = saleService.getAllSales();
            for (Sale sale : sales) {
                tableModel.addRow(new Object[]{sale.getSaleId(), sale.getGameId(), sale.getQuantity(), sale.getTotalPrice(), sale.getSaleDate()});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading sales: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void loadGames() {
        try {
            List<VideoGame> games = videoGameService.getAllGames();
            for (VideoGame game : games) {
                gameDropdown.addItem(game.getTitle());
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading games: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void loadCustomers() {
        try {
            List<Customer> customers = customerService.getAllCustomers();
            for (Customer customer : customers) {
                customerDropdown.addItem(customer.getEmail());
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading customers: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void loadEmployees() {
        try {
            List<Employee> employees = employeeService.getAllEmployees();
            for (Employee employee : employees) {
                employeeDropdown.addItem(employee.getEmail());
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading employees: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void updatePriceField() {
        try {
            String selectedGame = (String) gameDropdown.getSelectedItem();
            if (selectedGame != null) {
                double price = videoGameService.getGamePrice(selectedGame);
                priceField.setText(String.valueOf(price));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error updating price field: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void addSale() {
        String selectedGame = (String) gameDropdown.getSelectedItem();
        int quantity;

        try {
            quantity = Integer.parseInt(quantityField.getText());
            if (quantity < 0) {
                JOptionPane.showMessageDialog(this, "Quantity must be greater than zero.");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid quantity");
            return;
        }

      //  int employeeId;
      //  int customerId = (int) customerDropdown.getSelectedItem();
        try {
            int gameId = videoGameService.getGameId(selectedGame);
            int employeeId = employeeService.getEmployeeId((String) employeeDropdown.getSelectedItem());
            int customerId = customerService.getCustomerId((String) customerDropdown.getSelectedItem());
            double price = videoGameService.getGamePrice(selectedGame);
            double totalPrice = price * quantity;
            if (videoGameService.getGameStock(gameId) < quantity) {
                JOptionPane.showMessageDialog(this, "Not enough stock available.");
                return;
            }
            Sale sale = new Sale(gameId, employeeId, quantity, customerId);
            saleService.addSale(sale, videoGameService);
            videoGameService.updateGameStock(gameId, -quantity);
        } catch (SQLException e) {

        }


    }
}
