// This script is a GUI tool designed for managing a simple inventory of products. It provides functionality to add and remove product entries, each associated with a price. The application utilizes a CSV file('products.csv') for persistence storage of the product data, ensuring that information is retained when accross sessions.
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class InventoryManager extends JFrame {
    private final HashMap<String, Double> products = new HashMap<>();
    private JTextField txtProduct;
    private JTextField txtPrice;
    private JTextArea textArea;
    private final String PRODUCTS_FILE = "products.csv";
    private final String CSV_SEPARATOR = ",";
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public InventoryManager() {
        loadProducts();
        setupUI();
    }

    private void setupUI() {
        setTitle("Inventory Manager");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel dataPanel = setupDataPanel();
        add(dataPanel, BorderLayout.NORTH);

        JPanel listPanel = setupListPanel();
        add(new JScrollPane(listPanel), BorderLayout.CENTER);

        updateTextArea();
    }

    private JPanel setupDataPanel() {
        JPanel dataPanel = new JPanel();
        dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.Y_AXIS));
        dataPanel.setBorder(BorderFactory.createTitledBorder("Data Entry"));

        dataPanel.add(new JLabel("Product:"));
        txtProduct = new JTextField(10);
        dataPanel.add(txtProduct);

        dataPanel.add(new JLabel("Price:"));
        txtPrice = new JTextField(10);
        dataPanel.add(txtPrice);

        return dataPanel;
    }

    private JPanel setupListPanel() {
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        JButton btnAdd = new JButton("Add");
        JButton btnRemove = new JButton("Remove");
        listPanel.add(btnAdd);
        listPanel.add(btnRemove);

        btnAdd.addActionListener(e -> addProduct());
        btnRemove.addActionListener(e -> removeProduct());

        return listPanel;
    }

    private void addProduct() {
        String product = txtProduct.getText();
        try {
            double price = Double.parseDouble(txtPrice.getText());
            products.put(product, price);
            executorService.submit(this::saveProducts);
            updateTextArea();
            txtProduct.setText("");
            txtPrice.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid price.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeProduct() {
        String product = txtProduct.getText();
        if (products.remove(product) != null) {
            executorService.submit(this::saveProducts);
            updateTextArea();
        } else {
            JOptionPane.showMessageDialog(this, "Product not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTextArea() {
        if (textArea == null) textArea = new JTextArea(10, 20);
        textArea.setText("");
        products.forEach((product, price) -> textArea.append(product + ": $" + price + "\n"));
    }

    private void loadProducts() {
        File file = new File(PRODUCTS_FILE);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] tokens = line.split(CSV_SEPARATOR);
                    String product = tokens[0];
                    double price = Double.parseDouble(tokens[1]);
                    products.put(product, price);
                }
            } catch (IOException | NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveProducts() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PRODUCTS_FILE))) {
            for (Map.Entry<String, Double> entry : products.entrySet()) {
                writer.write(entry.getKey() + CSV_SEPARATOR + entry.getValue() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new InventoryManager().setVisible(true));
    }
}
