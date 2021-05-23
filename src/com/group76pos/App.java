package com.group76pos;
import javax.swing.*;

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
    private JList foodList;

    public App(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
    }

    public static void main(String[] args) {
        JFrame Frame = new App("Group76 POS");
        Frame.setVisible(true);
  }
}
