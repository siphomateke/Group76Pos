package com.group76pos;

public class Drink  extends Product {
  ProductSize size;
  boolean isBottle;

  Drink(int id, String description, int stockQuantity, double purchasePrice, double sellingPrice, int reorderLevel, ProductSize size, boolean isBottle) {
    super(id, description, stockQuantity, purchasePrice, sellingPrice, reorderLevel);
    this.size = size;
    this.isBottle = isBottle;
  }
}