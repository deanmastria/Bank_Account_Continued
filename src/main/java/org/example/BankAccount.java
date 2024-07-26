package org.example;

import java.util.Scanner;
import java.util.ArrayList;

public class BankAccount {
    private String accountNumber;  // Store the account number without hyphens
    private String accountHolderName;  // Store the name of the account holder
    private double balance;  // Store the current balance

    // Constructor to initialize the account holder's name, balance, and account number
    public BankAccount(String accountNumber, String accountHolderName, double balance) {
        this.accountHolderName = capitalizeName(accountHolderName);
        this.balance = balance;
        this.accountNumber = accountNumber.replace("-", "");  // Remove hyphens from the account number
        System.out.println("Welcome to The First National Bank of Deano");
        System.out.println(this.accountHolderName + " " + formatAccountNumber(this.accountNumber) + " Available Funds: " + String.format("%.2f", balance));
    }

    // Constructor to initialize the account holder's details via user input
    public BankAccount(ArrayList<BankAccount> accounts) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter account holder first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter account holder last name: ");
        String lastName = scanner.nextLine();
        String fullName = capitalizeName(firstName + " " + lastName);

        // Check if the user already has an account
        for (BankAccount account : accounts) {
            if (account.getAccountHolderName().equalsIgnoreCase(fullName)) {
                System.out.println("You already have an account.");
                return;
            }
        }

        this.accountHolderName = fullName;
        System.out.print("Enter starting balance: ");
        this.balance = scanner.nextDouble();
        scanner.nextLine(); // Consume newline left-over

        String accountNumber;
        while (true) {
            System.out.print("Enter 9-digit account number: ");
            accountNumber = scanner.nextLine();
            if (isValidAccountNumber(accountNumber)) {  // Validate the account number
                break;
            } else {
                System.out.println("Account number must be exactly 9 digits.");
            }
        }
        this.accountNumber = accountNumber.replace("-", "");  // Store account number without hyphens
        System.out.println(this.accountHolderName + " " + formatAccountNumber(this.accountNumber) + " Available Funds: " + String.format("%.2f", balance));
    }

    // Method to validate the account number
    private boolean isValidAccountNumber(String accountNumber) {
        return accountNumber.replace("-", "").length() == 9 && accountNumber.replace("-", "").matches("[0-9]{9}");
    }

    // Method to capitalize the account holder's name
    private String capitalizeName(String name) {
        String[] parts = name.split(" ");
        StringBuilder capitalize = new StringBuilder();
        for (String part : parts) {
            if (part.length() > 0) {
                capitalize.append(part.substring(0, 1).toUpperCase()).append(part.substring(1).toLowerCase()).append(" ");
            }
        }
        return capitalize.toString().trim();
    }

    // Helper method to format the account number with hyphens
    private String formatAccountNumber(String accountNumber) {
        return accountNumber.replaceAll("(\\d{3})(?=\\d)", "$1-");
    }

    // Method for depositing funds into the account
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println(" ");
            System.out.println("Deposited " + String.format("%.2f", amount) + " to " + accountHolderName);
            System.out.println("New balance: " + String.format("%.2f", balance));
        } else {
            System.out.println(" ");
            System.out.println("Cannot deposit " + String.format("%.2f", amount) + " to " + accountHolderName);
            System.out.println("Please enter a positive amount");
        }
    }

    // Method for withdrawing funds from the account
    public void withdrawal(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println(" ");
            System.out.println("Withdrew " + String.format("%.2f", amount) + " from " + accountHolderName);
            System.out.println("New balance: " + String.format("%.2f", balance));
        } else if (amount > balance) {
            System.out.println(" ");
            System.out.println("Cannot withdraw " + String.format("%.2f", amount) + " from " + accountHolderName);
            System.out.println("Insufficient balance");
        } else {
            System.out.println(" ");
            System.out.println("Please enter a positive amount");
        }
    }

    // Method for transferring funds to another account
    public void transfer(BankAccount toAccount, double amount) {
        if (amount > 0 && amount <= balance) {
            this.withdrawal(amount);  // Withdraw from current account
            toAccount.deposit(amount);  // Deposit into the recipient's account
            System.out.println("Transferred " + String.format("%.2f", amount) + " from " + this.accountHolderName + " to " + toAccount.accountHolderName);
        } else {
            System.out.println("Transfer failed. Please check the amount and try again.");
        }
    }

    // Method to print account details
    @Override
    public String toString() {
        return accountHolderName + " " + formatAccountNumber(accountNumber) + " Available Funds: " + String.format("%.2f", balance);
    }

    // Getter for account number
    public String getAccountNumber() {
        return accountNumber;
    }

    // Getter for account holder's name
    public String getAccountHolderName() {
        return accountHolderName;
    }

    public static void main(String[] args) {
        ArrayList<BankAccount> accounts = new ArrayList<>();

        // Create the account for Jack Johnson with 500 dollars
        BankAccount account1 = new BankAccount("888999555", "Jack Johnson", 500.00);
        accounts.add(account1);

        // Added additional accounts for the second part of the assignment
        BankAccount account2 = new BankAccount("468997556", "Leonardo DiCaprionardo", 5000.00);
        accounts.add(account2);

        BankAccount account3 = new BankAccount("488699675", "Don Johnson", 300.00);
        accounts.add(account3);

        // Check if the user already has an account and create a new account if they don't
        BankAccount newAccount = new BankAccount(accounts);
        if (newAccount.getAccountHolderName() != null) {
            accounts.add(newAccount);
        }

        account1.deposit(100.00);
        System.out.println(account1);

        account1.withdrawal(50.00);
        System.out.println(account1);

        // Withdraw 100 dollars from Leonardo DiCaprionardo
        account2.withdrawal(100.00);

        // Deposit 100 dollars into Don Johnson
        account3.deposit(100.00);

        // Print final account details
        System.out.println(" ");
        System.out.println("Final Account Details:");
        for (BankAccount account : accounts) {
            System.out.println(account);
        }
    }
}


