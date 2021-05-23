package com.group76pos;

import java.util.ArrayList;
import java.util.Date;

public class Sale {
  ArrayList<Transaction> transactions;
  double total;
  Date timeCompleted;
  Customer customer;

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

  public void checkout() {
    this.total = this.calculateTotal();
    SalesManager.getInstance().checkout(this);
  }

  public void removeTransaction(Transaction t) {
    this.transactions.remove(t);
  }

  public void addTransaction(Transaction t) {
    this.transactions.add(t);
  }
}