package com.group76pos;
import java.util.Date;

public class Transaction {
  Product product;
  Date time;
  double amount;
  int quantity;

  Transaction(Product product, Date time, double amount, int quantity) {
    this.product = product;
    this.time = time;
    this.amount = amount;
    this.quantity = quantity;
  }
}
