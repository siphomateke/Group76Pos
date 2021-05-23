package com.group76pos;

public class BankAccount {
  Customer customer;
  String accountNumber;
  double balance = 0;
  short pin;

  BankAccount(Customer customer, String accountNumber, double balance, short pin) {
    this.customer = customer;
    this.accountNumber = accountNumber;
    this.balance = balance;
    this.pin = pin;
  }

  public boolean verifyPin(short pin) {
    return this.pin == pin;
  }

  public void updateBalance(double newBalance) {
    this.balance = newBalance;
  }
}
