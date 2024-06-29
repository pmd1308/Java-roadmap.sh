/**
 * Classe SavingsAccount
 * Caderneta de poupança com taxa de juros.
 */
public class SavingsAccount extends Account {
    private double interestRate;

    /**
     * Constrói uma Conta Poupança com os detalhes especificados.
     * @param o número da conta
     * @param agência o código da agência
     * @param clientName o nome do cliente
     * @param saldo do saldo inicial da conta
     * @param jurosTaxa de juros da caderneta de poupança
 */
    public SavingsAccount(int number, String agency, String clientName, double balance, double interestRate) {
        super(number, agency, clientName, balance);
        this.interestRate = interestRate;
    }
    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }


    public void applyInterest() {
        double interest = getBalance() * interestRate;
        deposit(interest);
    }
/**
     * Formata os detalhes da conta em uma mensagem.
     * @return a mensagem de detalhes da conta formatada
 */
    @Override
    public String formatMessage() {
        return super.formatMessage() + String.format(" Your interest rate is %.2f%%.", interestRate * 100);
    }
}
