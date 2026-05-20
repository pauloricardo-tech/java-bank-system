import model.Account;

import service.Bank;

import ui.Menu;

import controller.AccountController;

import java.util.Scanner;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        ArrayList<Account> accounts = Bank.loadAccounts();

        Scanner scanner = new Scanner(System.in);

        Account account = Menu.login(accounts, scanner);

        if (account == null)
            return;

        int option;

        do {

            System.out.println("Current Account: " + account.getAccountNumber());

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

                default:
                    System.out.println("Invalid option.");

            }

        } while (option != 7);

        scanner.close();
    }
}