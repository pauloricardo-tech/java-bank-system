import java.util.ArrayList;

public class Account {

    private int accountNumber;

    private double balance;

    private ArrayList<String> transactionHistory = new ArrayList<>();

public Account(int accountNumber, double intialBalance) {

    this.accountNumber = accountNumber;
    balance = intialBalance;
}

public int getAccountNumber() {
    return accountNumber;
}

public double getBalance(){
    return balance;
}

public void showAccountInfo() {

    System.out.println("Account Number: " + accountNumber);
    System.out.println("Current Balance: $" + balance);
}

public void checkBalance() {
    System.out.println("Current balance: $" + balance);
}
public void deposit(double amount) {

    balance += amount;

    transactionHistory.add("Deposit: $" + amount);

    System.out.println("Deposit successful!");
}
public void withdraw(double amount) {

    if (amount > balance) {

        System.out.println("Insufficient balance!");

    } else {

        balance -= amount;

        transactionHistory.add("Withdraw: $" + amount);

        System.out.println("Withdrawal successful!");
    }
}

public void showTransactionHistory() {

        System.out.println("\n=== Transaction History ===");

        for (String transaction : transactionHistory) {
            System.out.println(transaction);
        }
    }
}