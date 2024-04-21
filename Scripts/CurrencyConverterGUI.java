// This script offers a simple yet powerful solution for currency conversion. It seamlessly integrates with an external exchange rate API, enabling users to convert between different currencies with ease. With a user-friendly interface and robust error handling, it ensures a smooth and reliable conversion experience. Additionally, it stores conversion operations locally, enhancing data integrity and accessibility

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CurrencyConverterGUI extends JFrame {

    private static final String API_URL = "https://api.exchangerate-api.com/v4/latest/";
    private static final Map<String, String> CURRENCIES = new HashMap<>();
    private static final String DB_URL = Sytem.getenv('CURRENCY_CONVERTER_DB');
    private static final String INSERT_OPERATION_SQL = "INSERT INTO operations ( " + 
                                                    "source_currency, " + 
                                                    "target_currency, " + 
                                                    "source_amount, " +
                                                    "target_amount) " + 
                                                    "VALUES (?, ?, ?, ?)";

    private JComboBox<String> sourceCurrencyComboBox;
    private JComboBox<String> targetCurrencyComboBox;
    private JTextField sourceAmountField;
    private JTextField targetAmountField;
    private JButton convertButton;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    // Load the program sequencialy
    public CurrencyConverterGUI() {
        loadAndSetupUI();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void loadAndSetupUI() {
        loadCurrencies(); // Load map thats contains the currency
        initializeDatabase();
        setupUI(); // Setup the user interface
    }

    private void loadCurrencies() {
        CURRENCIES.put("USD", "US Dollar");
        CURRENCIES.put("EUR", "Euro");
        CURRENCIES.put("GBP", "British Pound Sterling");
        CURRENCIES.put("BRL", "Brazilian Real");
        CURRENCIES.put("CNY", "Chinese Renminbi");
    }

    private void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS operations " + 
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
                        "source_currency TEXT, " + 
                        "target_currency TEXT, " + 
                        "source_amount REAL, " + 
                        "target_amount REAL)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        }
    }

    private void setupUI() {
        setTitle("Currency Converter");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 1));

        JPanel sourceCurrencyPanel = new JPanel();
        sourceCurrencyPanel.add(new JLabel("Source Currency:"));
        sourceCurrencyComboBox = new JComboBox<>(CURRENCIES.values().toArray(new String[0]));
        sourceCurrencyPanel.add(sourceCurrencyComboBox);

        JPanel targetCurrencyPanel = new JPanel();
        targetCurrencyPanel.add(new JLabel("Target Currency:"));
        targetCurrencyComboBox = new JComboBox<>(CURRENCIES.values().toArray(new String[0]));
        targetCurrencyPanel.add(targetCurrencyComboBox);

        JPanel amountPanel = new JPanel();
        amountPanel.add(new JLabel("Amount:"));
        sourceAmountField = new JTextField(10);
        amountPanel.add(sourceAmountField);
        targetAmountField = new JTextField(10);
        targetAmountField.setEditable(false);
        amountPanel.add(targetAmountField);

        JPanel buttonPanel = new JPanel();
        convertButton = new JButton("Convert");
        buttonPanel.add(convertButton);
        convertButton.addActionListener(e -> {
            try {
                String sourceCurrency = getKeyFromValue(CURRENCIES, sourceCurrencyComboBox.getSelectedItem().toString());
                String targetCurrency = getKeyFromValue(CURRENCIES, targetCurrencyComboBox.getSelectedItem().toString());
                double sourceAmount = Double.parseDouble(sourceAmountField.getText());
                double targetAmount = convertCurrency(sourceCurrency, targetCurrency, sourceAmount);
                targetAmountField.setText(String.valueOf(targetAmount));
                saveOperation(sourceCurrency, targetCurrency, sourceAmount, targetAmount);
            } catch (IOException | URISyntaxException | NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(sourceCurrencyPanel);
        add(targetCurrencyPanel);
        add(amountPanel);
        add(buttonPanel);
    }

    private static String getKeyFromValue(Map<String, String> map, String value) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }

    private double convertCurrency(String sourceCurrency, String targetCurrency, double sourceAmount) throws IOException, URISyntaxException, NumberFormatException {
        HttpClient client = HttpClient.newHTTPClient();
        HttpRequest request = Client.newBuilder()
                                    .uri(new URI(API_URL + sourceCurrency))
                                    .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.isSuccess()) {
            String responseBody = response.body();
            JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
            JsonObject rates = jsonObject.get("rates").getAsJsonObject();
            return sourceAmount * rates.get(targetCurrency).getAsDouble();
        } else {
            throw new RuntimeException("Error getting exchange rates: Status: " + response.statusCode());;
        }
    }



    private void saveOperation(String sourceCurrency, String targetCurrency, double sourceAmount, double targetAmount) {
        executorService.submit(() -> {
            try (Connection conn = DriverManager.getConnection(DB_URL);
                PreparedStatement pstmt = conn.prepareStatement(INSERT_OPERATION_SQL)) {
                pstmt.setString(1, sourceCurrency);
                pstmt.setString(2, targetCurrency);
                pstmt.setDouble(3, sourceAmount);
                pstmt.setDouble(4, targetAmount);
                pstmt.executeUpdate();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CurrencyConverterGUI::new);
    }

