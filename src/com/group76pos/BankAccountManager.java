package com.group76pos;

import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.Gson;

public class BankAccountManager implements IMemento {
  public static BankAccountManager instance;

  ArrayList<BankAccount> bankAccounts;

  private BankAccountManager() {
    this.bankAccounts = new ArrayList<>();
  }

  public static BankAccountManager getInstance() {
    if (instance == null) {
      instance = new BankAccountManager();
    }
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
    // TODO: Properly save bank accounts
    String jsonString = gson.toJson(this.bankAccounts);
    return new Memento(jsonString);
  }

  @Override
  public void restore(Memento m) {
    Gson gson = new Gson();
    BankAccount[] restoredAccounts = gson.fromJson(m.state, BankAccount[].class);
    this.bankAccounts = new ArrayList<>(Arrays.asList(restoredAccounts));
  }
}