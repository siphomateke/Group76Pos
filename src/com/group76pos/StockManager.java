package com.group76pos;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

public class StockManager implements IMemento {
  public static StockManager instance;
  ArrayList<Product> products;

  private StockManager() {
    this.products = new ArrayList<>(Arrays.asList(
      new Product(1, "Chicken Wrap with Mayo", 10, 60, 84, 5),
      new Burger(2, "Veggie Burger", 50, 40, 65, 45, false, true),
      new Fries(3, "Large Chips", 60, 10, 20, 50, ProductSize.Large),
      new Product(4, "Salad", 5, 5, 15, 0),
      new Drink(5, "Small Coke Can", 70, 4, 8, 50, ProductSize.Small, false),
      new Drink(6, "Large Coke", 50, 10, 16, 35, ProductSize.Large, true)
    ));
  }

  public static StockManager getInstance() {
    if (instance == null) {
      instance = new StockManager();
    }
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
    Gson gson = new Gson();
    String jsonString = gson.toJson(this.products);
    return new Memento(jsonString);
  }

  @Override
  public void restore(Memento m) {
    Gson gson = new Gson();
    Product[] restoredProducts = gson.fromJson(m.state, Product[].class);
    for (Product p: restoredProducts) {
      for (int i = 0; i < this.products.size(); i++) {
        if (p.id == this.products.get(i).id) {
          this.products.set(i, p);
        }
      }
    }
  }
}
