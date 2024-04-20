// Registration GUI using MongoDB
import javax.swing.*;
import java.awt.*;
import org.bson.Document;
import com.mongodb.client.MongoCollection;

public class RegistrationGUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private MongoCollection<Document> usersCollection;

    public RegistrationGUI(MongoCollection<Document> usersCollection) {
        // Initialize GUI window
        this.usersCollection = usersCollection;
        setTitle("Registration");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        // Username label and field
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        usernameField = new JTextField(10);
        passwordField = new JPasswordField(10);

        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);

        // Register button
        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(e -> {
            try {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                Document user = new Document("username", username).append("password", password);
                usersCollection.insertOne(user);
                setVisible(false);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                ex.printStackTrace();
            } finally {
                JOptionPane.showMessageDialog(null, "Redirecting to Login Page");
                dispose();
                LoginGUI loginGUI = new LoginGUI();
                loginGUI.setVisible(true);
            }
        });
        add(registerButton);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RegistrationGUI(null));
    }
}
