/**
 * Define as operações executadas por cada conta 
 */
public interface AccountActions {
    void deposit(double amount);
    void withdraw(double amount);
    String formatMessage();
}
