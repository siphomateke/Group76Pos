package com.group76pos;

import java.lang.reflect.Member;
import com.google.gson.Gson;

public class StateManager implements IMemento {
  public static StateManager instance;
  static String salesManagerPath = "salesManagerPath.json";
  static String stockManagerPath = "stockManagerPath.json";
  static String bankAccountManager = "bankAccountManager.json";

  private StateManager() {}

  public static StateManager getInstance() {
    return instance;
  }

  public void save() {
    SalesManager.getInstance().save().saveToFile(salesManagerPath);
    StockManager.getInstance().save().saveToFile(stockManagerPath);
    BankAccountManager.getInstance().save().saveToFile(bankAccountManager);
  }

  public void load() {
    SalesManager.getInstance().restore(new Memento().loadFromFile(salesManagerPath));
    StockManager.getInstance().restore(new Memento().loadFromFile(stockManagerPath));
    BankAccountManager.getInstance().restore(new Memento().loadFromFile(bankAccountManager));
  }
}
