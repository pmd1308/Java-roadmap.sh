// This script provides a graphical interface for the user's dashboard. It displays the user's information, such as their username, and allows them to generate a CSV file containing transaction history. The class establishes a connection to the database and fetches transaction data to generate a CSV file. It also includes an event listener for the "Generate CSV" button, triggering the CSV generation process.
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.io.*;

public class Dashboard extends JFrame {
    private String userInfo;
    private Connection conn;

    public Dashboard(String userInfo, Connection conn) {
        this.userInfo = userInfo;
        this.conn = conn;

        // Window GUI
        setTitle("Dashboard");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // User Info Panel
        JPanel userInfoPanel = new JPanel();
        userInfoPanel.setLayout(new GridLayout(2, 1));

        JLabel userInfoLabel = new JLabel(userInfo);
        userInfoPanel.add(userInfoLabel);

        add(userInfoPanel, BorderLayout.NORTH);

        // Button
        JButton generateCSVButton = new JButton("Generate CSV");
        generateCSVButton.addActionListener(e -> {
            try {
                Statement statement = conn.createStatement();
                ResultSet rs = statement.executeQuery("SELECT * " +
                                                    "FROM transactions");
                FileWriter writer = new FileWriter("transactions.csv");
                writer.write("Date,Description,Amount\n");
                while (rs.next()) {
                    String date = rs.getString("date");
                    String description = rs.getString("description");
                    double amount = rs.getDouble("amount");
                    writer.write(date + "," + description + "," + amount + "\n");
                }
                writer.close();
                JOptionPane.showMessageDialog(this, "CSV file generated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException | IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to generate CSV file.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        add(generateCSVButton, BorderLayout.CENTER);

        // Logout Button
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            dispose(); // Close the dashboard window
            EventQueue.invokeLater(() -> {
                Login login = new Login(conn);
                login.setVisible(true);
            });
        });
        add(logoutButton, BorderLayout.SOUTH);
    }
}
