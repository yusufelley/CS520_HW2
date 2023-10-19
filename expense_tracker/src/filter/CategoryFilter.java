package filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.Transaction;

public class CategoryFilter implements TransactionFilter{

    private String category;

    @Override
    public List<Transaction> filter(List<Transaction> transactions) {
         List<Transaction> filteredTransactions = new ArrayList<>();

        for (Transaction transaction : transactions) {
            if (applyCategoryFilter(transaction)) {
                filteredTransactions.add(transaction);
            }
        }

        return filteredTransactions;
    }
    
    @Override
    public void setFilterParameters(Map<String, Object> parameters) {
        this.category = (String) parameters.get("category");
    }

    private boolean applyCategoryFilter(Transaction transaction) {
        return transaction.getCategory().equalsIgnoreCase(category);
    }
}
