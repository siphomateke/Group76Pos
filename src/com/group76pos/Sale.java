package com.group76pos;

import java.util.ArrayList;
import java.util.Date;

public class Sale {
  ArrayList<Transaction> transactions;
  double total;
  Date timeCompleted;

  Sale() {
    this.transactions = new ArrayList<>();
    this.total = 0;
  }

  public double calculateTotal() {
    double total = 0;
    for (Transaction t: this.transactions) {
      total += t.quantity * t.amount;
    }
    return total;
  }

  public void cancel() {
    this.transactions = new ArrayList<>();
  }

  public void checkout() throws Exception {
    this.total = this.calculateTotal();

    // FIXME: Get account number and PIN
    String customerAccountNumber = "";
    short customerPin = 1234;

    // Make sure the customer's PIN is valid
    BankAccount account = BankAccountManager.getInstance().getBankAccount(customerAccountNumber);
    boolean validPin = account.verifyPin(customerPin);
    if (validPin) {
      // Make sure they have a sufficient balance
      if (account.balance >= this.total) {
        // Checkout has been approved
        this.timeCompleted = new Date(System.currentTimeMillis());

        // Reduce stock count of all products involved transaction by 1
        for (Transaction t: transactions) {
          Product p = t.product;
          p.updateStock(p.stockQuantity - 1);
        }

        // Finally, store the successful sale for historical purposes
        SalesManager.getInstance().addSale(this);
      } else {
        throw new Exception("Insufficient funds");
      }
    } else {
      throw new Exception("Invalid PIN");
    }
  }

  public void removeTransaction(Transaction t) {
    this.transactions.remove(t);
  }

  public void addTransaction(Transaction t) {
    this.transactions.add(t);
  }
}