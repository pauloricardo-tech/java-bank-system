package model;

import java.util.ArrayList;

import java.util.Locale;

import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;

import java.text.NumberFormat;

public class Account {

    private String holderName;

    private int accountNumber;
    
    private int pin;

    private double balance;

    private ArrayList<String> transactionHistory = new ArrayList<>();

    public String formatMoney(double amount) {

        NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.US);

        return formatter.format(amount);

    }

    private void addTransaction(String transaction) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        String timestamp = LocalDateTime.now().format(formatter);

        transactionHistory.add("[" + timestamp + "] " + transaction);

    }

public Account(int accountNumber, String holderName, double initialBalance, int pin) {

    this.accountNumber = accountNumber;
    this.holderName = holderName;
    this.pin = pin;
    balance = initialBalance;
}

public Account(int accountNumber, double balance) {

}

    public String getHolderName() {

    return holderName;
    }

    public int getAccountNumber() {

    return accountNumber;
}

public int getPin() {

    return pin;
}

public double getBalance(){
    return balance;
}

public void showAccountInfo() {

    System.out.println("Account Number: " + accountNumber);
    System.out.println("Current Balance: " + formatMoney(balance));
}

public void checkBalance() {

    System.out.println("\n========================");
    System.out.println("Current Balance");
    System.out.println(formatMoney(balance));
    System.out.println("========================");
}

public void deposit(double amount) {

    if (amount <= 0) {

        System.out.println("[ERROR] Invalid deposit amount!");

        return;
    }

    balance += amount;

    addTransaction("Deposit: " + formatMoney(amount));

    System.out.println("[SUCCESS] Deposit completed successfully!");
}

public void depositWithoutHistory(double amount) {

        balance += amount;
}

public void withdraw(double amount) {

    if (amount <= 0) {

        System.out.println("[ERROR] Invalid withdraw amount!");

        return;
    }

    if (amount > balance) {

        System.out.println("[ERROR] Insufficient balance!");

        return;
    }

    balance -= amount;

    addTransaction("Withdraw: " + formatMoney(amount));

    System.out.println("[SUCCESS] Withdrawal completed successfully!");
}

public void withdrawWithoutHistory(double amount) {

        balance -= amount;
}

public void showTransactionHistory() {

    System.out.println("\n========== TRANSACTION HISTORY ==========\n");

    if (transactionHistory.isEmpty()) {

        System.out.println("[INFO] No transactions found.\n");

        System.out.println("=========================================");

        return;

    }

    int start = Math.max(0, transactionHistory.size() - 5);

    for (int i = start; i < transactionHistory.size(); i++) {

        System.out.println(transactionHistory.get(i) + "\n");

        }

    System.out.println("=========================================");

    }

public ArrayList<String> getTransactionHistory() {

        return transactionHistory;
}

public void addTransferSent(double amount, int destinationAccount) {

        addTransaction("Transfer Sent: " + formatMoney(amount) + " to account " + destinationAccount);

    }

public void addTransferReceived(double amount, int sourceAccount) {

        addTransaction("Transfer Received: " + formatMoney(amount) + " from account " + sourceAccount);

    }
}