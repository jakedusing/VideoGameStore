import com.jd.CustomerService;
import com.jd.EmployeeService;
import com.jd.SaleService;
import com.jd.VideoGameService;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class GameStoreGUI  extends JFrame  {
    private final VideoGameService videoGameService;
    private final SaleService saleService;
    private final CustomerService customerService;
    private final EmployeeService employeeService;

    public GameStoreGUI(Connection connection) {
        this.videoGameService = new VideoGameService(connection);
        this.saleService = new SaleService(connection);
        this.customerService = new CustomerService(connection);
        this.employeeService = new EmployeeService(connection);

        setTitle("Game Store Manager");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Table to display games
        /*String[] columnNames = {"ID", "Title", "Genre", "Platform", "Price", "Stock"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable gameTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(gameTable);
        add(scrollPane, BorderLayout.CENTER);

        // Button to fetch and display games
        JButton loadGamesButton = new JButton("Load Games");
        loadGamesButton.addActionListener(e -> {
            tableModel.setRowCount(0); // Clear table
            try {
                List<com.jd.VideoGame> games = videoGameService.getAllGames();
                for (com.jd.VideoGame game : games) {
                    tableModel.addRow(new Object[]{
                            game.getGameId(), game.getTitle(), game.getGenre(),
                            game.getPlatform(), game.getPrice(), game.getStock()
                    });
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error loading games");
            }
        });*/

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("Sales Management", new SalesManagementPanel(videoGameService, saleService, customerService, employeeService));

        add(tabbedPane, BorderLayout.CENTER);
        //add(loadGamesButton, BorderLayout.SOUTH);
        setVisible(true);
    }
}
