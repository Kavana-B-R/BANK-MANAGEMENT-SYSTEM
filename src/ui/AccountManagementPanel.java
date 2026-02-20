package ui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import service.AccountService;
import model.Account;
import java.math.BigDecimal;
import java.sql.*;
import util.DatabaseUtil;

public class AccountManagementPanel extends JPanel {
    private JTextField customerNameField;
    private JTextField accountNumberField;
    private JComboBox<String> accountTypeCombo;
    private JTextField initialDepositField;
    private JButton createAccountButton;
    private JButton viewAccountsButton;
    private JTextArea accountsArea;
    private String username; // For customer role

    public AccountManagementPanel() {
        this(null);
    }

    public AccountManagementPanel(String username) {
        this.username = username;
        setBackground(new Color(248, 249, 250));
        initComponents();
        layoutComponents();
        styleComponents();
    }
    
    private void initComponents() {
        customerNameField = new JTextField(20);
        accountNumberField = new JTextField(20);
        accountTypeCombo = new JComboBox<>(new String[]{"SAVINGS", "CURRENT", "FIXED_DEPOSIT"});
        initialDepositField = new JTextField(10);

        createAccountButton = new JButton("Create Account");
        viewAccountsButton = new JButton("View Accounts");

        accountsArea = new JTextArea(10, 30);
        accountsArea.setEditable(false);
        accountsArea.setBorder(BorderFactory.createTitledBorder("Account Details"));

        // Pre-fill customer name if username is provided (for customer role)
        if (username != null && !username.isEmpty()) {
            customerNameField.setText(getCustomerNameFromUsername(username));
            customerNameField.setEditable(false); // Customers can't change their own name
        }

        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAccount();
            }
        });

        viewAccountsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewAccounts();
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

        JLabel titleLabel = new JLabel("Account Management", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(new Color(0, 123, 255));
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        JLabel subtitleLabel = new JLabel("Create and manage customer accounts", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(108, 117, 125));
        headerPanel.add(subtitleLabel, BorderLayout.SOUTH);

        add(headerPanel, BorderLayout.NORTH);

        // Input panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(222, 226, 230), 1),
            new EmptyBorder(20, 20, 20, 20)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title inside form
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        JLabel formTitleLabel = new JLabel("Account Creation Form", SwingConstants.CENTER);
        formTitleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        formTitleLabel.setForeground(new Color(33, 37, 41));
        inputPanel.add(formTitleLabel, gbc);

        // Customer Name
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel customerLabel = new JLabel("Customer Name:");
        customerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        inputPanel.add(customerLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        customerNameField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        inputPanel.add(customerNameField, gbc);

        // Account Number
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel accountLabel = new JLabel("Account Number:");
        accountLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        inputPanel.add(accountLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        accountNumberField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        inputPanel.add(accountNumberField, gbc);

        // Account Type
        gbc.gridx = 0; gbc.gridy = 3;
        JLabel typeLabel = new JLabel("Account Type:");
        typeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        inputPanel.add(typeLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 3;
        accountTypeCombo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        inputPanel.add(accountTypeCombo, gbc);

        // Initial Deposit
        gbc.gridx = 0; gbc.gridy = 4;
        JLabel depositLabel = new JLabel("Initial Deposit (₹):");
        depositLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        inputPanel.add(depositLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 4;
        initialDepositField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        inputPanel.add(initialDepositField, gbc);

        // Buttons panel
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 10, 10);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(createAccountButton);
        buttonPanel.add(viewAccountsButton);
        inputPanel.add(buttonPanel, gbc);

        add(inputPanel, BorderLayout.CENTER);

        // Accounts display area
        JScrollPane scrollPane = new JScrollPane(accountsArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(0, 123, 255), 2),
            "Account Details",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 14),
            new Color(0, 123, 255)
        ));
        add(scrollPane, BorderLayout.SOUTH);
    }

    private void styleComponents() {
        // Style buttons with modern look
        Color successColor = new Color(40, 167, 69);
        Color primaryColor = new Color(0, 123, 255);

        createAccountButton.setText("Create Account");
        createAccountButton.setBackground(successColor);
        createAccountButton.setForeground(Color.WHITE);
        createAccountButton.setFocusPainted(false);
        createAccountButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 255, 255, 100), 1),
            new EmptyBorder(10, 20, 10, 20)
        ));
        createAccountButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        createAccountButton.setFont(new Font("Segoe UI", Font.BOLD, 12));

        viewAccountsButton.setText("View Accounts");
        viewAccountsButton.setBackground(primaryColor);
        viewAccountsButton.setForeground(Color.WHITE);
        viewAccountsButton.setFocusPainted(false);
        viewAccountsButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 255, 255, 100), 1),
            new EmptyBorder(10, 20, 10, 20)
        ));
        viewAccountsButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        viewAccountsButton.setFont(new Font("Segoe UI", Font.BOLD, 12));

        // Style text fields
        customerNameField.setBackground(Color.WHITE);
        accountNumberField.setBackground(Color.WHITE);
        initialDepositField.setBackground(Color.WHITE);

        // Add borders to text fields
        customerNameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(206, 212, 218), 1),
            new EmptyBorder(5, 10, 5, 10)
        ));
        accountNumberField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(206, 212, 218), 1),
            new EmptyBorder(5, 10, 5, 10)
        ));
        initialDepositField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(206, 212, 218), 1),
            new EmptyBorder(5, 10, 5, 10)
        ));

        // Style text area
        accountsArea.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        accountsArea.setBackground(Color.WHITE);
        accountsArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }
    
    private void createAccount() {
        String customerName = customerNameField.getText().trim();
        String accountNumber = accountNumberField.getText().trim();
        String accountType = (String) accountTypeCombo.getSelectedItem();
        String depositText = initialDepositField.getText();

        if (customerName.isEmpty() || depositText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all required fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // For customers, account number is required
        if (username != null && !username.isEmpty() && accountNumber.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an account number", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            BigDecimal deposit = new BigDecimal(depositText);

            if (deposit.compareTo(BigDecimal.ZERO) < 0) {
                JOptionPane.showMessageDialog(this, "Deposit cannot be negative", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Find or create customer by name
            int customerId = findOrCreateCustomer(customerName);
            if (customerId == -1) {
                JOptionPane.showMessageDialog(this, "Failed to find or create customer", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Create account
            AccountService accountService = new AccountService();
            String result;

            if (!accountNumber.isEmpty()) {
                // Use custom account number
                result = accountService.createAccountWithNumber(customerId, accountNumber, accountType, deposit);
            } else {
                // Generate account number
                result = accountService.createAccount(customerId, accountType, deposit);
            }

            if (result == null) {
                JOptionPane.showMessageDialog(this, "Account created successfully for " + customerName + "!", "Success", JOptionPane.INFORMATION_MESSAGE);
                customerNameField.setText("");
                accountNumberField.setText("");
                initialDepositField.setText("");
                accountTypeCombo.setSelectedIndex(0);
                // Refresh the accounts view if customer name is still entered
                if (!customerNameField.getText().isEmpty()) {
                    viewAccounts();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Failed to create account: " + result, "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for deposit amount", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void viewAccounts() {
        AccountService accountService = new AccountService();
        String customerName = customerNameField.getText().trim();

        if (customerName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Customer Name to view accounts", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Find customer by name
        int customerId = findCustomerByName(customerName);
        if (customerId == -1) {
            accountsArea.setText("No customer found with name: " + customerName);
            return;
        }

        List<Account> accounts = accountService.getAccountsByCustomerId(customerId);

        if (accounts.isEmpty()) {
            accountsArea.setText("No accounts found for customer: " + customerName);
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Accounts for ").append(customerName).append(":\n\n");
            for (Account account : accounts) {
                sb.append("Account Number: ").append(account.getAccountNumber())
                  .append(" - ").append(account.getAccountType())
                  .append(" - ₹").append(account.getBalance()).append("\n");
            }
            accountsArea.setText(sb.toString());
        }
    }

    private boolean customerExists(int customerId) {
        String sql = "SELECT COUNT(*) FROM customers WHERE customer_id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, customerId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            System.err.println("Error checking customer existence: " + e.getMessage());
        }

        return false;
    }

    private int findCustomerByName(String customerName) {
        // Parse full name into first and last name
        String[] nameParts = customerName.trim().split("\\s+", 2);
        String firstName = nameParts[0];
        String lastName = nameParts.length > 1 ? nameParts[1] : "";

        String sql = "SELECT customer_id FROM customers WHERE first_name = ? AND last_name = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("customer_id");
                }
            }

        } catch (SQLException e) {
            System.err.println("Error finding customer by name: " + e.getMessage());
        }

        return -1; // Customer not found
    }

    private int findOrCreateCustomer(String customerName) {
        // First try to find existing customer
        int customerId = findCustomerByName(customerName);
        if (customerId != -1) {
            return customerId;
        }

        // Customer doesn't exist, create new one
        String[] nameParts = customerName.trim().split("\\s+", 2);
        String firstName = nameParts[0];
        String lastName = nameParts.length > 1 ? nameParts[1] : "";

        String sql = "INSERT INTO customers (first_name, last_name, email) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, firstName.toLowerCase() + "." + lastName.toLowerCase() + "@example.com"); // Generate email

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1); // Return the generated customer ID
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Error creating customer: " + e.getMessage());
        }

        return -1; // Failed to create customer
    }

    private String getCustomerNameFromUsername(String username) {
        String sql = "SELECT c.first_name, c.last_name FROM customers c " +
                     "JOIN users u ON c.user_id = u.user_id WHERE u.username = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String firstName = rs.getString("first_name");
                    String lastName = rs.getString("last_name");
                    return firstName + " " + lastName;
                }
            }

        } catch (SQLException e) {
            System.err.println("Error getting customer name from username: " + e.getMessage());
        }

        return ""; // Not found
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
