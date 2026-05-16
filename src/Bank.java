import java.util.ArrayList;
import java.util.Scanner;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

public class Bank {

    public static Account findAccount(ArrayList<Account> accounts, int accountNumber) {

        return Bank.findAccount(accounts, accountNumber);
    }

    public static void saveAccounts(ArrayList<Account> accounts) {

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

    }
