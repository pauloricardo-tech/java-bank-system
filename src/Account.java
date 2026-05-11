public class Account {

double balance = 1000;

public void checkBalance() {
    System.out.println("Current balance: $" + balance);
}
public void deposit(double amount) {

    balance += amount;

    System.out.println("Deposit successful!");
}
public void withdraw(double amount) {

    if (amount > balance) {

        System.out.println("Insufficient balance!");

    } else {

        balance -= amount;

        System.out.println("Withdrawal successful!");
    }
}

}