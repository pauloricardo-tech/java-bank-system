import java.util.Scanner;

import java.util.ArrayList;

public class Main {

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

    public static void showMenu() {

    }

    public static void main(String[] args) {

        ArrayList<Account> accounts = new ArrayList<>();

        accounts.add(new Account (12345, 1000));
        accounts.add(new Account(67890, 500));

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter account number");
        int accountNumber = scanner.nextInt();

        Account account = findAccount(accounts, accountNumber);

        if (account == null) {
            System.out.println("Account not found!");
            return;
        }

        int option;

        do {

            showMenu();

            System.out.println("\n=== BANK SYSTEM ===");
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

                    System.out.println("Enter initial balance");
                    double initialBalance = scanner.nextDouble();

                    accounts.add(new Account(newAccountNumber, initialBalance));

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
                    break;

                case 5:
                    System.out.println("Enter withdrawal amount");
                    double withdrawAmount = scanner.nextDouble();

                    account.withdraw(withdrawAmount);
                    break;


                case 6:
                    account.showTransactionHistory();
                    break;

                case 7:
                    System.out.println("Exiting system...");
                    break;

                default:
                    System.out.println("Invalid option.");

            }

        } while (option != 7);

        scanner.close();
    }
}