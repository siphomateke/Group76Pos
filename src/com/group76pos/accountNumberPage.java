package com.group76pos;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class accountNumberPage {
    private JPanel mainPanel;
    private JTextField accNumTextField;
    private JButton OKButton;
    private JPanel pannelTwo;

    public accountNumberPage() {
        OKButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });
    }
}
