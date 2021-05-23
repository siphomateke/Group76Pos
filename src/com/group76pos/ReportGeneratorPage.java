package com.group76pos;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReportGeneratorPage {
    public JPanel mainPanel;
    private JPanel panelTwo;
    private JRadioButton byItemsRadioButton;
    private JRadioButton byCustomersRadioButton;
    private JRadioButton byTransfersRadioButton;
    private JRadioButton byMonthRadioButton;
    private JButton generateReportButton;

    public ReportGeneratorPage() {
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(byItemsRadioButton);
        buttonGroup.add(byCustomersRadioButton);
        buttonGroup.add(byTransfersRadioButton);
        buttonGroup.add(byMonthRadioButton);

        generateReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ReportGroupBy groupBy = ReportGroupBy.Transfer;
                if (byItemsRadioButton.isSelected()) {
                    groupBy = ReportGroupBy.Product;
                } else if (byCustomersRadioButton.isSelected()) {
                    groupBy = ReportGroupBy.Customer;
                } else if (byTransfersRadioButton.isSelected()) {
                    groupBy = ReportGroupBy.Transfer;
                } else if (byMonthRadioButton.isSelected()) {
                    // FIXME: Implement
                }

                // FIXME: Get current month
                int month = 0;
                SalesManager.getInstance().generateReport(month, groupBy);
            }
        });
    }
}
