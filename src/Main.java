import java.util.Scanner;

public class Main {

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

        System.out.println("\n=== BANK SYSTEM ===");
        System.out.println("1 - Check Balance");
        System.out.println("2 - Deposit");
        System.out.println("3 - Withdraw");
        System.out.println("4 - Exit");
        System.out.print("Choose an option: ");
    }

    public static void main(String[] args) {

        Account account = new Account();

        Scanner scanner = new Scanner(System.in);

        int option;

        do {

            showMenu();

            option = scanner.nextInt();

            switch (option) {

                case 1:
                    account.checkBalance();
                    break;

                case 2:

                    System.out.print("Enter deposit amount: ");
                    double depositAmount = scanner.nextDouble();

                    account.deposit(depositAmount);

                    break;

                case 3:
                    System.out.println("Enter withdrawal amount");
                    double withdrawAmount = scanner.nextDouble();

                    account.withdraw(withdrawAmount);

                    break;

                case 4:
                    System.out.println("Exiting system...");
                    break;

                default:
                    System.out.println("Invalid option.");

            }

        } while (option != 4);

        scanner.close();
    }
}