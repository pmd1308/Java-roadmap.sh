import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ContaBancoGUI extends JFrame {

    private JLabel titleLabel;
    private JButton createSavingsAccountButton;
    private JButton createCheckingAccountButton;
    private JButton findAccountButton;
    private JButton exitButton;


    public ContaBancoGUI() {
        super("Bank Account System");

        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        titleLabel = new JLabel("Bank Account System", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        createSavingsAccountButton = new JButton("Create Savings Account");
        createCheckingAccountButton = new JButton("Create Checking Account");
        findAccountButton = new JButton("Find Account");
        exitButton = new JButton("Exit");

        createSavingsAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createSavingsAccount();
            }
        });

        createCheckingAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createCheckingAccount();
            }
        });

        findAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findAccount();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exit();
            }
        });

        // Layout management
        setLayout(new GridLayout(5, 1));
        add(titleLabel);
        add(createSavingsAccountButton);
        add(createCheckingAccountButton);
        add(findAccountButton);
        add(exitButton);
    }

    /**
     * Prompts the user to create a savings account.
     */
    private void createSavingsAccount() {
        try {
            String numberStr = JOptionPane.showInputDialog(this, "Enter account number:");
            int number = Integer.parseInt(numberStr);

            String agency = JOptionPane.showInputDialog(this, "Enter agency:");
            String clientName = JOptionPane.showInputDialog(this, "Enter client name:");
            String balanceStr = JOptionPane.showInputDialog(this, "Enter balance:");
            double balance = Double.parseDouble(balanceStr);

            String interestRateStr = JOptionPane.showInputDialog(this, "Enter interest rate:");
            double interestRate = Double.parseDouble(interestRateStr);

            Account account = new SavingsAccount(number, agency, clientName, balance, interestRate);
            AccountDAO accountDAO = new AccountDAO();
            accountDAO.save(account);

            JOptionPane.showMessageDialog(this, account.formatMessage(), "Account Created", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid numbers.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void createCheckingAccount() {
        try {
            String numberStr = JOptionPane.showInputDialog(this, "Enter account number:");
            int number = Integer.parseInt(numberStr);

            String agency = JOptionPane.showInputDialog(this, "Enter agency:");
            String clientName = JOptionPane.showInputDialog(this, "Enter client name:");
            String balanceStr = JOptionPane.showInputDialog(this, "Enter balance:");
            double balance = Double.parseDouble(balanceStr);

            String overdraftLimitStr = JOptionPane.showInputDialog(this, "Enter overdraft limit:");
            double overdraftLimit = Double.parseDouble(overdraftLimitStr);

            Account account = new CheckingAccount(number, agency, clientName, balance, overdraftLimit);
            AccountDAO accountDAO = new AccountDAO();
            accountDAO.save(account);

            JOptionPane.showMessageDialog(this, account.formatMessage(), "Account Created", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid numbers.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void findAccount() {
        try {
            String numberStr = JOptionPane.showInputDialog(this, "Enter account number:");
            int number = Integer.parseInt(numberStr);

            AccountDAO accountDAO = new AccountDAO();
            Account account = accountDAO.findByNumber(number);

            if (account != null) {
                JOptionPane.showMessageDialog(this, account.formatMessage(), "Account Found", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Account not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void exit() {
        int option = JOptionPane.showConfirmDialog(this, "Do you want to exit the program?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    /**
     * The main method to launch the GUI application.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ContaBancoGUI gui = new ContaBancoGUI();
                gui.setVisible(true);
            }
        });
    }
}
