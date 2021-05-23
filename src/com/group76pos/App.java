package com.group76pos;
import javax.swing.*;

public class App extends JFrame {
    private JPanel mainPanel;
    private JButton burgersButton;
    private JButton friesButton;
    private JButton drinksButton;
    private JButton reportsButton;
    private JButton cancelButton;
    private JButton checkoutButton;

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
