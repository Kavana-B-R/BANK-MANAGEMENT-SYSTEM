package ui;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.Timer;

public class AdminAccountOverviewPanel extends JPanel {
    private JTable accountTable;
    private JButton refreshButton;
    private JButton generateReportButton;
    private Timer autoRefreshTimer;
    
    public AdminAccountOverviewPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(new Color(248, 249, 250));

        initComponents();
        layoutComponents();
        styleComponents();

        // Setup auto-refresh every 30 seconds
        autoRefreshTimer = new Timer(30000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshAccountList(false); // Silent refresh for auto-refresh
            }
        });
        autoRefreshTimer.start();

        // Initial load
        refreshAccountList(false);
    }
    
    private void initComponents() {
        String[] columnNames = {"Account ID", "Customer ID", "Account Number", "Type", "Balance", "Status", "Created Date"};
        Object[][] data = {
            {1001, 1, "ACC1001", "SAVINGS", "₹1500.00", "ACTIVE", "2024-01-15"},
            {1002, 2, "ACC1002", "CURRENT", "₹2500.00", "ACTIVE", "2024-01-16"},
            {1003, 3, "ACC1003", "FIXED_DEPOSIT", "₹5000.00", "ACTIVE", "2024-01-17"}
        };
        
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };
        
        accountTable = new JTable(model);
        accountTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshAccountList(true); // Show message for manual refresh
            }
        });

        generateReportButton = new JButton("Generate Report");
        generateReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateReport();
            }
        });
    }
    
    private void layoutComponents() {
        JScrollPane scrollPane = new JScrollPane(accountTable);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(0, 123, 255), 2),
                "Account Overview",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 14),
                new Color(0, 123, 255)
            ),
            new EmptyBorder(10, 10, 10, 10)
        ));
        add(scrollPane, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        buttonPanel.setBackground(new Color(248, 249, 250));
        buttonPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
        buttonPanel.add(refreshButton);
        buttonPanel.add(generateReportButton);
        add(buttonPanel, BorderLayout.SOUTH);
        
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(248, 249, 250));
        headerPanel.setBorder(new EmptyBorder(0, 0, 15, 0));
        
        JLabel titleLabel = new JLabel("📊 Account Overview Dashboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(new Color(0, 123, 255));
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        
        JLabel infoLabel = new JLabel("View all bank accounts and their status", SwingConstants.CENTER);
        infoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        infoLabel.setForeground(new Color(108, 117, 125));
        headerPanel.add(infoLabel, BorderLayout.SOUTH);
        
        add(headerPanel, BorderLayout.NORTH);
    }
    
    private void styleComponents() {
        // Style buttons with modern look
        Color primaryColor = new Color(0, 123, 255);
        Color successColor = new Color(40, 167, 69);
        
        refreshButton.setText("🔄 Refresh");
        refreshButton.setBackground(primaryColor);
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 255, 255, 100), 1),
            new EmptyBorder(8, 15, 8, 15)
        ));
        refreshButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        generateReportButton.setText("📄 Generate Report");
        generateReportButton.setBackground(successColor);
        generateReportButton.setForeground(Color.WHITE);
        generateReportButton.setFocusPainted(false);
        generateReportButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 255, 255, 100), 1),
            new EmptyBorder(8, 15, 8, 15)
        ));
        generateReportButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Style table with modern look
        accountTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        accountTable.setRowHeight(30);
        accountTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        accountTable.getTableHeader().setBackground(new Color(248, 249, 250));
        accountTable.getTableHeader().setForeground(new Color(33, 37, 41));
        accountTable.setGridColor(new Color(222, 226, 230));
        accountTable.setShowGrid(true);
        accountTable.setShowVerticalLines(false);
        accountTable.setIntercellSpacing(new Dimension(0, 0));
        
        // Alternating row colors and better styling
        accountTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(248, 249, 250));
                    c.setForeground(new Color(33, 37, 41));
                } else {
                    c.setBackground(new Color(0, 123, 255, 150));
                    c.setForeground(Color.WHITE);
                }
                setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
                return c;
            }
        });
    }
    
    private void refreshAccountList(boolean showMessage) {
        try {
            service.AccountService accountService = new service.AccountService();
            java.util.List<model.Account> accounts = accountService.getAllAccounts();

            DefaultTableModel model = (DefaultTableModel) accountTable.getModel();
            model.setRowCount(0); // Clear existing rows

            for (model.Account account : accounts) {
                model.addRow(new Object[]{
                    account.getAccountId(),
                    account.getCustomerId(),
                    account.getAccountNumber(),
                    account.getAccountType(),
                    "₹" + account.getBalance(),
                    account.getStatus(),
                    "N/A" // Created date not implemented yet
                });
            }

            if (showMessage) {
                JOptionPane.showMessageDialog(this, "Account list refreshed! Found " + accounts.size() + " accounts.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            if (showMessage) {
                JOptionPane.showMessageDialog(this, "Error refreshing accounts: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void generateReport() {
        DefaultTableModel model = (DefaultTableModel) accountTable.getModel();
        int rowCount = model.getRowCount();
        int colCount = model.getColumnCount();

        StringBuilder report = new StringBuilder();
        // Add header
        for (int col = 0; col < colCount; col++) {
            report.append(model.getColumnName(col));
            if (col < colCount - 1) report.append(", ");
        }
        report.append("\n");

        // Add rows
        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < colCount; col++) {
                report.append(model.getValueAt(row, col));
                if (col < colCount - 1) report.append(", ");
            }
            report.append("\n");
        }

        // Save to file
        try (FileWriter writer = new FileWriter("account_report.csv")) {
            writer.write(report.toString());
            JOptionPane.showMessageDialog(this, "Report generated successfully: account_report.csv", "Report Generated", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to generate report: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
