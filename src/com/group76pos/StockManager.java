package com.group76pos;

import java.util.ArrayList;

public class StockManager implements IMemento {
  public static StockManager instance;
  ArrayList<Product> products;

  private StockManager() {}

  public static StockManager getInstance() {
    return instance;
  }

  private void showAlert(String message) {
    // TODO: Actually show alert in UI
  }

  public void checkReorderLevels(Product product) {
    if (product.stockQuantity < product.reorderLevel) {
      this.showAlert(String.format("%s are running low %d / %d", product.description, product.stockQuantity, product.reorderLevel));
    }
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
