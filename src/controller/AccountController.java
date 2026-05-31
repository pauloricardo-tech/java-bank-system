package controller;

import model.Account;
import service.Bank;
import ui.Menu;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        System.out.println("Statement Generated: " + now.format(formatter));

        System.out.println("User: " + account.getHolderName());
        System.out.println("Account Number: " + account.getAccountNumber());
        System.out.println("Current Balance: " + account.formatMoney(account.getBalance()));

        int totalTransactions = account.getTransactionHistory().size();
        int deposits = 0;
        int withdrawals = 0;
        int transfersSent = 0;
        int transfersReceived = 0;

        for (String transaction : account.getTransactionHistory()) {

            String lowerTransaction = transaction.toLowerCase();

            if (lowerTransaction.contains("deposit")) {

                deposits++;

            } else if (lowerTransaction.contains("withdraw")) {

                withdrawals++;

            } else if (lowerTransaction.contains("transfer sent")) {

                transfersSent++;

            } else if (lowerTransaction.contains("transfer received")) {

                transfersReceived++;
            }
        }

        System.out.println("\nTransaction Summary:\n");

        System.out.println("Total Transactions: " + totalTransactions);
        System.out.println("Deposits: " + deposits);
        System.out.println("Withdrawals: " + withdrawals);
        System.out.println("Transfers Sent: " + transfersSent);
        System.out.println("Transfers Received: " + transfersReceived);

        System.out.println("\nLast 5 Transactions:\n");

        account.showTransactionHistory();

    }

    public static void searchTransactions(Account account, Scanner scanner) {

        System.out.println("\n=== SEARCH TRANSACTIONS ===\n");

        System.out.println("1. Deposits");
        System.out.println("2. Withdrawals");
        System.out.println("3. Transfers Sent");
        System.out.println("4. Transfers Received");

        System.out.println("\nChoose an option:");

        int option = readInt(scanner);

        String filter = "";

        switch (option) {

            case 1:
                filter = "deposit";
                break;

            case 2:
                filter = "withdraw";
                break;

            case 3:
                filter = "transfer sent";
                break;

            case 4:
                filter = "transfer received";
                break;

            default:
                System.out.println("[ERROR] Invalid option!");
                return;
        }

        System.out.println("\n========== RESULTS ==========\n");

        boolean found = false;

        for (String transaction : account.getTransactionHistory()) {

            if (transaction.toLowerCase().contains(filter)) {

                System.out.println(transaction + "\n");

                found = true;
            }
        }

        if (!found) {

            System.out.println("[INFO] No matching transactions found.\n");
        }

        System.out.println("=========================================");

    }

    public static void  exitSystem(ArrayList<Account> accounts) {

        System.out.println("[INFO] Exiting system...");

        Bank.saveAccounts(accounts);
    }
}
