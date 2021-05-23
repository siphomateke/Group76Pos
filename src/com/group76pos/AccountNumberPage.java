package com.group76pos;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AccountNumberPage {
    public JPanel mainPanel;
    private JTextField accNumTextField;
    private JButton OKButton;
    private JPanel pannelTwo;

    public AccountNumberPage() {
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String accountNumber = accNumTextField.getText();
                SalesManager.getInstance().setAccountNumber(accountNumber);
            }
        });
    }
}
