import java.util.Scanner;

public class Main {

    public static void showMenu() {

        System.out.println("\n=== BANK SYSTEM ===");
        System.out.println("1 - Check Balance");
        System.out.println("2 - Deposit");
        System.out.println("3 - Withdraw");
        System.out.println("4 - Exit");
        System.out.print("Choose an option: ");
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        double balance = 1000.0;
        int option;

        do {

            showMenu();

            option = scanner.nextInt();

            switch (option) {

                case 1:
                    System.out.println("Current balance: $" + balance);
                    break;

                case 2:
                    System.out.print("Enter deposit amount: ");
                    double deposit = scanner.nextDouble();
                    balance += deposit;
                    System.out.println("Deposit successful!");
                    break;

                case 3:
                    System.out.print("Enter withdraw amount: ");
                    double withdraw = scanner.nextDouble();

                    if (withdraw <= balance) {
                        balance -= withdraw;
                        System.out.println("Withdraw successful!");
                    } else {
                        System.out.println("Insufficient balance.");
                    }

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