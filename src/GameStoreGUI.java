import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.sql.Connection;
import java.sql.SQLException;

public class GameStoreGUI  extends JFrame  {
    private final VideoGameService videoGameService;

    public GameStoreGUI(Connection connection) {
        this.videoGameService = new VideoGameService(connection);

        setTitle("Game Store Manager");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Table to display games
        String[] columnNames = {"ID", "Title", "Genre", "Platform", "Price", "Stock"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable gameTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(gameTable);
        add(scrollPane, BorderLayout.CENTER);

        // Button to fetch and display games
        JButton loadGamesButton = new JButton("Load Games");
        loadGamesButton.addActionListener(e -> {
            tableModel.setRowCount(0); // Clear table
            try {
                List<VideoGame> games = videoGameService.getAllGames();
                for (VideoGame game : games) {
                    tableModel.addRow(new Object[]{
                            game.getGameId(), game.getTitle(), game.getGenre(),
                            game.getPlatform(), game.getPrice(), game.getStock()
                    });
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error loading games");
            }
        });

        add(loadGamesButton, BorderLayout.SOUTH);
        setVisible(true);
    }
}
