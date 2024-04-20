// This script provides a user interface for logging into the system. It presents a simple window where users can input their username and password. Upon clicking on the "Login" button, the provided credentials are verified against the database. If the login is successful, the user is redirected to the dashboard, where they can view their information and perform various actions, including generating a CSV file containing a transaction history. If the login is unsuccessful, an error is returned. The file also includes the main method for running the login window.
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Login extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private Connection conn;

    public Login(Connection conn) {
        this.conn = conn;
        // Window GUI
        setTitle("Login");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Input Panel
        JPanel inputField = new JPanel();
        inputField.setLayout(new GridLayout(2, 2));

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        inputField.add(usernameLabel);
        inputField.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        inputField.add(passwordLabel);
        inputField.add(passwordField);

        add(inputField, BorderLayout.CENTER);

        // Button
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> {
            try {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                PreparedStatement statement = conn.prepareStatement("SELECT * " + 
                                                                    "FROM users " + 
                                                                    "WHERE username = ? " + 
                                                                    "AND password = ?");
                statement.setString(1, username);
                statement.setString(2, password);
                ResultSet rs = statement.executeQuery();

                if (rs.next()) {
                    String userInfo = "Welcome, " + rs.getString("username") + "!";
                    Dashboard dashboard = new Dashboard(userInfo, conn);
                    dashboard.setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, 
                                                "Invalid username or password.", 
                                                "Error", 
                                                JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, 
                                            "Failed to login.", 
                                            "Error", 
                                            JOptionPane.ERROR_MESSAGE);
            }
        });
        add(loginButton, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", 
                                                        "username", 
                                                        "password");
            EventQueue.invokeLater(() -> {
                Login login = new Login(conn);
                login.setVisible(true);
            });
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                                        "Failed to connect to the database.", 
                                        "Error", 
                                        JOptionPane.ERROR_MESSAGE);
        }
    }
}
