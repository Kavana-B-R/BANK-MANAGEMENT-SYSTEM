package ui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TransactionPanel extends JPanel {
    private JComboBox<String> transactionTypeCombo;
    private JTextField accountNumberField;
    private JTextField amountField;
    private JTextField descriptionField;
    private JButton executeButton;
    private JButton clearButton;
    
    public TransactionPanel() {
        setBackground(new Color(248, 249, 250));
        initComponents();
        layoutComponents();
        styleComponents();
    }
    
    private void initComponents() {
        transactionTypeCombo = new JComboBox<>(new String[]{"DEPOSIT", "WITHDRAWAL"});
        accountNumberField = new JTextField(15);
        amountField = new JTextField(10);
        descriptionField = new JTextField(20);
        
        executeButton = new JButton("Execute Transaction");
        clearButton = new JButton("Clear");
        
        executeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executeTransaction();
            }
        });
        
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });
    }
    
    private void layoutComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(248, 249, 250));
        headerPanel.setBorder(new EmptyBorder(0, 0, 20, 0));
        
        JLabel titleLabel = new JLabel("💰 Transaction Management", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(new Color(0, 123, 255));
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        
        JLabel subtitleLabel = new JLabel("Process deposits and withdrawals", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(108, 117, 125));
        headerPanel.add(subtitleLabel, BorderLayout.SOUTH);
        
        add(headerPanel, BorderLayout.NORTH);
        
        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(222, 226, 230), 1),
            new EmptyBorder(20, 20, 20, 20)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Title inside form
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        JLabel formTitleLabel = new JLabel("Transaction Details", SwingConstants.CENTER);
        formTitleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        formTitleLabel.setForeground(new Color(33, 37, 41));
        formPanel.add(formTitleLabel, gbc);
        
        // Transaction Type
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel typeLabel = new JLabel("Transaction Type:");
        typeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        formPanel.add(typeLabel, gbc);
        
        gbc.gridx = 1; gbc.gridy = 1;
        transactionTypeCombo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        formPanel.add(transactionTypeCombo, gbc);
        
        // Account Number
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel accountLabel = new JLabel("Account Number:");
        accountLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        formPanel.add(accountLabel, gbc);
        
        gbc.gridx = 1; gbc.gridy = 2;
        accountNumberField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        formPanel.add(accountNumberField, gbc);
        
        // Amount
        gbc.gridx = 0; gbc.gridy = 3;
        JLabel amountLabel = new JLabel("Amount (₹):");
        amountLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        formPanel.add(amountLabel, gbc);
        
        gbc.gridx = 1; gbc.gridy = 3;
        amountField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        formPanel.add(amountField, gbc);
        
        // Description
        gbc.gridx = 0; gbc.gridy = 4;
        JLabel descLabel = new JLabel("Description:");
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        formPanel.add(descLabel, gbc);
        
        gbc.gridx = 1; gbc.gridy = 4;
        descriptionField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        formPanel.add(descriptionField, gbc);
        
        // Buttons panel
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 10, 10);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(executeButton);
        buttonPanel.add(clearButton);
        formPanel.add(buttonPanel, gbc);
        
        add(formPanel, BorderLayout.CENTER);
    }
    
    private void styleComponents() {
        // Style buttons with modern look
        Color successColor = new Color(40, 167, 69);
        Color secondaryColor = new Color(108, 117, 125);
        
        executeButton.setText("Execute Transaction");
        executeButton.setBackground(successColor);
        executeButton.setForeground(Color.WHITE);
        executeButton.setFocusPainted(false);
        executeButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 255, 255, 100), 1),
            new EmptyBorder(10, 20, 10, 20)
        ));
        executeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        executeButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        clearButton.setText("Clear");
        clearButton.setBackground(secondaryColor);
        clearButton.setForeground(Color.WHITE);
        clearButton.setFocusPainted(false);
        clearButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 255, 255, 100), 1),
            new EmptyBorder(10, 20, 10, 20)
        ));
        clearButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        clearButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        // Style form fields
        transactionTypeCombo.setBackground(Color.WHITE);
        accountNumberField.setBackground(Color.WHITE);
        amountField.setBackground(Color.WHITE);
        descriptionField.setBackground(Color.WHITE);
        
        // Add borders to text fields
        accountNumberField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(206, 212, 218), 1),
            new EmptyBorder(5, 10, 5, 10)
        ));
        amountField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(206, 212, 218), 1),
            new EmptyBorder(5, 10, 5, 10)
        ));
        descriptionField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(206, 212, 218), 1),
            new EmptyBorder(5, 10, 5, 10)
        ));
    }
    
    private void executeTransaction() {
        String type = (String) transactionTypeCombo.getSelectedItem();
        String accountNumber = accountNumberField.getText();
        String amountText = amountField.getText();
        String description = descriptionField.getText();

        if (accountNumber.isEmpty() || amountText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all required fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double amount = Double.parseDouble(amountText);
            if (amount <= 0) {
                JOptionPane.showMessageDialog(this, "Amount must be positive", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Process the transaction using TransactionService
            service.TransactionService transactionService = new service.TransactionService();
            String result = transactionService.processTransaction(accountNumber, type, java.math.BigDecimal.valueOf(amount), description);

            if (result == null) {
                // Success
                String message = type + " of ₹" + String.format("%.2f", amount) + " for account " + accountNumber + " processed successfully!";
                JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
            } else {
                // Error
                JOptionPane.showMessageDialog(this, "Transaction failed: " + result, "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid amount", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void clearForm() {
        accountNumberField.setText("");
        amountField.setText("");
        descriptionField.setText("");
        transactionTypeCombo.setSelectedIndex(0);
    }

    private void styleButton(JButton button, Color bgColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 255, 255, 100), 1),
            new EmptyBorder(10, 20, 10, 20)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
    }
}
