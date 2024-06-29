// This code creates a Java Swing GUI application for CRUD operations oa a user database. It includes components for entering and user data (name, afe, active, status, gender), buttons for inserting and retrieving data from the database, and methods to handle database connectios and operatios. The GUI layout is organized using GRIDLayout, and lambda expressions are used for button to simplify the envent handling.

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserCRUDGUI extends JFrame {
    // Parameters
    private JTextField nameField;
    private JTextField ageField;
    private JCheckBox activeCheckBox;
    private JComboBox<String> genderComboBox;
    private JButton insertButton;
    private JButton showButton;
    private JButton exportButton;
    private JTextArea outputArea;
    private Connection connection;
    private ExecutorService executorService;

    public UserCRUDGUI() {
        // GUI Window
        setTitle("User CRUD");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1));

        // Name Field
        nameField = new JTextField();
        panel.add(new JLabel("Name:"));
        panel.add(nameField);

        // Age Field
        ageField = new JTextField();
        panel.add(new JLabel("Age:"));
        panel.add(ageField);

        // Active CheckBox
        activeCheckBox = new JCheckBox();
        panel.add(new JLabel("Active:"));
        panel.add(activeCheckBox);

        // Gender ComboBox
        genderComboBox = new JComboBox<>();
        panel.add(new JLabel("Gender:"));
        panel.add(genderComboBox);

        // Insert Button
        insertButton = new JButton("Insert");
        panel.add(insertButton);
        insertButton.addActionListener(e -> {
            executorService.execute(() -> {
                String name = nameField.getText();
                byte age = Byte.parseByte(ageField.getText());
                boolean active = activeCheckBox.isSelected();
                String gender = (String) genderComboBox.getSelectedItem();

                try (PreparedStatement pstmt = connection.prepareStatement("INSERT INTO users (name, age, active, gender) VALUES (?, ?, ?, ?)")) {
                    pstmt.setString(1, name);
                    pstmt.setByte(2, age);
                    pstmt.setBoolean(3, active);
                    pstmt.setString(4, gender);
                    pstmt.executeUpdate();
                    SwingUtilities.invokeLater(() -> outputArea.append("Data inserted successfully!\n"));
                } catch (SQLException ex) {
                    outputArea.setText(ex.getMessage());
                }
            });
        });

        // Show Button
        showButton = new JButton("Show");
        panel.add(showButton);
        showButton.addActionListener(e -> {
            executorService.execute(() -> {
                try (Statement stmt = connection.createStatement();
                     ResultSet rs = stmt.executeQuery("SELECT * FROM users")) {
                    outputArea.setText("");
                    while (rs.next()) {
                        String name = rs.getString("name");
                        byte age = rs.getByte("age");
                        boolean active = rs.getBoolean("active");
                        String gender = rs.getString("gender");
                        SwingUtilities.invokeLater(() -> outputArea.append(name + ", " + age + ", " + active + ", " + gender + "\n"));
                    }
                } catch (SQLException ex) {
                    outputArea.setText(ex.getMessage());
                }
            });
        });

        // Export to CSV file button
        exportButton = new JButton("Export");
        panel.add(exportButton);
        exportButton.addActionListener(e -> {
            executorService.execute(() -> {
                String fileName = "user_data_" + LocalDateTime.now() + ".csv";
                try (PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM users");
                     ResultSet rs = pstmt.executeQuery();
                     FileWriter fileWriter = new FileWriter(fileName)) {
                    fileWriter.write("name, age, active, gender\n");
                    while (rs.next()) {
                        String name = rs.getString("name");
                        byte age = rs.getByte("age");
                        boolean active = rs.getBoolean("active");
                        String gender = rs.getString("gender");
                        fileWriter.write(name + ", " + age + ", " + active + ", " + gender + "\n");
                    }
                    fileWriter.flush();
                    SwingUtilities.invokeLater(() -> outputArea.append("Data exported to " + fileName));
                } catch (SQLException | IOException ex) {
                    outputArea.setText(ex.getMessage());
                }
            });
        });

        // Output Area
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        panel.add(scrollPane);

        add(panel, BorderLayout.CENTER);

        // Initialize executor service
        executorService = Executors.newSingleThreadExecutor();

        // Connect to the database
        connectToDatabase();
    }

    // Connect to the database
    private void connectToDatabase() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(System.getenv("DB_URL"), System.getenv("DB_USER"), System.getenv("DB_PASSWORD"));
            Statement stmt = connection.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS users (" +
                    "id INTEGER PRIMARY KEY AUTO_INCREMENT, " +
                    "name TEXT, " +
                    "age INTEGER, " +
                    "active BOOLEAN, " +
                    "gender TEXT" +
                    ")");
        } catch (ClassNotFoundException | SQLException e) {
            outputArea.setText(e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Swing components should be created and manipulated on the event dispatch thread
        SwingUtilities.invokeLater(() -> {
            UserCRUDGUI gui = new UserCRUDGUI();
            gui.setVisible(true);
        });
    }
}
