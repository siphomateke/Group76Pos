package com.group76pos;

public class Burger extends Product {
    boolean hasCheese;
    boolean hasLettuce;

    Burger(int id, String description, int stockQuantity, double purchasePrice, double sellingPrice, int reorderLevel, boolean hasCheese, boolean hasLettuce) {
        super(id, description, stockQuantity, purchasePrice, sellingPrice, reorderLevel);
        this.hasCheese = hasCheese;
        this.hasLettuce = hasLettuce;
    }
}