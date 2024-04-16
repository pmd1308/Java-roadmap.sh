import kong.unirest.JsonNode;
import kong.unirest.Unirest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CurrencyCalculator {
    private List<MyCurrency> currencies;
    private Connection connection;

    public CurrencyCalculator() {
        this.currencies = new ArrayList<>();
        connectToDatabase();
        loadCurrenciesFromAPI();
    }

    // Conexão com o banco de dados
    private void connectToDatabase() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:history.db");
            // Criação da tabela se não existir
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS history (id INTEGER PRIMARY KEY AUTOINCREMENT, operation TEXT)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Carregamento das moedas da API
    private void loadCurrenciesFromAPI() {
        try {
            JsonNode response = Unirest.get("https://api.coingecko.com/api/v3/simple/price?ids=bitcoin,ethereum&vs_currencies=usd,eur,gbp")
                    .header("accept", "application/json")
                    .asJson()
                    .getBody();

            // Carrega os valores do BitCoin
            double[][] bitcoinValues = new double[3][3];
            bitcoinValues[0][0] = response.getObject().getJSONObject("bitcoin").getDouble("usd");
            bitcoinValues[0][1] = response.getObject().getJSONObject("bitcoin").getDouble("eur");
            bitcoinValues[0][2] = response.getObject().getJSONObject("bitcoin").getDouble("gbp");

            // Carrega os valores do Ethereum
            double[][] ethereumValues = new double[3][3];
            ethereumValues[0][0] = response.getObject().getJSONObject("ethereum").getDouble("usd");
            ethereumValues[0][1] = response.getObject().getJSONObject("ethereum").getDouble("eur");
            ethereumValues[0][2] = response.getObject().getJSONObject("ethereum").getDouble("gbp");

            // Adiciona as moedas dentro de seus valores
            currencies.add(new MyCurrency("Bitcoin", bitcoinValues));
            currencies.add(new MyCurrency("Ethereum", ethereumValues));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Conversão de moeda
    private double convertCurrency(double value, String source, String target) {
        double sourceValue = 0;
        double targetValue = 0;

        // Encontra os valores das moedas de origem e destino
        for (MyCurrency currency : currencies) {
            if (currency.getName().equalsIgnoreCase(source)) {
                sourceValue = currency.getValue(source, target);
            } else if (currency.getName().equalsIgnoreCase(target)) {
                targetValue = currency.getValue(source, target);
            }
        }

        // Verifica se as moedas foram encontradas
        if (sourceValue == 0 || targetValue == 0) {
            System.out.println("Moeda de origem ou destino não encontrada.");
            return 0;
        }

        // Converte o valor
        return value * (targetValue / sourceValue);
    }

    // Menu de opções
    public void showMenu() {
        Scanner scanner = new Scanner(System.in);
        int option;
        do {
            System.out.println("=== Calculadora de Moedas ===");
            System.out.println("1. Listar moedas e seus valores");
            System.out.println("2. Converter moeda");
            System.out.println("3. Ver histórico de operações");
            System.out.println("4. Limpar histórico de operações");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    listCurrencies();
                    break;
                case 2:
                    performConversion();
                    break;
                case 3:
                    viewHistory();
                    break;
                case 4:
                    clearHistory();
                    break;
                case 5:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (option != 5);
        scanner.close(); // Fechar o scanner ao finalizar
    }

    // Listagem das moedas e valores
    private void listCurrencies() {
        System.out.println("Moedas disponíveis:");
        for (MyCurrency currency : currencies) {
            System.out.println(currency.getName() + ":");
            currency.printValues();
        }
    }

    // Realização da conversão de moeda
    private void performConversion() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o valor a ser convertido:");
        double value = scanner.nextDouble();
        System.out.println("Digite a moeda de origem (Bitcoin ou Ethereum):");
        String source = scanner.next();
        System.out.println("Digite a moeda de destino (Bitcoin ou Ethereum):");
        String target = scanner.next();
        double result = convertCurrency(value, source, target);
        if (result != 0) {
            System.out.println("Resultado da conversão: " + result + " " + target.toUpperCase());
            recordOperation("Conversão de " + source.toUpperCase() + " para " + target.toUpperCase() + ": " + value + " -> " + result);
        }
    }

    // Visualização do histórico de operações
    private void viewHistory() {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM history ORDER BY timestamp");
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                System.out.println("Histórico de operações vazio.");
            } else {
                System.out.println("Histórico de operações:");
                while (resultSet.next()) {
                    System.out.println(resultSet.getInt("id") + ": " + resultSet.getString("operation"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Limpeza do histórico de operações
    private void clearHistory() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM history");
            System.out.println("Histórico de operações limpo.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Registro de operações
    private void recordOperation(String operation) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO history (operation) VALUES (?)");
            preparedStatement.setString(1, operation);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Fechamento da conexão com o banco de dados
    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Conexão com o banco de dados fechada.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método principal
    public static void main(String[] args) {
        CurrencyCalculator calculator = new CurrencyCalculator();
        calculator.showMenu();
        calculator.closeConnection(); // Fechar conexão com o banco de dados ao finalizar
    }
}

// Classe para representar as moedas
class MyCurrency {
    private String name;
    private double[][] values;

    // Construtor
    public MyCurrency(String name, double[][] values) {
        this.name = name;
        this.values = values;
    }

    // Obter o nome da moeda
    public String getName() {
        return name;
    }

    // Obter o valor da moeda
    public double getValue(String source, String target) {
        if (source.equalsIgnoreCase(name)) {
            return values[0][getIndex(target)];
        } else if (target.equalsIgnoreCase(name)) {
            return 1 / values[0][getIndex(source)];
        }
        return 0;
    }

    // Imprimir os valores da moeda
    public void printValues() {
        System.out.println("  USD: " + values[0][0] + ", EUR: " + values[0][1] + ", GBP: " + values[0][2]);
    }

    // Obter o índice da moeda
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
