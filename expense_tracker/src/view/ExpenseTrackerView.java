package view;

import javax.swing.*;
import javax.swing.JFormattedTextField.AbstractFormatterFactory;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import controller.ExpenseTrackerController;
import controller.InputValidation;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class ExpenseTrackerView extends JFrame {

  private JTable transactionsTable;
  private JButton addTransactionBtn;
  private JFormattedTextField amountField;
  private JTextField categoryField;
  private DefaultTableModel model;
  private JButton undoTransactionBtn; // MARK
  private String filterType;
  private JFormattedTextField filterAmountField; // MARK
  private JComboBox filterAmountConditions; // MARK
  private JTextField filterCategoryField; // MARK
  private JButton applyFilterBtn;

  InputValidation inputValidation = new InputValidation();

  public ExpenseTrackerView() {
    setTitle("Expense Tracker"); // Set title
    setSize(600, 400); // Make GUI larger

    String[] columnNames = { "serial", "Amount", "Category", "Date" };
    this.model = new DefaultTableModel(columnNames, 0);

    addTransactionBtn = new JButton("Add Transaction");
    undoTransactionBtn = new JButton("Undo Transaction"); // MARK
    applyFilterBtn = new JButton("Apply Filter"); // MARK

    // Create UI components
    JLabel amountLabel = new JLabel("Amount:");
    NumberFormat format = NumberFormat.getNumberInstance();

    amountField = new JFormattedTextField(format);
    amountField.setColumns(10);

    JLabel categoryLabel = new JLabel("Category:");
    categoryField = new JTextField(10);

    // Create Filter UI components
    filterAmountField = new JFormattedTextField(format); // Initialize filterAmountField
    filterAmountField.setColumns(10);
    filterCategoryField = new JTextField(10);
    
    // create checkbox
    String conditions[] = { ">", "<", "=", ">=", "<=" }; // MARK
    filterAmountConditions = new JComboBox<String>(conditions);

    // Create table
    transactionsTable = new JTable(model);

    // Enable single-row selection MARK
    transactionsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    // Layout components
    JPanel inputPanel = new JPanel();
    inputPanel.add(amountLabel);
    inputPanel.add(amountField);
    inputPanel.add(categoryLabel);
    inputPanel.add(categoryField);
    inputPanel.add(addTransactionBtn); // MARK

    // Create Filter UI components
    JLabel filterLabel = new JLabel("Filters");

    // Create radio buttons for filter options
    JRadioButton amountFilterRadio = new JRadioButton("Filter by Amount");
    JRadioButton categoryFilterRadio = new JRadioButton("Filter by Category");

    // Create a button group for radio buttons
    ButtonGroup filterGroup = new ButtonGroup();
    filterGroup.add(amountFilterRadio);
    filterGroup.add(categoryFilterRadio);

    // Create panels for filter components
    JPanel filterPanel = new JPanel();
    JPanel amountFilterPanel = new JPanel();
    JPanel categoryFilterPanel = new JPanel();

    // Add components to panels
    amountFilterPanel.setLayout(new BoxLayout(amountFilterPanel, BoxLayout.PAGE_AXIS));

    JPanel amountLabelPanel = new JPanel();
    amountLabelPanel.add(new JLabel("Amount:"));
    amountFilterPanel.add(amountLabelPanel);
    amountFilterPanel.add(filterAmountField);

    JPanel conditionLabelPanel = new JPanel();
    conditionLabelPanel.add(new JLabel("Condition:"));
    amountFilterPanel.add(conditionLabelPanel);
    amountFilterPanel.add(filterAmountConditions);

    categoryFilterPanel.add(new JLabel("Category:"));
    categoryFilterPanel.add(filterCategoryField);

    // Add panels to filter panel
    filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.PAGE_AXIS));
    filterPanel.add(filterLabel);
    filterPanel.add(amountFilterRadio);
    filterPanel.add(amountFilterPanel);
    filterPanel.add(categoryFilterRadio);
    filterPanel.add(categoryFilterPanel);
    filterPanel.add(applyFilterBtn);

    // Add listener for radio buttons
    amountFilterRadio.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        amountFilterPanel.setVisible(true);
        categoryFilterPanel.setVisible(false);
        setFilterType("amount");
        List<Integer> empty_list = new ArrayList<Integer>();
        colorFilteredTransactions(empty_list);
      }
    });

    categoryFilterRadio.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        amountFilterPanel.setVisible(false);
        categoryFilterPanel.setVisible(true);
        setFilterType("category");
        List<Integer> empty_list = new ArrayList<Integer>();
        colorFilteredTransactions(empty_list);
      }
    });

    // Set default visibility
    amountFilterPanel.setVisible(false);
    categoryFilterPanel.setVisible(false);

    JPanel buttonPanel = new JPanel();
    buttonPanel.add(addTransactionBtn);
    buttonPanel.add(undoTransactionBtn); // MARK

    // Add panels to frame
    add(inputPanel, BorderLayout.NORTH);
    add(new JScrollPane(transactionsTable), BorderLayout.CENTER);
    add(buttonPanel, BorderLayout.SOUTH);
    add(filterPanel, BorderLayout.EAST);

    // Set frame properties
    setSize(400, 300);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }

  public void refreshTable(List<Transaction> transactions) {
    // Clear existing rows
    model.setRowCount(0);
    // Get row count
    int rowNum = model.getRowCount();
    double totalCost = 0;
    // Calculate total cost
    for (Transaction t : transactions) {
      totalCost += t.getAmount();
    }
    // Add rows from transactions list
    for (Transaction t : transactions) {
      model.addRow(new Object[] { rowNum += 1, t.getAmount(), t.getCategory(), t.getTimestamp() });
    }
    // Add total row
    Object[] totalRow = { "Total", null, null, totalCost };
    model.addRow(totalRow);

    // Fire table update
    transactionsTable.updateUI();

  }

  public JButton getAddTransactionBtn() {
    return addTransactionBtn;
  }

  public JButton getUndoTransactionBtn() { // MARK
    return undoTransactionBtn;
  }

  public JButton getApplyFilterBtn() { // MARK
    return applyFilterBtn;
  }

  public DefaultTableModel getTableModel() {
    return model;
  }

  // Other view methods
  public JTable getTransactionsTable() {
    return transactionsTable;
  }

  public double getAmountField() {
    if (amountField.getText().isEmpty()) {
      return 0;
    } else {
      double amount = Double.parseDouble(amountField.getText());
      return amount;
    }
  }

  public void setAmountField(JFormattedTextField amountField) {
    this.amountField = amountField;
  }

  public String getCategoryField() {
    return categoryField.getText();
  }

  public void setCategoryField(JTextField categoryField) {
    this.categoryField = categoryField;
  }

  public int getSelectedRow() { // MARK
    return transactionsTable.getSelectedRow();
  }

  public void setFilterType(String filterType) { // MARK
    this.filterType = filterType;
  }

  public String getFilterType() { // MARK
    if(this.filterType != null)
      return this.filterType;
    return "";
  }

  public double getFilterAmount() { // MARK
    if (filterAmountField.getText().isEmpty() || !InputValidation.isValidAmount(Double.parseDouble(filterAmountField.getText()))) {
      return -1.0;
    } else {
      double filterAmount = Double.parseDouble(filterAmountField.getText());
      return filterAmount;
    }
  }

  public String getFilterAmountCondition() { // MARK
    String conditon = filterAmountConditions.getSelectedItem().toString();
    if(conditon.equals(">")) return "greaterthan";
    else if(conditon.equals("<")) return "lessthan";
    else if(conditon.equals(">=")) return "greaterthanequal";
    else if(conditon.equals("<=")) return "lessthanequal";
    else return "equalto";
  }

  public String getFilterCategory() { // MARK
    if (!InputValidation.isValidCategory(filterCategoryField.getText())) 
      return "";
    return filterCategoryField.getText();
  }

  public void colorFilteredTransactions(List<Integer> filteredTransactions){
    transactionsTable.setDefaultRenderer(Object.class, new ColoredRowRenderer(filteredTransactions)); // Index of the row to be colored
    transactionsTable.repaint();
  }
}


class ColoredRowRenderer extends DefaultTableCellRenderer {
    private List<Integer> targetRows;

    public ColoredRowRenderer(List<Integer> targetRows) {
        this.targetRows = targetRows;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component rendererComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (targetRows.contains(row)) {
            // Set background color for the specified rows
            rendererComponent.setBackground(Color.GREEN);
        } else {
            // Reset background color for other rows
            rendererComponent.setBackground(table.getBackground());
        }
        return rendererComponent;
    }
}