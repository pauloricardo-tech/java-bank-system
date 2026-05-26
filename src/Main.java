import model.Account;

import service.Bank;

import ui.Menu;

import controller.AccountController;

import java.util.Scanner;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        ArrayList<Account> accounts = Bank.loadAccounts();

        System.out.println("========================");
        System.out.println("    JAVA BANK SYSTEM");
        System.out.println("         v1.0");
        System.out.println("========================");

        Account account = Menu.login(accounts, scanner);

        if (account == null)
            return;

        int option;

        do {

            System.out.println("\n========================");
            System.out.println("Current Account: " + account.getAccountNumber());
            System.out.println("User: " + account.getHolderName());
            System.out.println("========================");

            Menu.showMenu();

            option = AccountController.readInt(scanner);

            switch (option) {

                case 1:

                    AccountController.createAccount(accounts, scanner);

                    break;

                case 2:

                    Account switchedAccount = AccountController.switchAccount(accounts, scanner);

                    if (switchedAccount != null) {

                        account = switchedAccount;
                    }

                    break;

                case 3:

                    AccountController.showBalance(account);

                    break;

                case 4:

                  AccountController.depositMoney(account, accounts, scanner);

                    break;

                case 5:

                    AccountController.withdrawMoney(account, accounts, scanner);

                    break;


                case 6:

                    AccountController.showHistory(account);

                    break;

                case 7:

                    AccountController.exitSystem(accounts);

                    break;

                case 8:

                    AccountController.transferMoney(account, accounts, scanner);

                    break;

                case 9:

                    System.out.println("\n[INFO] Logout completed successfully\n");

                    account = Menu.login(accounts, scanner);

                    if (account == null)

                        return;

                    break;

                case 10:

                    AccountController.showStatement(account);

                    break;

                default:
                    System.out.println("Invalid option.");

            }

        } while (option != 7);

        scanner.close();
    }
}