package com.group76pos;

import java.util.ArrayList;
import com.google.gson.Gson;

public class BankAccountManager implements IMemento {
  public static BankAccountManager instance;

  ArrayList<BankAccount> bankAccounts;

  private BankAccountManager() {}

  public static BankAccountManager getInstance() {
    return instance;
  }

  public BankAccount getBankAccount(String accountNumber) {
    for (BankAccount account: this.bankAccounts) {
      if (account.accountNumber == accountNumber) {
        return account;
      }
    }
    return null;
  }

  @Override
  public Memento save() {
    Gson gson = new Gson();
    String jsonString = gson.toJson(this);
    return new Memento(jsonString);
  }

  @Override
  public void restore(Memento m) {
    Gson gson = new Gson();
    String jsonString = gson.toJson(this);
    return new Memento(jsonString); 
  }
}