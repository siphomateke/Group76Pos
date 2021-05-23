package com.group76pos;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;

public class App extends JFrame {
    private JPanel mainPanel;

    // the food buttons
    private JButton burgersButton;
    private JButton friesButton;
    private JButton drinksButton;

    // report button should generate report somehow (maybe on a new menu)
    private JButton reportsButton;

    // cancel button should quit the program or something
    private JButton cancelButton;
    private JButton checkoutButton;

    // this should show the items that are added to the order/cart
    private JList listOrder;
    private JLabel totalAmountLabel;

    // this should be able to change the items dynamically based on the food buttons
    private JList<String> foodList;

    private Sale activeSale;

    private void populateProducts() {
        DefaultListModel foodListModel = new DefaultListModel();
        for (Product product: StockManager.getInstance().products) {
            foodListModel.addElement(product);
        }
        this.foodList.setModel(foodListModel);
    }

    private void populateCart() {
        if (activeSale != null) {
            DefaultListModel listOrderModel = new DefaultListModel();
            for (Transaction transaction : activeSale.transactions) {
                listOrderModel.addElement(transaction);
            }
            listOrder.setModel(listOrderModel);
        }

        // FIXME: Remove test receipt printing
        System.out.println(SalesManager.getInstance().issueReceipt(activeSale));
    }

    private void addToCart(Product product) {
        Transaction existingTransaction = null;
        for (Transaction t: this.activeSale.transactions) {
            if (t.product.id == product.id) {
                existingTransaction = t;
                break;
            }
        }
        if (existingTransaction == null) {
            Transaction transaction = new Transaction(product, new Date(), 0, 0);
            this.activeSale.addTransaction(transaction);
        } else {
            // If the product is already in the cart, just increment the quantity
            existingTransaction.quantity++;
        }
        this.populateCart();
    }

    public App(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();

        // FIXME: Load from disk before doing anything

        activeSale = new Sale();
        this.populateProducts();

        foodList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                JList list = (JList) evt.getSource();
                Product selectedProduct = (Product) list.getSelectedValue();
                System.out.println(selectedProduct);
                addToCart(selectedProduct);
            }
        });
        
        burgersButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });
        friesButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });
        drinksButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });
        cancelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });
        checkoutButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });
        reportsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });
    }

    public static void main(String[] args) {
        JFrame Frame = new App("Group76 POS");
        Frame.setVisible(true);
    }
}
