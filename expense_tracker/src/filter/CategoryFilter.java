package filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.Transaction;

public class CategoryFilter implements TransactionFilter {

    private String category;

    @Override
    public List<Integer> filter(List<Transaction> transactions) {
        List<Integer> filteredTransactions = new ArrayList<>();

        for (int i = 0; i < transactions.size(); i++) {
            Transaction transaction = transactions.get(i);
            if (applyCategoryFilter(transaction)) {
                filteredTransactions.add(i);
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
