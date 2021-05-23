package com.group76pos;

public class Product {
  int id;
  String description;
  int stockQuantity;
  double purchasePrice;
  double sellingPrice;
  int reorderLevel;

  Product(int id, String description, int stockQuantity, double purchasePrice, double sellingPrice, int reorderLevel) {
    this.id = id;
    this.description = description;
    this.stockQuantity = stockQuantity;
    this.purchasePrice = purchasePrice;
    this.sellingPrice = sellingPrice;
    this.reorderLevel = reorderLevel;
  }

  public void updateStock(int quantity) {
    this.stockQuantity = quantity;

    StockManager.getInstance().checkReorderLevels(this);
  }

  @Override
  public String toString() {
    return description;
  }
}
