package ui;

import controller.AccountController;
import model.Account;
import service.Bank;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {

    public static void showAccountHeader(Account account) {

        System.out.println("\n========================");

        System.out.println("Welcome, " + account.getHolderName() + "!");

        System.out.println("Account Number: " + account.getAccountNumber());

        System.out.println("========================");
    }

    public static void showMenu() {

        System.out.println("\n=== BANK SYSTEM ===");
        System.out.println("1. Create Account");
        System.out.println("2. Switch Account");
        System.out.println("3. Check Balance");
        System.out.println("4. Deposit");
        System.out.println("5. Withdraw");
        System.out.println("6. Transfer Money");
        System.out.println("7. Transaction History");
        System.out.println("8. Account Statement");
        System.out.println("9. Search Transactions");
        System.out.println("10. Logout");
        System.out.println("11. Exit");

        System.out.println("\nChoose an option:");
        System.out.println("> ");
    }

    public static Account login(ArrayList<Account> accounts, Scanner scanner) {

        System.out.println("Enter account number:");
        int accountNumber = AccountController.readInt(scanner);

        Account account = Bank.findAccount(accounts, accountNumber);

        if (account == null) {

            System.out.println("[ERROR] Account not found!");
            return null;
        }

        int attempts = 0;

        while (attempts < 3) {

            System.out.println("Enter PIN:");

            int enteredPin = AccountController.readInt(scanner);

            if (account.getPin() == enteredPin) {

                Menu.showAccountHeader(account);

                return account;
            }

            attempts++;

            System.out.println("\n[ERROR] Incorrect PIN!");

            if (attempts < 3) {

                System.out.println("Attempts remaining: " + (3 - attempts));
            }
        }

        System.out.println("\n[ERROR] Too many failed attempts!");

        return null;

    }

}
