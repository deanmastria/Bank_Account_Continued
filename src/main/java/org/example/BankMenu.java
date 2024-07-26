package org.example;

import java.util.Scanner;
import java.util.ArrayList;

public class BankMenu {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<BankAccountContinued> accounts = new ArrayList<>();

        while (true) {
            System.out.println("Bank Menu:");
            System.out.println("1. Create a new account");
            System.out.println("2. Select an existing account");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            if (choice == 1) {
                // Create a new account
                System.out.println("Let's make a new account!");
                BankAccountContinued newAccount = new BankAccountContinued();
                accounts.add(newAccount);
                greetUser(newAccount);
                mainMenu(newAccount, accounts);
            } else if (choice == 2) {
                // Select an existing account
                System.out.print("Enter account number: ");
                String accountNumber = scanner.nextLine();
                BankAccountContinued account = findAccount(accounts, accountNumber);
                if (account != null) {
                    greetUser(account);
                    mainMenu(account, accounts);
                } else {
                    System.out.println("Account not found.");
                }
            } else if (choice == 3) {
                // Exit the program
                System.out.println("Goodbye!");
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Method to greet the user by their name
    private static void greetUser(BankAccountContinued account) {
        System.out.println("Hello, " + account.getAccountHolderName() + "!");
    }

    // Main menu method for interacting with the selected account
    private static void mainMenu(BankAccountContinued account, ArrayList<BankAccountContinued> accounts) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Account Menu:");
            System.out.println("1. Check balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Back to main menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            if (choice == 1) {
                // Check balance
                System.out.println(account);
            } else if (choice == 2) {
                // Deposit into the account
                System.out.print("Enter amount to deposit: ");
                double amount = scanner.nextDouble();
                account.deposit(amount);
            } else if (choice == 3) {
                // Withdraw from the account
                System.out.print("Enter amount to withdraw: ");
                double amount = scanner.nextDouble();
                account.withdrawal(amount);
            } else if (choice == 4) {
                // Transfer funds to another account
                System.out.print("Enter recipient's account number: ");
                String toAccountNumber = scanner.nextLine();
                BankAccountContinued toAccount = findAccount(accounts, toAccountNumber);
                if (toAccount != null) {
                    System.out.print("Enter amount to transfer: ");
                    double amount = scanner.nextDouble();
                    account.transfer(toAccount, amount);
                } else {
                    System.out.println("Recipient account not found. Would you like to create a new account? (yes/no)");
                    String response = scanner.nextLine();
                    if (response.equalsIgnoreCase("yes")) {
                        System.out.println("Creating new recipient account.");
                        BankAccountContinued newAccount = new BankAccountContinued();
                        accounts.add(newAccount);
                        System.out.print("Enter amount to transfer: ");
                        double amount = scanner.nextDouble();
                        account.transfer(newAccount, amount);
                    } else {
                        System.out.println("Transfer cancelled.");
                    }
                }
            } else if (choice == 5) {
                // Back to main menu
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Method to find an account by account number
    private static BankAccountContinued findAccount(ArrayList<BankAccountContinued> accounts, String accountNumber) {
        String normalizedAccountNumber = accountNumber.replace("-", "");  // Remove hyphens for comparison
        for (BankAccountContinued account : accounts) {
            if (account.getAccountNumber().equals(normalizedAccountNumber)) {
                return account;
            }
        }
        return null;
    }
}
