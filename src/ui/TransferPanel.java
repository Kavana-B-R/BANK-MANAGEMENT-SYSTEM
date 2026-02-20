package ui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TransferPanel extends JPanel {
    private JTextField fromAccountField;
    private JTextField toAccountField;
    private JTextField amountField;
    private JTextField descriptionField;
    private JButton transferButton;
    private JButton clearButton;
    
    public TransferPanel() {
        setBackground(new Color(248, 249, 250));
        initComponents();
        layoutComponents();
        styleComponents();
    }
    
    private void initComponents() {
        fromAccountField = new JTextField(15);
        toAccountField = new JTextField(15);
        amountField = new JTextField(10);
        descriptionField = new JTextField(20);
        
        transferButton = new JButton("Transfer Funds");
        clearButton = new JButton("Clear");
        
        transferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                transferFunds();
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
        
        JLabel titleLabel = new JLabel("🔄 Fund Transfer", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(new Color(0, 123, 255));
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        
        JLabel subtitleLabel = new JLabel("Transfer funds between accounts", SwingConstants.CENTER);
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
        JLabel formTitleLabel = new JLabel("Transfer Details", SwingConstants.CENTER);
        formTitleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        formTitleLabel.setForeground(new Color(33, 37, 41));
        formPanel.add(formTitleLabel, gbc);
        
        // From Account
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel fromLabel = new JLabel("From Account:");
        fromLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        formPanel.add(fromLabel, gbc);
        
        gbc.gridx = 1; gbc.gridy = 1;
        fromAccountField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        formPanel.add(fromAccountField, gbc);
        
        // To Account
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel toLabel = new JLabel("To Account:");
        toLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        formPanel.add(toLabel, gbc);
        
        gbc.gridx = 1; gbc.gridy = 2;
        toAccountField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        formPanel.add(toAccountField, gbc);
        
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
        
        transferButton.setText("Transfer Funds");
        transferButton.setBackground(new Color(0, 123, 255));
        transferButton.setForeground(Color.WHITE);
        transferButton.setFocusPainted(false);
        transferButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 255, 255, 100), 1),
            new EmptyBorder(10, 20, 10, 20)
        ));
        transferButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        transferButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        clearButton.setText("Clear");
        clearButton.setBackground(new Color(108, 117, 125));
        clearButton.setForeground(Color.WHITE);
        clearButton.setFocusPainted(false);
        clearButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 255, 255, 100), 1),
            new EmptyBorder(10, 20, 10, 20)
        ));
        clearButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        clearButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        buttonPanel.add(transferButton);
        buttonPanel.add(clearButton);
        formPanel.add(buttonPanel, gbc);
        
        add(formPanel, BorderLayout.CENTER);
    }
    
    private void styleComponents() {
        // Style text fields background
        fromAccountField.setBackground(Color.WHITE);
        toAccountField.setBackground(Color.WHITE);
        amountField.setBackground(Color.WHITE);
        descriptionField.setBackground(Color.WHITE);
        
        // Add borders to text fields
        fromAccountField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(206, 212, 218), 1),
            new EmptyBorder(5, 10, 5, 10)
        ));
        toAccountField.setBorder(BorderFactory.createCompoundBorder(
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
    
    private void transferFunds() {
        String fromAccount = fromAccountField.getText();
        String toAccount = toAccountField.getText();
        String amountText = amountField.getText();
        String description = descriptionField.getText();
        
        if (fromAccount.isEmpty() || toAccount.isEmpty() || amountText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all required fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (fromAccount.equals(toAccount)) {
            JOptionPane.showMessageDialog(this, "Cannot transfer to the same account", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            double amount = Double.parseDouble(amountText);
            if (amount <= 0) {
                JOptionPane.showMessageDialog(this, "Amount must be positive", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // TODO: Add actual transfer logic
            String message = "Transfer of ₹" + amount + " from account " + fromAccount +
                           " to account " + toAccount + " processed successfully!";
            JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
            clearForm();
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid amount", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void clearForm() {
        fromAccountField.setText("");
        toAccountField.setText("");
        amountField.setText("");
        descriptionField.setText("");
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
