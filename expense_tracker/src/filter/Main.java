package filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.Transaction;

public class Main {
    public static void main(String[] args) {
        // Create sample transactions
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(150.0, "Groceries"));
        transactions.add(new Transaction(1200.0, "Electronics"));
        transactions.add(new Transaction(80.0, "Clothing"));
        transactions.add(new Transaction(30.0, "Groceries"));
        transactions.add(new Transaction(800.0, "Electronics"));

        // Create and apply filters
        // AmountFilter amountFilter = new AmountFilter();
        // amountFilter.setFilterParameters(Map.of("amount", 10.0, "condition", "greaterThan"));

        CategoryFilter categoryFilter = new CategoryFilter();
        categoryFilter.setFilterParameters(Map.of("category", "Groceries"));

        // List<Transaction> filteredTransactions = amountFilter.filter(transactions);
        List<Transaction> filteredTransactions = categoryFilter.filter(transactions);

        // Display the results
        System.out.println("Original Transactions:");
        displayTransactions(transactions);

        System.out.println("\nFiltered Transactions:");
        displayTransactions(filteredTransactions);
    }

    private static void displayTransactions(List<Transaction> transactions) {
        for (Transaction transaction : transactions) {
            System.out.println("Amount: " + transaction.getAmount() + ", Category: " + transaction.getCategory());
        }
    }
}

