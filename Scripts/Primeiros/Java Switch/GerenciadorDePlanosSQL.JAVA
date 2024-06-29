import java.sql.*;
import java.io.IOException;

public class SQLPlanManager {
    // Configurações do banco de dados
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try {
            // Lendo dados de login do banco de dados como variáveis de sistema
            String user = System.getenv("DB_USER");
            String pass = System.getenv("DB_PASS");
            String dbName = System.getenv("DB_NAME");

            // Verificando se as variáveis de sistema estão configuradas corretamente
            if (user == null || pass == null || dbName == null) {
                System.out.println("Por favor, configure as variáveis de sistema DB_USER, DB_PASS e DB_NAME.");
                return;
            }

            // Conectando ao banco de dados
            System.out.println("Conectando ao banco de dados...");
            conn = DriverManager.getConnection(DB_URL + dbName, user, pass);

            // Criando tabela se não existir
            String sqlCreateTable = "CREATE TABLE IF NOT EXISTS plans (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "plan VARCHAR(255) NOT NULL," +
                    "duration INT NOT NULL)";
            stmt = conn.createStatement();
            stmt.executeUpdate(sqlCreateTable);

            // Menu principal
            System.out.println("Menu:");
            System.out.println("1. Escolher Plano");
            System.out.println("2. Ver Usuários Registrados");
            System.out.println("3. Abrir Terminal SQL");

            // Lendo a escolha do usuário
            int choice = Integer.parseInt(System.console().readLine());

            // Executando operações com base na escolha do usuário
            switch (choice) {
                case 1:
                    choosePlan(conn);
                    break;
                case 2:
                    viewRegisteredUsers(conn);
                    break;
                case 3:
                    openSQLTerminal(user, pass, dbName);
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } catch (SQLException se) {
            // Lidando com erros do JDBC
            se.printStackTrace();
        } catch (Exception e) {
            // Lidando com erros gerais
            e.printStackTrace();
        } finally {
            // Fechando recursos
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        System.out.println("Fim do programa.");
    }

    static void choosePlan(Connection conn) throws SQLException {
        // Selecionando um plano
        System.out.println("Escolha o plano:");
        System.out.println("1. Plano A");
        System.out.println("2. Plano B");
        System.out.println("3. Plano C");

        int choice = Integer.parseInt(System.console().readLine());

        switch (choice) {
            case 1:
                registerPlan(conn, "Plano A");
                break;
            case 2:
                registerPlan(conn, "Plano B");
                break;
            case 3:
                registerPlan(conn, "Plano C");
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }

    static void registerPlan(Connection conn, String plan) throws SQLException {
        // Registrando um plano
        System.out.println("Quantos meses? ");
        int duration = Integer.parseInt(System.console().readLine());

        String sqlInsert = "INSERT INTO plans (plan, duration) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sqlInsert)) {
            pstmt.setString(1, plan);
            pstmt.setInt(2, duration);
            pstmt.executeUpdate();
            System.out.println("Plano cadastrado com sucesso.");
        }
    }

    static void viewRegisteredUsers(Connection conn) throws SQLException {
        // Visualizando usuários registrados
        String sqlSelect = "SELECT * FROM plans";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlSelect)) {
            System.out.println("Usuários cadastrados: ");
            while (rs.next()) {
                int id = rs.getInt("id");
                String plan = rs.getString("plan");
                int duration = rs.getInt("duration");
                System.out.println("ID: " + id + ", Plano: " + plan + ", Duração: " + duration);
            }
        }
    }

    static void openSQLTerminal(String user, String pass, String dbName) {
        // Abrindo terminal SQL
        String[] command = {"gnome-terminal", "--", "bash", "-c", "mysql -u " + user + " -p" + pass + " " + dbName};

        try {
            ProcessBuilder builder = new ProcessBuilder(command);
            builder.redirectErrorStream(true);
            Process process = builder.start();

            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
