package model;

import java.util.ArrayList;

import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;

public class Account {

    private String holderName;

    private int accountNumber;
    
    private int pin;

    private double balance;

    private ArrayList<String> transactionHistory = new ArrayList<>();

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
    System.out.println("Current Balance: $" + balance);
}

public void checkBalance() {

    System.out.println("Current balance: $" + balance);
}

public void deposit(double amount) {

    if (amount <= 0) {

        System.out.println("Invalid deposit amount!");

        return;
    }

    balance += amount;

    addTransaction("Deposit: $" + amount);

    System.out.println("Deposit successful!");
}

public void withdraw(double amount) {

    if (amount <= 0) {

        System.out.println("Invalid withdraw amount!");

        return;
    }

    if (amount > balance) {

        System.out.println("Insufficient balance!");

        return;
    }

    balance -= amount;

    addTransaction("Withdraw: $" + amount);

    System.out.println("Withdrawal successful!");
}

public void showTransactionHistory() {

    System.out.println("\n=== Transaction History ===");

    for (String transaction : transactionHistory) {

        System.out.println(transaction);

        }
    }

public void addTransferSent(double amount, int destinationAccount) {

        addTransaction("Transfer sent: $" + amount + " to account " + destinationAccount);

    }

public void addTransferReceived(double amount, int sourceAccount) {

        addTransaction("Transfer received: $" + amount + " from account " + sourceAccount);

    }
}