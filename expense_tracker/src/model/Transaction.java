package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {

  // made private and final to ensure immutability
  private final double amount;
  private final String category;
  private final String timestamp;

  public Transaction(double amount, String category) {
    this.amount = amount;
    this.category = category;
    this.timestamp = generateTimestamp();
  }

  public double getAmount() {
    return this.amount;
  }

  public String getCategory() {
    return this.category;
  }

  public String getTimestamp() {
    return this.timestamp;
  }

  private String generateTimestamp() {
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    return sdf.format(new Date());
  }

}