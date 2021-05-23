package com.group76pos;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class reportGeneratorPage {
    private JPanel mainPanel;
    private JPanel panelTwo;
    private JRadioButton byItemsRadioButton;
    private JRadioButton byCustomersRadioButton;
    private JRadioButton byTransfersRadioButton;
    private JRadioButton byMonthRadioButton;
    private JButton generateReportButton;

    public reportGeneratorPage() {
        generateReportButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });
    }
}
