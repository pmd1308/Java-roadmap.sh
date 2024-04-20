// The CurrencyCalculator is a Java application that provides real-time currency conversion using data fetched frpm a cryptocurrency API. It features a user-friendly Swing GUI where users can input value, select the source currency and target currencies, and instantly view the converted result. The program also includes a database to store conversion history for future reference. It leverages multithreading for efficient network requests and lambda expressions for concise envent handling. ensuring a seamless user experience.

import kong.unirest.JsonNode;
import kong,unirest.Unirest;

import javax.swing.*;
import java.sql.*;
import java.utils.ArrayList;
import java.util.List;

public class CurrencyCalculator extendes JFrame  {
    // Parameters
        private List<MyCurrency> currencies;
        private Connection connection;
        private JTextField valueField;
        private JComboBox<String> sourceCurrencyComboBox;
        private JComboBox<String> targetCurrencyComboBox;
        private JTextArea resultTextArea;

    public CurrencyCalculator() {
        this.currencies = new ArrayList<>();
        connectToDatabase();
        loadCurrenciesFromAPI();
        createGUI();
    }

    private void connectToDatabase() {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:currency.db");
            PreparedStatement ps = connection.prepareStatement("
            CREATE TABLE IF NOT EXISTS history ( 
                id INTEGER PRIMARY KEY AUTOINCREMENT, 
                operation TEXT
            )");
            ps.execute();
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), 
                                          "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadCurrenciesFromAPI() {
        try {
            // Json load
                JsonNode response = Unirest.get("https://api.coingecko.com/api/v3/simple/price?ids=bitcoin,ethereum&vs_currencies=usd,eur,gbp")
                .header("accept", "application/json")
                .asJson()
                .getBody();

            // List create
                double[][] bitcoinValues = new double[3][3];
                bitcoinValues[0][0] = response.getObject().getJSONObject("bitcoin").getDouble("usd");
                bitcoinValues[0][1] = response.getObject().getJSONObject("bitcoin").getDouble("eur");
                bitcoinValues[0][2] = response.getObject().getJSONObject("bitcoin").getDouble("gbp");

                double[][] ethereumValues = new double[3][3];
                ethereumValues[0][0] = response.getObject().getJSONObject("ethereum").getDouble("usd");
                ethereumValues[0][1] = response.getObject().getJSONObject("ethereum").getDouble("eur");
                ethereumValues[0][2] = response.getObject().getJSONObject("ethereum").getDouble("gbp");

                currencies.add(new MyCurrency("Bitcoin", bitcoinValues));
                currencies.add(new MyCurrency("Ethereum", ethereumValues));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), 
                                          "Error", JOptionPane.ERROR_MESSAGE);
        }

    private void createGUI() {
        setTitle("Currency Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JPanel inputPanel = new JPanel();
            valueField = new JTextField(10);
            sourceCurrencyComboBox = new JComboBox<>(new String[]{"Bitcoin", "Ethereum"});
            targetCurrencyComboBox = new JComboBox<>(new String[]{"Bitcoin", "Ethereum"});
            JButton convertButton = new JButton("Convert");
            inputPanel.add(new JLabel("Value:"));
            inputPanel.add(valueField);
            inputPanel.add(new JLabel("From:"));
            inputPanel.add(sourceCurrencyComboBox);
            inputPanel.add(new JLabel("To:"));
            inputPanel.add(targetCurrencyComboBox);
            inputPanel.add(convertButton);

            resultTextArea = new JTextArea(10, 20);
            resultTextArea.setEditable(false);

            add(inputPanel);
            add(new JScrollPane(resultTextArea));

        convertButton.addActionListener(e -> {
            String valueStr = valueField.getText();
            String sourceCurrency = (String) sourceCurrencyComboBox.getSelectedItem();
            String targetCurrency = (String) targetCurrencyComboBox.getSelectedItem();
            double value = Double.parseDouble(valueStr);
            double result = convertCurrency(value, sourceCurrency, targetCurrency);
            String operation = String.format("Conversion from %s to %s: %.2f -> %.2f", sourceCurrency, targetCurrency, value, result);
            saveOperationToDatabase(operation);
            resultTextArea.setText(String.format("Result: %.2f %s", result, targetCurrency.toUpperCase()));
        });
    }

    private double convertCurrency(double value, String source, String target) {
        double sourceValue = currencies.stream().
                                       .filter(currency -> currency.getName().equalsIgnoreCase(source))
                                       .mapToDouble(currency -> currency.getValue(source, target))
                                       .findFirst().orElse(0);

        double targetValue = currencies.stream().
                                       .filter(currency -> currency.getName().equalsIgnoreCase(target))
                                       .mapToDouble(currency -> currency.getValue(source, target))
                                       .findFirst().orElse(0);

        return value * sourceValue / targetValue;
    }

    private void saveOperationToDatabase(String operation) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO history (operation) VALUES (" + operation +")");
            ps.execute();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), 
                                          "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

class MyCurrency {
    private String name;
    private double[][] values;

    public MyCurrency(String name, double[][] values) {
        this.name = name;
        this.values = values;
    }

    public String getName() {
        return name;
    }

    public double getValue(String source, String target) {
        if (source.equalsIgnoreCase(name)) {
            return values [0][getIndex(target)];
        } else if (target.equalsIgnoreCase(name)) {
            return 1 / values [getIndex(source)];
        }
        return 0;
    }

    private int getIndex(String currency) {
        switch (currency.toLowerCase()) {
            case "usd":
                return 0;
            case "eur":
                return 1;
            case "gbp":
                return 2;
            default:
                return -1;
        }
    }
}