package com.group76pos;

import java.util.ArrayList;

public class StockManager {
  public static StockManager instance;
  ArrayList<Product> products;

  private StockManager() {}

  public static StockManager getInstance() {
    return instance;
  }

  private void showAlert(String message) {
    // TODO: Auto-generated method stub
    return null;
  }

  public void checkReorderLevels(Product product) {
    // TODO: Auto-generated method stub
    return null;
  }

  @Override
  public Memento save() {
    // TODO: Auto-generated method stub
    return null;
  }

  @Override
  public void restore(Memento m) {
    // TODO: Auto-generated method stub

  }
}
