import javax.swing.JOptionPane;
import controller.ExpenseTrackerController;
import model.ExpenseTrackerModel;
import view.ExpenseTrackerView;

public class ExpenseTrackerApp {

  public static void main(String[] args) {

    // Create MVC components
    ExpenseTrackerModel model = new ExpenseTrackerModel();
    ExpenseTrackerView view = new ExpenseTrackerView();
    ExpenseTrackerController controller = new ExpenseTrackerController(model, view);

    // Initialize view
    view.setVisible(true);

    // Handle add transaction button clicks
    view.getAddTransactionBtn().addActionListener(e -> {
      // Get transaction data from view
      double amount = view.getAmountField();
      String category = view.getCategoryField();

      // Call controller to add transaction
      boolean added = controller.addTransaction(amount, category);

      if (!added) {
        JOptionPane.showMessageDialog(view, "Invalid amount or category entered");
        view.toFront();
      }
    });

    // Handle undo transaction button clicks
    view.getUndoTransactionBtn().addActionListener(e -> { 
      System.out.println(controller.undoTransaction(view.getSelectedRow()));
    });

    // Handle filter button clicks
    view.getApplyFilterBtn().addActionListener(e -> { 
      controller.applyFilter();
    });
  }
}