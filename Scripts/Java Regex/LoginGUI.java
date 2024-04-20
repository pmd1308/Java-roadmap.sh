// A Login Gui for validate user credentials in MongoDB.
import java.swing.*;
import java.awt.*;
import org.bson.Document;
import com.mongodb.*;
import java.util.Arrays;

public class LoginGUI extends JFrame {
  private JTextField usernameField;
  private JPasswordField passwordField;
  private MongoCollection<Document> usersCollection;

  public LoginGUI() {
    // Window
      setTitle("Login");
      setSize(300, 200);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setLayout(new GridLayout());

    // Connect to MongoDB
      MongoClient mongoClient = MongoClients.create(
        MongoClientSettings.builder()
            .applyToClusterSettings(builder ->
                builder.hosts(Arrays.asList(new ServerAddress("localhost", 27017))))
            .credential(MongoCredential.createCredential(System.getenv(username), System.getenv(database), System.getenv(password).toCharArray()))
            .build());
      MongoDatabase database = mongoClient.getDatabase("mydb");
      usersCollection = database.getCollection("users");
      
    // GUI Login
      JLabel usernameLabel = new JLabel("Username: ");
      usernameField = new JTextField();
      add(usernameLabel);
      add(usernameField);

      JLabel passwordLabel = new JLabel("Password: ");
      passwordField = new JPasswordField();
      add(passwordLabel);
      add(passwordField);

      JButton loginButton = new JButton("Login");
      loginButton.addActionListener(e -> {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        Document user = usersCollection.find(eq("username", username)).first();
        if (validateUser(username, String.valueOf(password) {
          JOptionPane.showMessageDialog(null, "Login Successful");
          // Redirect to dashboard if has
          dispose();
        } else {
          JOptionPane.showMessageDialog(null, "Login Failed");
        }
      });
      add(loginButton);

      JButton registerButton = new JButton;
      registerButton.addActionListener(e -> {
        dispose(); // Close current login GUI
        RegistrationGUI RegistrationGUI = new RegistrationGUI(usersCollection);
        RegistrationGUI.setVisible(true);
      });
      add(registerButton);

      setVisible(true);

  }

  private boolean validateUser(String username, String password) {
    Document query = new Document("username", username).append("password", password)
    return usersCollection.countDocuments(query) > 0;
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(LoginGUI::new);
  }
}