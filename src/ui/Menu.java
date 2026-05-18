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

        System.out.println("1 - Create Account");
        System.out.println("2 - Switch Account");
        System.out.println("3 - Check Balance");
        System.out.println("4 - Deposit");
        System.out.println("5 - Withdraw");
        System.out.println("6 - Transaction History");
        System.out.println("7 - Exit");

        System.out.println("Choose an option:");
    }

    public static Account login(ArrayList<Account> accounts, Scanner scanner) {

        System.out.println("Enter account number:");
        int accountNumber = AccountController.readInt(scanner);

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

        Menu.showAccountHeader(account);

        return account;
    }

}
