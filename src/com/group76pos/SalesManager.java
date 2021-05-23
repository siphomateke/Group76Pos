package com.group76pos;

import java.util.ArrayList;

public class SalesManager implements IMemento {
  public static SalesManager instance;
  ArrayList<Sale> sales;

  private SalesManager() {}

  public static SalesManager getInstance() {
    return instance;
  }

  public void generateReport(int month, ReportGroupBy groupBy) {
    // TODO: Implement
  }

  public void addSale(Sale sale) {
    this.sales.add(sale);
  }

  public String issueReceipt() {
    // TODO: Implement
    return "";
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
