package model;

import java.util.ArrayList;
import java.util.List;

public class ExpenseTrackerModel {

  private List<Transaction> transactions;

  public ExpenseTrackerModel() {
    transactions = new ArrayList<>();
  }

  public void addTransaction(Transaction t) {
    transactions.add(t);
  }

  public void removeTransaction(Transaction t) {
    transactions.remove(t);
  }

  public List<Transaction> getTransactions() {
    // Returning a copy of the transactions list to avoid external modification and
    // ensuring immutability
    return new ArrayList<>(transactions);
  }

}