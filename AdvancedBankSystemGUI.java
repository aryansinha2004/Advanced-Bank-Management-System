import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

class BankAccount {
    private String accountNumber;
    private String accountHolder;
    protected double balance;

    public BankAccount(String accountNumber, String accountHolder, double balance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = balance;
    }
 
    public String getAccountNumber() {
        return accountNumber;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
        } else {
            JOptionPane.showMessageDialog(null, "Insufficient funds for withdrawal.");
        }
    }

    public String toString() {
        return "Account Number: " + accountNumber + "\nAccount Holder: " + accountHolder + "\nBalance: " + balance;
    }
}

class SavingsAccount extends BankAccount {
    private double interestRate;

    public SavingsAccount(String accountNumber, String accountHolder, double balance, double interestRate) {
        super(accountNumber, accountHolder, balance);
        this.interestRate = interestRate;
    }

    public void applyInterest() {
        balance += balance * interestRate / 100;
        JOptionPane.showMessageDialog(null, "Interest applied. New balance: " + balance);
    }
}

class CheckingAccount extends BankAccount {
    private double overdraftLimit;

    public CheckingAccount(String accountNumber, String accountHolder, double balance, double overdraftLimit) {
        super(accountNumber, accountHolder, balance);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance + overdraftLimit) {
            balance -= amount;
            JOptionPane.showMessageDialog(null, "Withdrawn: " + amount);
        } else {
            JOptionPane.showMessageDialog(null, "Insufficient funds for withdrawal.");
        }
    }
}

class Bank {
    private HashMap<String, BankAccount> accounts;

    public Bank() {
        accounts = new HashMap<>();
    }

    public void addAccount(BankAccount account) {
        accounts.put(account.getAccountNumber(), account);
    }

    public BankAccount findAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }
}

public class AdvancedBankSystemGUI {
    private static Bank bank = new Bank();

    public static void main(String[] args) {
        // Create the main frame
        JFrame frame = new JFrame("Advanced Bank System");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create components
        JButton createSavingsButton = new JButton("Create Savings Account");
        JButton createCheckingButton = new JButton("Create Checking Account");
        JButton performTransactionButton = new JButton("Perform Transaction");
        JButton exitButton = new JButton("Exit");

        // Add components to the frame
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.add(createSavingsButton);
        frame.add(createCheckingButton);
        frame.add(performTransactionButton);
        frame.add(exitButton);

        // Add action listeners
        createSavingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createSavingsAccount();
            }
        });

        createCheckingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createCheckingAccount();
            }
        });

        performTransactionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performTransaction();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Make the frame visible
        frame.setVisible(true);
    }

    private static void createSavingsAccount() {
        String accountNumber = JOptionPane.showInputDialog("Enter account number:");
        String accountHolderName = JOptionPane.showInputDialog("Enter account holder name:");
        double initialBalance = Double.parseDouble(JOptionPane.showInputDialog("Enter initial balance:"));
        double interestRate = Double.parseDouble(JOptionPane.showInputDialog("Enter interest rate:"));

        bank.addAccount(new SavingsAccount(accountNumber, accountHolderName, initialBalance, interestRate));
        JOptionPane.showMessageDialog(null, "Savings Account created.");
    }

    private static void createCheckingAccount() {
        String accountNumber = JOptionPane.showInputDialog("Enter account number:");
        String accountHolderName = JOptionPane.showInputDialog("Enter account holder name:");
        double initialBalance = Double.parseDouble(JOptionPane.showInputDialog("Enter initial balance:"));
        double overdraftLimit = Double.parseDouble(JOptionPane.showInputDialog("Enter overdraft limit:"));

        bank.addAccount(new CheckingAccount(accountNumber, accountHolderName, initialBalance, overdraftLimit));
        JOptionPane.showMessageDialog(null, "Checking Account created.");
    }

    private static void performTransaction() {
        String accountNumber = JOptionPane.showInputDialog("Enter account number:");
        BankAccount transactionAccount = bank.findAccount(accountNumber);

        if (transactionAccount == null) {
            JOptionPane.showMessageDialog(null, "Account not found.");
            return;
        }

        String[] options = {"Deposit", "Withdraw", "Check Balance"};
        int transactionChoice = JOptionPane.showOptionDialog(null, "Select transaction type:", "Transaction",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        switch (transactionChoice) {
            case 0: // Deposit
                double depositAmount = Double.parseDouble(JOptionPane.showInputDialog("Enter amount to deposit:"));
                transactionAccount.deposit(depositAmount);
                break;
            case 1: // Withdraw 
                double withdrawAmount = Double.parseDouble(JOptionPane.showInputDialog("Enter amount to withdraw:"));
                transactionAccount.withdraw(withdrawAmount);
                break;
            case 2: // Check Balance 
                JOptionPane.showMessageDialog(null, transactionAccount.toString());
                break;
            default:
                JOptionPane.showMessageDialog(null, "Invalid transaction choice.");
        }
    }  
}