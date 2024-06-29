/**
 * Abstract class Account
 * Representação dos metodos e dados de cada conta
 */

public abstract class Account implements AccountActions {
    private int number;
    private String agency;
    private String clientName;
    private double balance;

    /**
     * Colocado para parametrizar cada conta que herde o a classe Accout
     */
    public Account(int number, String agency, String clientName, double balance) {
        this.number = number;
        this.agency = agency;
        this.clientName = clientName;
        this.balance = balance;
    }

    // Encapsulamento
    public int getNumber() {
        return number;
    }

    public String getAgency() {
        return agency;
    }

    public String getClientName() {
        return clientName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    // Operações
    @Override
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }


    @Override
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
        }
    }

    // Retorna a mensagem sobre a conta
    @Override
    public String formatMessage() {
        return String.format("Hello %s, thank you for creating an account at our bank. Your agency is %s, account number %d and your balance %.2f is now available for withdrawal.",
                clientName, agency, number, balance);
    }
}
