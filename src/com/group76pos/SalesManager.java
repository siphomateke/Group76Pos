package com.group76pos;

import com.google.gson.Gson;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Date;

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

  public String issueReceipt(Sale sale) {
    System.out.println("*******************************\n      Group76 POS Systems\n*******************************\nProducts      Quantity   Amount"+"\n-------------------------------");
    for (Transaction transaction: sale.transactions){
      System.out.println(transaction.product+"           "+transaction.quantity+"    N$"+transaction.amount);
    }
    System.out.println("*******************************\nTotal Amount:          N$"+"\n*******************************"+sale.total)
    ;
    return "";
  }

  private class SavedTransaction extends Transaction {
    int productId;

    SavedTransaction(int productId, Date time, double amount, int quantity) {
      super(null, time, amount, quantity);
      this.productId = productId;
    }
  }

  private class SavedSale extends Sale {
    ArrayList<SavedTransaction> transactions;

    SavedSale(ArrayList<SavedTransaction> transactions, double total, Date timeCompleted) {
      this.transactions = transactions;
      this.total = total;
      this.timeCompleted = timeCompleted;
    }
  }

  /**
   * Converts sales into a format ready for saving to disk. Notably converts products into just product IDs.
   * @return
   */
  @Override
  public Memento save() {
    Gson gson = new Gson();
    // Convert sales to contain transactions with product IDs and not product objects.
    ArrayList<SavedSale> savedSales = new ArrayList<>();
    for (Sale s: this.sales) {
      // Convert transactions to contain product IDs and not product objects.
      ArrayList<SavedTransaction> savedTransactions = new ArrayList<>();
      for (Transaction t: s.transactions) {
        savedTransactions.add(new SavedTransaction(t.product.id, t.time, t.amount, t.quantity));
      }
      savedSales.add(new SavedSale(savedTransactions, s.total, s.timeCompleted));
    }
    String jsonString = gson.toJson(savedSales);
    return new Memento(jsonString);
  }

  @Override
  public void restore(Memento m) {
    Gson gson = new Gson();
    SavedSale[] restored = gson.fromJson(m.state, SavedSale[].class);

    ArrayList<Sale> sales = new ArrayList<>();
    for (SavedSale s: restored) {
      ArrayList<Transaction> transactions = new ArrayList<>();
      for (SavedTransaction t: s.transactions) {

        // Find product from ID
        Product product = null;
        for (Product p: StockManager.getInstance().products) {
          if (p.id == t.productId) {
            product = p;
          }
        }

        transactions.add(new Transaction(product, t.time, t.amount, t.quantity));
      }

      Sale sale = new Sale();
      sale.transactions = transactions;
      sale.total = s.total;
      sale.timeCompleted = s.timeCompleted;
      sales.add(sale);
    }

    this.sales = sales;
  }
}
