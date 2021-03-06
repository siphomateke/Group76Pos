package com.group76pos;

import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.Gson;

public class BankAccountManager implements IMemento {
  public static BankAccountManager instance;

  ArrayList<BankAccount> bankAccounts;

  private BankAccountManager() {
    Customer c1 = new Customer("Justin Brandt", "0812345678", "justinb@email.com");
    Customer c2 = new Customer("Craig Van Niekerk", "08198765432", "craig123@email.com");
    Customer c3 = new Customer("Sipho Mateke", "0818527419", "siphom987@email.com");
    this.bankAccounts = new ArrayList<>(Arrays.asList(
      new BankAccount(c1, "220040869", 10000, (short) 2001),
      new BankAccount(c2, "219002444", 60, (short) 2000),
      new BankAccount(c3, "220101361", 800, (short) 2000)
    ));
  }

  public static BankAccountManager getInstance() {
    if (instance == null) {
      instance = new BankAccountManager();
    }
    return instance;
  }

  public BankAccount getBankAccount(String accountNumber) {
    for (BankAccount account: this.bankAccounts) {
      if (account.accountNumber.equals(accountNumber)) {
        return account;
      }
    }
    return null;
  }

  @Override
  public Memento save() {
    Gson gson = new Gson();
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