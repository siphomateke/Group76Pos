package com.group76pos;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PinNumberPage {
    public JPanel mainPanel;
    private JPanel panelTwo;
    private JButton OKButton;
    private JTextField pinNumTextField;

    public PinNumberPage() {
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pin = pinNumTextField.getText();
                SalesManager.getInstance().setAccountPin(Short.parseShort(pin));
                pinNumTextField.setText("");
            }
        });
    }
}
