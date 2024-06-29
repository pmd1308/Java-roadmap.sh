import java.sql.*;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    // JDBC URL do banco de dados SQLite
    private static final String JDBC_URL = "jdbc:sqlite:notas_fiscais.db";

    // Método principal
    public static void main(String[] args) {
        // Carregando as credenciais do banco de dados das variáveis de sistema
        String username = System.getenv("DB_USERNAME");
        String password = System.getenv("DB_PASSWORD");

        // Criando a tabela de notas fiscais (se não existir)
        createTable(username, password);

        // Lendo o código de barras da nota fiscal a partir da entrada do usuário
        Map<String, String> details = readBarcodeFromScanner();

        // Inserindo uma nova nota fiscal com os detalhes obtidos do código de barras
        if (details != null) {
            String cliente = details.get("cliente");
            double valor = Double.parseDouble(details.get("valor"));
            insertNotaFiscal(username, password, 1, cliente, valor);
        }

        // Consultando todas as notas fiscais
        queryNotasFiscais(username, password);
    }

    // Método para criar a tabela de notas fiscais
    private static void createTable(String username, String password) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, username, password);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS notas_fiscais (" +
                    "id INTEGER PRIMARY KEY, " +
                    "cliente TEXT, " +
                    "valor REAL)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para inserir uma nova nota fiscal
    private static void insertNotaFiscal(String username, String password, int id, String cliente, double valor) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO notas_fiscais (id, cliente, valor) VALUES (?, ?, ?)")) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, cliente);
            preparedStatement.setDouble(3, valor);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para consultar todas as notas fiscais
    private static void queryNotasFiscais(String username, String password) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, username, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM notas_fiscais")) {
            System.out.println("Notas Fiscais: ");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String cliente = resultSet.getString("cliente");
                double valor = resultSet.getDouble("valor");
                System.out.println("ID: " + id + " valor: " + valor + " cliente: " + cliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para ler o código de barras da nota fiscal a partir do scanner
    private static Map<String, String> readBarcodeFromScanner() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Escaneie o código de barras: ");
        String barcodeData = scanner.nextLine();

        // Verifica se o código de barras está no formato correto
        if (!barcodeData.contains(":")) {
            System.out.println("Formato inválido");
            return null;
        }
        Map<String, String> details = new HashMap<>();
        String[] keyValue = barcodeData.split(":", 2);

        // Verifica se o código de barras contém os detalhes esperados
        if (keyValue.length != 2) {
            System.out.println("Formato inválido");
            return null;
        }

        // Armazena os detalhes da nota fiscal em um mapa
        details.put(keyValue[0].trim(), keyValue[1].trim());
        return details;
    }
}
