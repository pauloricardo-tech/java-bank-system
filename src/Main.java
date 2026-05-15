import java.io.File;

import java.io.FileNotFoundException;

import java.io.FileWriter;

import java.io.IOException;

import java.util.Scanner;

import java.util.ArrayList;

public class Main {

    public static void saveAccount(ArrayList<Account> accounts) {

        try {

            FileWriter writer = new FileWriter("accounts.txt");

            for (Account account : accounts) {

                writer.write(account.getAccountNumber() + "," + account.getHolderName() + "," + account.getBalance() + "," + account.getPin() + "\n");
            }

            writer.close();

            System.out.println("Accounts saved successfully");
        } catch (IOException e) {

            System.out.println("Error saving accounts");
        }
    }

    public static ArrayList<Account> loadAccounts() {

        ArrayList<Account> accounts = new ArrayList<>();

        try {

            File file = new File ("accounts.txt");

            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNextLine()) {

                String line = fileScanner.nextLine();

                String[] parts = line.split(",");

                int accountNumber = Integer.parseInt(parts[0]);

                String holderName = parts[1];

                double balance = Double.parseDouble(parts[2]);

                int pin = Integer.parseInt(parts[3]);

                accounts.add(new Account(accountNumber, holderName, balance, pin));
            }

            fileScanner.close();

        } catch (FileNotFoundException e) {

            System.out.println("No saved accounts found");
        }

        return accounts;
    }

    public static Account findAccount(ArrayList<Account> accounts, int accountNumber) {

        for (Account account : accounts) {

            if (account.getAccountNumber() == accountNumber) {
                return account;
            }
        }

        return null;
    }

    public static void checkBalance(double balance) {
        System.out.println("Current Balance: $" + balance);
    }

    public static double deposit(double balance, double amount) {

        balance += amount;

        System.out.println("Deposit successful!");

        return balance;
    }

    public static double withdraw(double balance, double amount) {

        if (amount > balance) {

            System.out.println("Insufficient balance");

        } else {

            balance -= amount;

            System.out.println("Withdraw successful!");
        }

        return balance;
    }

    public static void showMenu(Account account) {

    }

    public static void main(String[] args) {

        ArrayList<Account> accounts = Bank.loadAccounts();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter account number");
        int accountNumber = scanner.nextInt();

        Account account = Bank.findAccount(accounts, accountNumber);

        if (account == null) {
            System.out.println("Account not found!");
            return;
        }

        System.out.println("Enter PIN");
        int enteredPin = scanner.nextInt();

        if (account.getPin() != enteredPin) {

            System.out.println("Incorrect Pin");
            return;
        }

        showAccountHeader(account);

        int option;

        do {

            showMenu(account);

            System.out.println("\n=== BANK SYSTEM ===");
            System.out.println("Current Account: " + account.getAccountNumber());

            System.out.println("1 - Create Account");
            System.out.println("2 - Switch Account");
            System.out.println("3 - Check Balance");
            System.out.println("4 - Deposit");
            System.out.println("5 - Withdraw");
            System.out.println("6 - Transaction History");
            System.out.println("7 - Exit");

            System.out.println("Choose an option: ");

            option = scanner.nextInt();

            switch (option) {

                case 1:
                    System.out.println("Enter new account number: ");
                    int newAccountNumber = scanner.nextInt();

                    Account existingAccount = Bank.findAccount(accounts, newAccountNumber);

                    if (existingAccount != null) {

                        System.out.println("Account number already exists!");

                        break;
                    }

                    System.out.println("Enter holder name: ");

                    scanner.nextLine();

                    String holderName = scanner.nextLine();

                    System.out.println("Create PIN");
                    int pin = scanner.nextInt();

                    if (pin < 1000 || pin > 9999) {

                        System.out.println("PIN must have 4 digits!");

                        break;
                    }

                    if (existingAccount != null) {

                        System.out.println("Account already exists!");

                        break;
                    }

                    System.out.println("Enter initial balance");
                    double initialBalance = scanner.nextDouble();

                    accounts.add(new Account(newAccountNumber,holderName,initialBalance,pin));

                    Bank.saveAccounts(accounts);

                    System.out.println("Account created successfully!");
                    break;

                case 2:

                    System.out.println("Enter account number: ");
                    int switchAccountNumber = scanner.nextInt();

                    Account foundAccount = findAccount(accounts, switchAccountNumber);

                    if (foundAccount != null) {

                        account = foundAccount;

                        System.out.println("Account switched successfully");

                    } else {

                        System.out.println("Account not found!");
                    }
                    break;

                case 3:
                    account.checkBalance();
                    break;

                case 4:
                    System.out.print("Enter deposit amount: ");
                    double depositAmount = scanner.nextDouble();

                    account.deposit(depositAmount);

                    Bank.saveAccounts(accounts);
                    break;

                case 5:
                    System.out.println("Enter withdrawal amount");
                    double withdrawAmount = scanner.nextDouble();

                    account.withdraw(withdrawAmount);

                    Bank.saveAccounts(accounts);
                    break;


                case 6:
                    account.showTransactionHistory();
                    break;

                case 7:
                    System.out.println("Exiting system...");

                    Bank.saveAccounts(accounts);

                    break;

                default:
                    System.out.println("Invalid option.");

            }

        } while (option != 7);

        scanner.close();
    }

    public static void showAccountHeader(Account account) {

        System.out.println("\n========================");

        System.out.println("Welcome, " + account.getHolderName() + "!");

        System.out.println("Account Number: " + account.getAccountNumber());

        System.out.println("========================");
    }
}