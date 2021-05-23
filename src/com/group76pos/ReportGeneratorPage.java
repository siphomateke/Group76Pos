package com.group76pos;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

public class ReportGeneratorPage {
    public JPanel mainPanel;
    private JPanel panelTwo;
    private JRadioButton byItemsRadioButton;
    private JRadioButton byCustomersRadioButton;
    private JRadioButton byTransfersRadioButton;
    private JButton generateReportButton;

    public ReportGeneratorPage() {
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(byItemsRadioButton);
        buttonGroup.add(byCustomersRadioButton);
        buttonGroup.add(byTransfersRadioButton);

        generateReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ReportGroupBy groupBy = null;
                if (byItemsRadioButton.isSelected()) {
                    groupBy = ReportGroupBy.Product;
                } else if (byCustomersRadioButton.isSelected()) {
                    groupBy = ReportGroupBy.Customer;
                } else if (byTransfersRadioButton.isSelected()) {
                    groupBy = ReportGroupBy.Transfer;
                }

                Calendar cal = Calendar.getInstance();
                cal.setTime(new Date());
                int month = cal.get(Calendar.MONTH);
                SalesManager.getInstance().generateReport(month, groupBy);
            }
        });
    }
}
