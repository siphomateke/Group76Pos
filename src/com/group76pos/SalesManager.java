package com.group76pos;

import com.google.gson.Gson;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;

public class SalesManager implements IMemento {
  public static SalesManager instance;
  ArrayList<Sale> sales;

  private Sale activeSale;
  private String customerAccountNumber;
  private short customerPin;

  private SalesManager() {
    this.sales = new ArrayList<>();
  }

  public static SalesManager getInstance() {
    if (instance == null) {
      instance = new SalesManager();
    }
    return instance;
  }

  public void generateReport(int month, ReportGroupBy groupBy) {
    // TODO: Implement
  }

  public void addSale(Sale sale) {
    this.sales.add(sale);
  }

  private void getAccountNumber() {
    App.showPage("accountNumber");
  }

  public void setAccountNumber(String accountNumber) {
    this.customerAccountNumber = accountNumber;
    App.showPage("pinNumber");
  }

  public void setAccountPin(short pin) {
    customerPin = pin;
    finishCheckout();
  }

  public void finishCheckout() {
    try {
      // Make sure the customer's PIN is valid
      BankAccount account = BankAccountManager.getInstance().getBankAccount(customerAccountNumber);
      if (account != null) {
        boolean validPin = account.verifyPin(customerPin);
        if (validPin) {
          // Make sure they have a sufficient balance
          if (account.balance >= activeSale.total) {
            // Checkout has been approved
            activeSale.timeCompleted = new Date(System.currentTimeMillis());

            // Reduce stock count of all products involved transaction by 1
            for (Transaction t : activeSale.transactions) {
              Product p = t.product;
              p.updateStock(p.stockQuantity - 1);
            }

            // Finally, store the successful sale for historical purposes
            this.addSale(activeSale);

            JOptionPane.showMessageDialog(null, "Success! Transaction approved", "Checkout", JOptionPane.INFORMATION_MESSAGE);
            App.getInstance().clearSale();
            App.showPage("dashboard");

            JOptionPane.showMessageDialog(null, SalesManager.getInstance().issueReceipt(activeSale), "Receipt", JOptionPane.INFORMATION_MESSAGE);

            StateManager.getInstance().save();
          } else {
            throw new Exception("Insufficient funds");
          }
        } else {
          throw new Exception("Invalid PIN");
        }
      } else {
        throw new Exception(String.format("Bank Account %s not found", customerAccountNumber));
      }
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e.toString(), "Checkout error", JOptionPane.ERROR_MESSAGE);
    }
  }

  public void checkout(Sale sale) {
    this.activeSale = sale;
    this.getAccountNumber();
  }

  public String issueReceipt(Sale sale) {
    String output = "\n*************************************************\n             Group76 POS Systems";
    int maxNameLength = 0;
    int maxQuantityLength = 0;
    int maxAmountLength = 0;
    for (Transaction transaction: sale.transactions) {
      if (transaction.product.toString().length() > maxNameLength) {
        maxNameLength = transaction.product.toString().length();
      }
      String quantity =Integer.toString(transaction.quantity);
      if (quantity.length() > maxQuantityLength) {
        maxQuantityLength = quantity.length();
      }
      String amount = Double.toString(transaction.amount);
      if (amount.length() > maxAmountLength) {
        maxAmountLength = amount.length();
      }
    }
    String columnFormat = "\n%-"+(maxNameLength+3)+"s %-"+(maxQuantityLength+8)+"s %-"+(maxAmountLength+5)+"s";
    output += "\n*************************************************";
    output += String.format(columnFormat, "Products", "Quantity", "Amount");
    output += "\n-------------------------------------------------";
    for (Transaction transaction: sale.transactions){
      output += String.format(columnFormat, transaction.product, transaction.quantity, "N$"+transaction.amount);
    }
    output += String.format("\n*************************************************\nTotal Amount:                N$"+sale.total+"\n");
    output += "*************************************************";
    return output;
  }

  private class SavedTransaction extends Transaction {
    int productId;

    SavedTransaction(int productId, Date time, double amount, int quantity) {
      super(null, time, amount, quantity);
      this.productId = productId;
    }
  }

  private class SavedSale extends Sale {
    ArrayList<SavedTransaction> transactionsWithIds;

    SavedSale(ArrayList<SavedTransaction> transactions, double total, Date timeCompleted) {
      this.transactionsWithIds = transactions;
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
      for (SavedTransaction t: s.transactionsWithIds) {

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
