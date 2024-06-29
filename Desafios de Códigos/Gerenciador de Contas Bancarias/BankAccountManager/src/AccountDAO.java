/**
 * Class AccountDAO
 * DataBase operations;
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class AccountDAO {

    /**
     * Saves an account to the database.
     */
    public void save(Account account) {
        String sql = "INSERT INTO Account(number, agency, clientName, balance) VALUES(?, ?, ?, ?)";
        Connection conn = DatabaseConnection.connect();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, account.getNumber());
            pstmt.setString(2, account.getAgency());
            pstmt.setString(3, account.getClientName());
            pstmt.setDouble(4, account.getBalance());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            DatabaseConnection.close(conn);
        }
    }

    public Account findByNumber(int number) {
        String sql = "SELECT number, agency, clientName, balance FROM Account WHERE number = ?";
        Account account = null;
        Connection conn = DatabaseConnection.connect();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, number);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {

                account = new SavingsAccount(
                        rs.getInt("number"),
                        rs.getString("agency"),
                        rs.getString("clientName"),
                        rs.getDouble("balance"),
                        0.01 // taxa de juros fixa como exemplo
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            DatabaseConnection.close(conn);
        }
        return account;
    }
}
