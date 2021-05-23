package com.group76pos;

import com.google.gson.Gson;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
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

  private class ReportRow {
    private Customer customer;
    private Transaction transaction;

    ReportRow(Customer customer, Transaction transaction) {
      this.customer = customer;
      this.transaction = transaction;
    }

    @Override
    public String toString() {
      return String.join(",", new String[]{
        "\""+customer.name+"\"",
        "\""+transaction.product.description+"\"",
        Double.toString(transaction.product.sellingPrice),
        Integer.toString(transaction.quantity),
        Double.toString(transaction.amount),
        transaction.time.toString(),
      });
    }
  }

  public void generateReport(int month, ReportGroupBy groupBy) {
    try {
      ArrayList<ReportRow> reportRows = new ArrayList<>();
      String reportName;
      if (groupBy != null) {
        switch (groupBy) {
          case Product: {
            for (Sale s : this.sales) {
              for (Transaction t : s.transactions) {
                reportRows.add(new ReportRow(s.customer, t));
              }
            }
            reportRows.sort(Comparator.comparingInt(r -> r.transaction.product.id));
            break;
          }
          case Customer: {
            ArrayList<Sale> sortedSales = new ArrayList<>(this.sales);
            sortedSales.sort(Comparator.comparing(s -> s.customer.name));
            for (Sale s : sortedSales) {
              for (Transaction t : s.transactions) {
                reportRows.add(new ReportRow(s.customer, t));
              }
            }
            break;
          }
          case Transfer: {
            // group transactions by sales
            for (Sale s : this.sales) {
              for (Transaction t : s.transactions) {
                reportRows.add(new ReportRow(s.customer, t));
              }
            }
            break;
          }
        }
        reportName = groupBy.toString();
      } else {
        reportName = "All";
        // group by date
        for (Sale s : this.sales) {
          for (Transaction t : s.transactions) {
            reportRows.add(new ReportRow(s.customer, t));
          }
        }
        reportRows.sort(Comparator.comparing(r -> r.transaction.time));
      }

      String path = "Report-" + reportName + ".csv";

      // Filter report rows by current month
      ArrayList<ReportRow> filteredRows = new ArrayList<>();
      for (ReportRow r: reportRows) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(r.transaction.time);
        int transactionMonth = cal.get(Calendar.MONTH);
        if (transactionMonth == month) {
          filteredRows.add(r);
        }
      }

      String headerRow = String.join(",", new String[]{
        "Customer", "Product", "Price", "Quantity", "Total", "Time"
      });
      StringBuilder csvString = new StringBuilder(headerRow);
      for (ReportRow r : filteredRows) {
        csvString.append("\n").append(r.toString());
      }
      try {
        File file = new File(path);
        FileWriter writer = new FileWriter(file);
        writer.write(csvString.toString());
        writer.close();

        if (file.exists()) {
          Desktop.getDesktop().open(file);
        }
        JOptionPane.showMessageDialog(null, String.format("Successfully saved %s report to %s", groupBy, path), "Success", JOptionPane.INFORMATION_MESSAGE);
      } catch (IOException e) {
        JOptionPane.showMessageDialog(null, e.getMessage(), "Failed to save report", JOptionPane.ERROR_MESSAGE);
      }
    } finally {
      App.showPage("dashboard");
    }
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
            activeSale.customer = account.customer;

            // Reduce stock count of all products involved transaction by 1
            for (Transaction t : activeSale.transactions) {
              Product p = t.product;
              p.updateStock(p.stockQuantity - t.quantity);
              System.out.println(p.description + " = " + p.stockQuantity);
            }

            // Deduct from customer's account
            account.updateBalance(account.balance - activeSale.total);

            // Finally, store the successful sale for historical purposes
            this.addSale(activeSale);

            JOptionPane.showMessageDialog(null, "Success! Transaction approved", "Checkout", JOptionPane.INFORMATION_MESSAGE);
            App.getInstance().clearSale();
            App.showPage("dashboard");

            JOptionPane.showMessageDialog(null, SalesManager.getInstance().issueReceipt(activeSale), "Receipt", JOptionPane.INFORMATION_MESSAGE);

            StateManager.getInstance().save();
          } else {
            App.showPage("dashboard");
            throw new Exception("Insufficient funds");
          }
        } else {
          throw new Exception("Invalid PIN");
        }
      } else {
        App.showPage("dashboard");
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
    StringBuilder output = new StringBuilder("\n*************************************************\n             Group76 POS Systems");
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
    output.append("\n*************************************************");
    output.append(String.format(columnFormat, "Products", "Quantity", "Amount"));
    output.append("\n-------------------------------------------------");
    for (Transaction transaction: sale.transactions){
      output.append(String.format(columnFormat, transaction.product, transaction.quantity, "N$" + transaction.amount));
    }
    output.append("\n*************************************************\nTotal Amount:                N$").append(sale.total).append("\n");
    output.append("*************************************************");
    return output.toString();
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

    SavedSale(ArrayList<SavedTransaction> transactions, double total, Date timeCompleted, Customer customer) {
      this.transactionsWithIds = transactions;
      this.total = total;
      this.timeCompleted = timeCompleted;
      this.customer = customer;
    }
  }

  /**
   * Converts sales into a format ready for saving to disk. Notably converts products into just product IDs.
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
      savedSales.add(new SavedSale(savedTransactions, s.total, s.timeCompleted, s.customer));
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
      sale.customer = s.customer;
      sales.add(sale);
    }

    this.sales = sales;
  }
}
