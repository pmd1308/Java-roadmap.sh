import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CRUD {
    // Configuração BD carregada de variáveis de sistema
    private static final String URL = System.getenv("DB_URL");
    private static final String USER = System.getenv("DB_USER");
    private static final String PASSWORD = System.getenv("DB_PASSWORD");

    // Método para estabelecer conexão com o banco de dados
    public Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Método para inserir dados na tabela de usuários
    public void inserirDados(String nome, byte idade, boolean ativo, char genero) {
        String sql = "INSERT INTO usuarios (nome, idade, ativo, genero) VALUES (?, ?, ?, ?)";

        try (Connection conn = conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nome);
            pstmt.setByte(2, idade);
            pstmt.setBoolean(3, ativo);
            pstmt.setString(4, String.valueOf(genero));
            pstmt.executeUpdate();

            System.out.println("Dados inseridos com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para consultar e exibir os dados da tabela de usuários
    public void consultarDados() {
        String sql = "SELECT nome, idade, ativo, genero FROM usuarios";

        try (Connection conn = conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String nome = rs.getString("nome");
                byte idade = rs.getByte("idade");
                boolean ativo = rs.getBoolean("ativo");
                char genero = rs.getString("genero").charAt(0);

                System.out.println("Nome: " + nome + " | Idade: " + idade + " | Ativo: " + ativo + " | Gênero: " + genero);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método principal
    public static void main(String[] args){
        CRUD crud = new CRUD();
    } 
}
