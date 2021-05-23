package com.group76pos;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.NoSuchFileException;

public class StateManager {
  public static StateManager instance;
  static String salesManagerPath = "salesManagerPath.json";
  static String stockManagerPath = "stockManagerPath.json";
  static String bankAccountManager = "bankAccountManager.json";

  private StateManager() {}

  public static StateManager getInstance() {
    if (instance == null) {
      instance = new StateManager();
    }
    return instance;
  }

  public void save() {
    try {
      SalesManager.getInstance().save().saveToFile(salesManagerPath);
      StockManager.getInstance().save().saveToFile(stockManagerPath);
      BankAccountManager.getInstance().save().saveToFile(bankAccountManager);
    } catch (IOException e) {
      // TODO: Properly handle errors saving files and show error message
      e.printStackTrace();
    }
  }

  public void load() {
    try {
      SalesManager.getInstance().restore(Memento.loadFromFile(salesManagerPath));
      StockManager.getInstance().restore(Memento.loadFromFile(stockManagerPath));
      BankAccountManager.getInstance().restore(Memento.loadFromFile(bankAccountManager));
    } catch (NoSuchFileException e) {
      // If the files don't exist, no need to restore them.
    } catch (IOException e) {
      JOptionPane.showMessageDialog(null, e.toString(), "Error loading application", JOptionPane.ERROR_MESSAGE);
    }
  }
}
