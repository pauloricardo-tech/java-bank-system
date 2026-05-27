package controller;

import model.Account;
import service.Bank;
import ui.Menu;

import java.util.ArrayList;
import java.util.Scanner;

public class AccountController {

    public static int readInt(Scanner scanner) {

        while (!scanner.hasNextInt()) {

            System.out.println("[ERROR] Invalid number! Try again:");

            scanner.next();
        }

        return scanner.nextInt();
    }

    public static double readDouble(Scanner scanner) {

        while (!scanner.hasNextDouble()) {

            System.out.println("[ERROR] Invalid value! Try again:");

            scanner.next();
        }

        return scanner.nextDouble();
    }

    public static void createAccount(ArrayList<Account> accounts, Scanner scanner) {

        System.out.println("Enter new account number:");
        int newAccountNumber = readInt(scanner);

        Account existingAccount = Bank.findAccount(accounts, newAccountNumber);

        if (existingAccount != null) {

            System.out.println("[ERROR] Account already exists!");
            return;
        }

        System.out.println("Enter holder name:");

        scanner.nextLine();

        String holderName = scanner.nextLine();

        System.out.println("Create PIN");
        int pin = readPin(scanner);

        if (pin == -1) {
            return;
        }

        if (pin < 1000 || pin > 9999) {

            System.out.println("[ERROR] PIN must have 4 digits!");
            return;
        }

        System.out.println("Enter initial balance:");
        double initialBalance = readDouble(scanner);

        accounts.add(new Account(newAccountNumber, holderName, initialBalance, pin));

        Bank.saveAccounts(accounts);

        System.out.println("[SUCCESS] Account created successfully!");
    }

    public static int readPin(Scanner scanner) {

        String pinInput = scanner.next();

        if (pinInput.length() != 4) {

            System.out.println("[ERROR] PIN must have exactly 4 digits!");

            return -1;
        }

        if (!pinInput.matches("\\d{4}")) {

            System.out.println("[ERROR] PIN must contain only numbers!");

            return -1;
        }

        return Integer.parseInt(pinInput);
    }

    public static Account switchAccount(ArrayList<Account> accounts, Scanner scanner) {

        System.out.println("Enter account number:");
        int switchAccountNumber = readInt(scanner);

        Account foundAccount = Bank.findAccount(accounts, switchAccountNumber);

        if (foundAccount == null) {

            System.out.println("[ERROR] Account not found!");

            return null;
        }

        System.out.println("Enter PIN:");

        int enteredPin = readPin(scanner);

        if (enteredPin == -1) {
            return null;
        }

        if (foundAccount.getPin() != enteredPin) {

            System.out.println("[ERROR] Incorrect PIN!");

            return null;
        }

        System.out.println("[SUCCESS] Account switched successfully!");

        Menu.showAccountHeader(foundAccount);

        return foundAccount;

    }

    public static void depositMoney(Account account, ArrayList<Account> accounts, Scanner scanner) {

        System.out.println("Enter deposit amount:");
        double depositAmount = readDouble(scanner);

        account.deposit(depositAmount);

        Bank.saveAccounts(accounts);
    }

    public static void withdrawMoney(Account account, ArrayList<Account> accounts, Scanner scanner) {

        System.out.println("Enter withdrawal amount:");
        double withdrawAmount = readDouble(scanner);

        account.withdraw(withdrawAmount);

        Bank.saveAccounts(accounts);
    }

    public static void showBalance(Account account) {

        account.checkBalance();
    }

    public static void showHistory(Account account) {

        account.showTransactionHistory();
    }

    public static void transferMoney(Account currentAccount, ArrayList<Account> accounts, Scanner scanner) {

        System.out.println("Enter destination account number:");

        int destinationNumber = readInt(scanner);

        Account destinationAccount = Bank.findAccount(accounts, destinationNumber);

        if (destinationAccount == null) {

            System.out.println("[ERROR] Destination account not found!");

            return;
        }

        if (currentAccount.getAccountNumber() == destinationNumber) {

            System.out.println("[ERROR] Cannot transfer to the same account!");

            return;
        }

        System.out.println("Enter transfer amount:");

        double amount = readDouble(scanner);

        if (amount <= 0) {

            System.out.println("[ERROR] Transfer amount must be greater than 0!");

            return;
        }

        if (amount > currentAccount.getBalance()) {

            System.out.println("[ERROR] Insufficient balance!");

            return;
        }

        System.out.println("Confirm transfer of " + currentAccount.formatMoney(amount) + " to account " + destinationAccount.getAccountNumber() + "? (Y/N)");

        String confirmation = scanner.next();

        if (!confirmation.equalsIgnoreCase("Y")) {

            System.out.println("[INFO] Transfer cancelled!");

            return;
        }

        currentAccount.withdrawWithoutHistory(amount);

        destinationAccount.depositWithoutHistory(amount);

        currentAccount.addTransferSent(amount, destinationAccount.getAccountNumber());

        destinationAccount.addTransferReceived(amount, currentAccount.getAccountNumber());

        Bank.saveAccounts(accounts);

        System.out.println("[SUCCESS] Transfer completed successfully!");

    }

    public static void showStatement(Account account) {

        System.out.println("\n========== ACCOUNT STATEMENT ==========\n");

        System.out.println("User: " + account.getHolderName());
        System.out.println("Account Number: " + account.getAccountNumber());
        System.out.println("Current Balance: " + account.formatMoney(account.getBalance()));

        System.out.println("\nRecent Transactions:\n");

        account.showTransactionHistory();

    }

    public static void  exitSystem(ArrayList<Account> accounts) {

        System.out.println("[INFO] Exiting system...");

        Bank.saveAccounts(accounts);
    }
}
