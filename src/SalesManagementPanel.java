import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class SalesManagementPanel extends JPanel {
    private JTable salesTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> gameDropdown;
    private JTextField quantityField, priceField;
    private JButton addSaleButton;
    private VideoGameService videGameService;
    private SaleService saleService;

    public SalesManagementPanel(VideoGameService videoGameService, SaleService saleService) {
        this.videGameService = videoGameService;
        this.saleService = saleService;

        setLayout(new BorderLayout());

        // Table setup
        String[] columnNames = {"Sale ID", "Game", "Quantity", "Total Price", "Date"};
        tableModel = new DefaultTableModel(columnNames, 0);
        salesTable = new JTable(tableModel);
        loadSalesData();
        add(new JScrollPane(salesTable), BorderLayout.CENTER);
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
}
