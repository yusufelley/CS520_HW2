package filter;
import java.util.List;
import java.util.Map;

import model.Transaction;

public interface TransactionFilter {
    
    public List<Integer> filter(List<Transaction> transactions);

    public void setFilterParameters(Map<String, Object> parameters);
}