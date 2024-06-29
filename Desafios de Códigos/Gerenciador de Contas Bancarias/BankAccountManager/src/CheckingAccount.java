/**
     * Conta Verificada de Classe
     * Representa uma conta corrente com limite de cheque especial.
 */
public class CheckingAccount extends Account {
    private double overdraftLimit;

    /**
     * Constrói uma Conta Corrent com os detalhes especificados.
     * @param o número da conta
     * @param agência o código da agência
     * @param clientName o nome do cliente
     * @param saldo do saldo inicial da conta
     * @param cheque especialLimite o limite do cheque especial para a conta corrente
     */
    public CheckingAccount(int number, String agency, String clientName, double balance, double overdraftLimit) {
        super(number, agency, clientName, balance);
        this.overdraftLimit = overdraftLimit;
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    public void setOverdraftLimit(double overdraftLimit) {
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void withdraw(double amount) {
        if (amount > 0 && amount <= (getBalance() + overdraftLimit)) {
            setBalance(getBalance() - amount);
        }
    }

    @Override
    public String formatMessage() {
        return super.formatMessage() + String.format(" Your overdraft limit is %.2f.", overdraftLimit);
    }
}
