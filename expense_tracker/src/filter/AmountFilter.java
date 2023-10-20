package filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.Transaction;

public class AmountFilter implements TransactionFilter {

    private double amount;
    private String condition;

    @Override
    public List<Integer> filter(List<Transaction> transactions) {
        List<Integer> filteredTransactions = new ArrayList<>();

        for (int i = 0; i < transactions.size(); i++) {
            Transaction transaction = transactions.get(i);
            if (applyAmountFilter(transaction)) {
                filteredTransactions.add(i);
            }
        }

        return filteredTransactions;
    }

    @Override
    public void setFilterParameters(Map<String, Object> parameters) {
        this.amount = (double) parameters.get("amount");
        this.condition = (String) parameters.get("condition");
    }

    private boolean applyAmountFilter(Transaction transaction) {
        double transactionAmount = transaction.getAmount();

        switch (condition.toLowerCase()) {
            case "greaterthan":
                return transactionAmount > amount;
            case "lessthan":
                return transactionAmount < amount;
            case "greaterthanequal":
                return transactionAmount >= amount;
            case "lessthanequal":
                return transactionAmount <= amount;
            case "equalto":
                return transactionAmount == amount;
            default:
                throw new IllegalArgumentException("Invalid condition: " + condition);
        }
    }
}
