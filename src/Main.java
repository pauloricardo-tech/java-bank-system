import java.io.File;

import java.io.FileNotFoundException;

import java.io.FileWriter;

import java.io.IOException;

import java.util.Scanner;

import java.util.ArrayList;

public class Main {

    public static Account login(ArrayList<Account> accounts, Scanner scanner) {

        System.out.println("Enter account number:");
        int accountNumber = scanner.nextInt();

        Account account = Bank.findAccount(accounts, accountNumber);

        if (account == null) {

            System.out.println("Account not found!");
            return null;
        }

        System.out.println("Enter PIN");
        int enteredPin = scanner.nextInt();

        if (account.getPin() != enteredPin) {

            System.out.println("Incorrect PIN!");
            return null;
        }

        showAccountHeader(account);

        return account;
    }

    public static void createAccount(ArrayList<Account> accounts, Scanner scanner) {

        System.out.println("Enter new account number:");
        int newAccountNumber = scanner.nextInt();

        Account existingAccount = Bank.findAccount(accounts, newAccountNumber);

        if (existingAccount != null) {

            System.out.println("Account already exists!");
            return;
        }

        System.out.println("Enter holder name:");

        scanner.nextLine();

        String holderName = scanner.nextLine();

        System.out.println("Create PIN");
        int pin = scanner.nextInt();

        if (pin < 1000 || pin > 9999) {

            System.out.println("PIN must have 4 digits!");
            return;
        }

        System.out.println("Enter initial balance:");
        double initialBalance = scanner.nextDouble();

        accounts.add(new Account(newAccountNumber, holderName, initialBalance, pin));

        Bank.saveAccounts(accounts);

        System.out.println("Account created successfully");
    }

    public static Account switchAccount(ArrayList<Account> accounts, Scanner scanner) {

        System.out.println("Enter account number:");
        int switchAccountNumber = scanner.nextInt();

        Account foundAccount = Bank.findAccount(accounts, switchAccountNumber);

        if (foundAccount == null) {

            System.out.println("Account not found!");

            return null;
        }

        System.out.println("Enter PIN:");
        int enteredPin = scanner.nextInt();

        if (foundAccount.getPin() != enteredPin) {

            System.out.println("Incorrect PIN!");

            return null;
        }

        System.out.println("Account switched successfully!");

        showAccountHeader(foundAccount);

        return foundAccount;

    }

    public static void showBalance(Account account) {

        account.checkBalance();
    }

    public static void depositMoney(Account account, ArrayList<Account> accounts, Scanner scanner) {

        System.out.println("Enter deposit amount;");
        double depositAmount = scanner.nextDouble();

        account.deposit(depositAmount);

        Bank.saveAccounts(accounts);
    }

    public static void withdrawMoney(Account account, ArrayList<Account> accounts, Scanner scanner) {

        System.out.println("Enter withdrawal amount:");
        double withdrawAmount = scanner.nextDouble();

        account.withdraw(withdrawAmount);

        Bank.saveAccounts(accounts);
    }

    public static void showHistory(Account account) {

        account.showTransactionHistory();
    }

    public static void  exitSystem(ArrayList<Account> accounts) {

        System.out.println("Exiting system...");

        Bank.saveAccounts(accounts);
    }

    public static void showMenu() {

        System.out.println("\n=== BANK SYSTEM ===");

        System.out.println("1 - Create Account");
        System.out.println("2 - Switch Account");
        System.out.println("3 - Check Balance");
        System.out.println("4 - Deposit");
        System.out.println("5 - Withdraw");
        System.out.println("6 - Transaction History");
        System.out.println("7 - Exit");

        System.out.println("Choose an option: ");
    }

    public static void main(String[] args) {

        ArrayList<Account> accounts = Bank.loadAccounts();

        Scanner scanner = new Scanner(System.in);

        Account account = login(accounts, scanner);

        if (account == null)
            return;

        int option;

        do {

            System.out.println("Current Account: " + account.getAccountNumber());

            showMenu();

            option = scanner.nextInt();

            switch (option) {

                case 1:

                    createAccount(accounts, scanner);

                    break;

                case 2:

                    Account switchedAccount = switchAccount(accounts, scanner);

                    if (switchedAccount != null) {

                        account = switchedAccount;
                    }

                    break;

                case 3:

                    showBalance(account);

                    break;

                case 4:

                    depositMoney(account, accounts, scanner);

                    break;

                case 5:

                    withdrawMoney(account, accounts, scanner);

                    break;


                case 6:

                    showHistory(account);

                    break;

                case 7:

                    exitSystem(accounts);

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