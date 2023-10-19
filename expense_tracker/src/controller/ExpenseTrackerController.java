package controller;

import view.ExpenseTrackerView;

import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import filter.AmountFilter;
import filter.CategoryFilter;
import model.ExpenseTrackerModel;
import model.Transaction;

public class ExpenseTrackerController {

  private ExpenseTrackerModel model;
  private ExpenseTrackerView view;

  public ExpenseTrackerController(ExpenseTrackerModel model, ExpenseTrackerView view) {
    this.model = model;
    this.view = view;

    // Set up view event handlers
  }

  public void refresh() {

    // Get transactions from model
    List<Transaction> transactions = model.getTransactions();

    // Pass to view
    view.refreshTable(transactions);

  }

  public boolean addTransaction(double amount, String category) {
    if (!InputValidation.isValidAmount(amount)) {
      return false;
    }
    if (!InputValidation.isValidCategory(category)) {
      return false;
    }

    Transaction t = new Transaction(amount, category);
    model.addTransaction(t);
    view.getTableModel().addRow(new Object[] { t.getAmount(), t.getCategory(), t.getTimestamp() });
    refresh();
    return true;
  }

  // Other controller methods
  public boolean undoTransaction(int index) { // MARK

    try {
      if (index == -1) {
        throw new Exception("Please select a row to undo it");
      }
      Transaction transaction = model.getTransactions().get(index);
      model.removeTransaction(transaction);
      view.getTableModel().removeRow(0);
      refresh();
      return true;
    } catch (Exception e) {
      JOptionPane.showMessageDialog(
          null,
          e.getMessage(),
          "Alert!", JOptionPane.ERROR_MESSAGE);
    }
    return false;
  }

  public void applyFilter() {
    List<Transaction> transactions = model.getTransactions();
    String filterType = view.getFilterType();
    if (filterType.equals("amount")) {
      double amount = view.getFilterAmount();
      String amountCondition = view.getFilterAmountCondition();
      AmountFilter amountFilter = new AmountFilter();
      amountFilter.setFilterParameters(Map.of("amount", amount, "condition", amountCondition));
      List<Transaction> filteredTransactions = amountFilter.filter(transactions);
      displayTransactions(filteredTransactions);
    } 
    else if (filterType.equals("category")) {
      String category = view.getFilterCategory();
      CategoryFilter categoryFilter = new CategoryFilter();
      categoryFilter.setFilterParameters(Map.of("category", category));
      List<Transaction> filteredTransactions = categoryFilter.filter(transactions);
      displayTransactions(filteredTransactions);
    } else {
      JOptionPane.showMessageDialog(
          null,
          "Please choose a valid filter and appropriate conditions!!",
          "Alert!", JOptionPane.ERROR_MESSAGE);
    }
  }

  private static void displayTransactions(List<Transaction> transactions) {
    for (Transaction transaction : transactions) {
        System.out.println("Amount: " + transaction.getAmount() + ", Category: " + transaction.getCategory());
    }
}
}