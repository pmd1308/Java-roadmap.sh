// Libries
  import javax.swing.*;
  import java.awt.*;
  import java.awt.event.*;
  import java.sql.*;

public class InventoryManager extends JFrame {
    // Parameters
      private Connection conn;
      private JTextArea textArea;

    public InventoryManager() {
        connectToDatabase();

        // Create the GUI
          setTitle("Inventory Manager");
          setSize(400, 300);
          setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          setLayout(new BorderLayout());

        // Panel for displaying product
          JPanel displayPanel = new JPanel();
          displayPanel.setLayout(new BorderLayout());
          displayPanel.setBorder(BorderFactory.createTitledBorder("Inventory"));
          textArea = new JTextArea(10, 20);
          textArea.setEditable(false);
          JScrollPane scrollPane = new JScrollPane(textArea);
          displayPanel.add(scrollPane, BorderLayout.CENTER);
          add(displayPanel, BorderLayout.CENTER);

        // Panel for adding products
          JPanel inputPanel = new JPanel();
          inputPanel.setLayout(new FlowLayout());
          inputPanel.setBorder(BorderFactory.createTitledBorder("Add Product"));
          JTextField productNameField = new JTextField(10);
          JTextField quantityField = new JTextField(5);

        // Buttons
          JButton addButton = new JButton("Add");
          addButton.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent e) {
                  try {
                      String productName = productNameField.getText();
                      int quantity = Integer.parseInt(quantityField.getText());
                      addProduct(productName, quantity);
                      updateTextArea();
                  } catch (NumberFormatException | SQLException ex) {
                      JOptionPane.showMessageDialog(InventoryManager.this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                  }
              }
          });
          inputPanel.add(new JLabel("Product Name:"));
          inputPanel.add(productNameField);
          inputPanel.add(new JLabel("Quantity:"));
          inputPanel.add(quantityField);
          inputPanel.add(addButton);
          add(inputPanel, BorderLayout.SOUTH);

          JButton removeButton = new JButton("Remove");
          removeButton.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent e) {
                  try {
                      String productName = productNameField.getText();
                      removeProduct(productName);
                      updateTextArea();
                  } catch (SQLException ex) {
                      JOptionPane.showMessageDialog(InventoryManager.this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                  }
              }
          });
          inputPanel.add(removeButton, BorderLayout.WEST);

          // Button for updating quantity
          JButton updateButton = new JButton("Update");
          updateButton.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent e) {
                  try {
                      String productName = productNameField.getText();
                      int quantity = Integer.parseInt(quantityField.getText());
                      updateProduct(productName, quantity);
                      updateTextArea();
                  } catch (NumberFormatException | SQLException ex) {
                      JOptionPane.showMessageDialog(InventoryManager.this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                  }
              }
          });
          inputPanel.add(updateButton, BorderLayout.EAST);

        // Update text area
        updateTextArea();
    }

    private void connectToDatabase() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory", "root", "");
            PreparedStatement ps = conn.prepareStatement("CREATE TABLE IF NOT EXISTS products (id INTEGER PRIMARY KEY AUTO_INCREMENT, product_name VARCHAR(255), quantity INT)");
            ps.execute();
            ps.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void addProduct(String productName, int quantity) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("INSERT INTO products (product_name, quantity) VALUES (?, ?)");
        ps.setString(1, productName);
        ps.setInt(2, quantity);
        ps.execute();
        ps.close();
    }

    private void removeProduct(String productName) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("DELETE FROM products WHERE product_name = ?");
        ps.setString(1, productName);
        ps.execute();
        ps.close();
    }

    private void updateProduct(String productName, int quantity) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("UPDATE products SET quantity = ? WHERE product_name = ?");
        ps.setInt(1, quantity);
        ps.setString(2, productName);
        ps.execute();
        ps.close();
    }

    private void updateTextArea() {
        textArea.setText("");
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM products");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String productName = rs.getString("product_name");
                int quantity = rs.getInt("quantity");
                textArea.append(productName + ": " + quantity + "\n");
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new InventoryManager().setVisible(true));
    }
}
