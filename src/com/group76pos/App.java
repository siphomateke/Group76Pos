package com.group76pos;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private JLabel totalAmountLabel;

    // this should be able to change the items dynamically based on the food buttons
    private JList<String> foodList;
    private JButton menuButton;
    private JScrollPane cartScrollPane;
    private JPanel cartPanel;

    private int productFilter = -1;
    private Sale activeSale;

    private void populateProducts() {
        DefaultListModel foodListModel = new DefaultListModel();
        for (Product product: StockManager.getInstance().products) {
            if (
                    this.productFilter == -1
                    || (this.productFilter == 0 && product instanceof Burger)
                    || (this.productFilter == 1 && product instanceof Fries)
                    || (this.productFilter == 2 && product instanceof Drink)
            ) {
                foodListModel.addElement(product);
            }
        }
        this.foodList.setModel(foodListModel);
    }

    private void updateTotal() {
        this.activeSale.total = this.activeSale.calculateTotal();
        this.totalAmountLabel.setText("N$" + this.activeSale.total);
    }

    private void updateCart() {
        cartPanel.removeAll();
        if (activeSale != null) {
            for (Transaction transaction : activeSale.transactions) {
                JPanel transactionRow = new JPanel();
                GridLayout layout = new GridLayout();
                layout.setHgap(3);
                transactionRow.setLayout(layout);

                transactionRow.add(new JLabel(transaction.product.description));
                JLabel label = new JLabel();
                label.setText("N$"+(transaction.amount * transaction.quantity));
                transactionRow.add(label);

                JTextField quantityField = new JTextField();
                quantityField.setText(Integer.toString(transaction.quantity));
                // Handle updating transaction quantity
                quantityField.getDocument().addDocumentListener(new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent documentEvent) {
                        handleQuantityChange();
                    }
                    @Override
                    public void removeUpdate(DocumentEvent documentEvent) {
                        handleQuantityChange();
                    }
                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        handleQuantityChange();
                    }
                    public void handleQuantityChange() {
                        try {
                            String text = quantityField.getText();
                            if (text.length() > 0) {
                                int quantity = Integer.parseInt(text);
                                System.out.println("Quantity: "+quantity);
                                if (quantity <= 0) {
                                    JOptionPane.showMessageDialog(null,
                                            "Error: Please enter number bigger than 0", "Error Message",
                                            JOptionPane.ERROR_MESSAGE);
                                } else {
                                    transaction.quantity = quantity;
                                    label.setText("N$"+(transaction.amount * transaction.quantity));
                                    updateTotal();
                                }
                            }
                        } catch (NumberFormatException e) {
                            quantityField.setText(Integer.toString(transaction.quantity));
                        }
                    }
                });
                transactionRow.add(quantityField);

                JButton removeButton = new JButton("Remove");
                removeButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                      activeSale.removeTransaction(transaction);
                      updateCart();
                    }
                });
                transactionRow.add(removeButton);

                cartPanel.add(transactionRow, BorderLayout.NORTH);
            }
        }
        cartPanel.revalidate();
        cartPanel.repaint();
        this.updateTotal();

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
            Transaction transaction = new Transaction(product, new Date(), product.sellingPrice, 1);
            this.activeSale.addTransaction(transaction);
        } else {
            // If the product is already in the cart, just increment the quantity
            existingTransaction.quantity++;
        }
        this.updateCart();
    }

    public App(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();

        // FIXME: Load from disk before doing anything

        activeSale = new Sale();
        cartPanel.setLayout(new BoxLayout(cartPanel, BoxLayout.PAGE_AXIS));
        this.populateProducts();

        foodList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                JList list = (JList) evt.getSource();
                Product selectedProduct = (Product) list.getSelectedValue();
                System.out.println(selectedProduct);
                addToCart(selectedProduct);
            }
        });
        
        burgersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                productFilter = 0;
                populateProducts();
            }
        });
        friesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                productFilter = 1;
                populateProducts();
            }
        });
        drinksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                productFilter = 2;
                populateProducts();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                activeSale.cancel();
                updateCart();
            }
        });
        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    activeSale.checkout();
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(null, exception.toString());
                }
            }
        });
        reportsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                activeSale.cancel();
            }
        });
        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                productFilter = -1;
                populateProducts();
            }
        });
    }

    public static void main(String[] args) {
        JFrame Frame = new App("Group76 POS");
        Frame.setVisible(true);
    }
}
