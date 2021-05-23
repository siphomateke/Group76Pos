package com.group76pos;

public class Fries extends Product  {
  ProductSize size;

  Fries(int id, String description, int stockQuantity, double purchasePrice, double sellingPrice, int reorderLevel, ProductSize size) {
    super(id, description, stockQuantity, purchasePrice, sellingPrice, reorderLevel);
    this.size = size;
  }
}