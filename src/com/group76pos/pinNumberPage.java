package com.group76pos;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class pinNumberPage {
    private JPanel mainPanel;
    private JPanel panelTwo;
    private JButton OKButton;
    private JTextField pinNumTextField;

    public pinNumberPage() {
        OKButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });
    }
}
