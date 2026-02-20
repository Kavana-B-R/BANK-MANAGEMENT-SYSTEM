package ui;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

import ui.AdminUserManagementPanel;
import ui.AdminAccountOverviewPanel;
import ui.TransactionPanel;
import ui.AccountManagementPanel;
import ui.TransferPanel;

public class DashboardFrame extends JFrame {
    private String userRole;
    private String username; // Add username field
    private JTabbedPane tabbedPane;

    // Store original transaction data for filtering
    private java.util.List<model.Transaction> originalTransactions = new java.util.ArrayList<>();
    private java.util.List<model.Account> customerAccounts = new java.util.ArrayList<>();

    public DashboardFrame(String role, String username) {
        this.userRole = role;
        this.username = username; // Initialize username
        setTitle("SecureBank Professional - " + role.toUpperCase() + " Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1400, 900);
        setLocationRelativeTo(null);

        // Use default Look and Feel

        initComponents();
        layoutComponents();
    }

    private void initComponents() {
        tabbedPane = new JTabbedPane();

        // Create tabs based on user role
        if (userRole.equals("ADMIN")) {
            tabbedPane.addTab("User Management", new AdminUserManagementPanel());
            tabbedPane.addTab("Account Overview", new AdminAccountOverviewPanel());
            tabbedPane.addTab("Transaction History", createTransactionHistoryPanel());
            tabbedPane.addTab("Reports", createReportsPanel());
        } else if (userRole.equals("TELLER")) {
            tabbedPane.addTab("Customer Accounts", createCustomerAccountsPanel());
            tabbedPane.addTab("Transactions", new TransactionPanel());
            tabbedPane.addTab("Account Management", new AccountManagementPanel());
        } else if (userRole.equals("CUSTOMER")) {
            tabbedPane.addTab("My Accounts", createMyAccountsPanel());
            tabbedPane.addTab("Transfer Funds", new TransferPanel());
            tabbedPane.addTab("Transaction History", createCustomerTransactionHistory());
        }
    }

    private void layoutComponents() {
        // Create a main panel with sophisticated gradient background
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Create sophisticated multi-stop gradient background
                Color[] colors = {
                    new Color(248, 249, 250),
                    new Color(241, 243, 244),
                    new Color(235, 237, 240),
                    new Color(229, 231, 235)
                };
                float[] fractions = {0.0f, 0.3f, 0.7f, 1.0f};

                LinearGradientPaint gradient = new LinearGradientPaint(
                    0, 0, 0, getHeight(),
                    fractions, colors
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                // Add subtle pattern overlay
                g2d.setColor(new Color(255, 255, 255, 5));
                for (int i = 0; i < getWidth(); i += 20) {
                    for (int j = 0; j < getHeight(); j += 20) {
                        if ((i + j) % 40 == 0) {
                            g2d.fillOval(i, j, 1, 1);
                        }
                    }
                }
            }
        };
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Header panel with glass morphism effect
        JPanel headerPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Beautiful glass morphism gradient header
                Color[] headerColors = {
                    new Color(13, 110, 253, 240),
                    new Color(0, 86, 179, 240),
                    new Color(0, 123, 255, 220)
                };
                float[] headerFractions = {0.0f, 0.5f, 1.0f};

                LinearGradientPaint headerGradient = new LinearGradientPaint(
                    0, 0, getWidth(), 0,
                    headerFractions, headerColors
                );
                g2d.setPaint(headerGradient);

                // Create rounded rectangle with glass effect
                RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 0, 0);
                g2d.fill(roundedRectangle);

                // Add inner glow effect
                g2d.setColor(new Color(255, 255, 255, 40));
                g2d.fillRect(0, getHeight() - 3, getWidth(), 3);

                // Add subtle shadow at bottom
                g2d.setColor(new Color(0, 0, 0, 20));
                g2d.fillRect(0, getHeight() - 1, getWidth(), 1);
            }
        };
        headerPanel.setPreferredSize(new Dimension(-1, 100));
        headerPanel.setBorder(new EmptyBorder(20, 30, 20, 30));
        headerPanel.setOpaque(false);

        // Enhanced welcome message with better typography
        JPanel welcomePanel = new JPanel(new BorderLayout());
        welcomePanel.setOpaque(false);

        JLabel welcomeLabel = new JLabel("SecureBank Professional");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        welcomeLabel.setForeground(Color.WHITE);

        JLabel roleLabel = new JLabel(userRole.toUpperCase() + " DASHBOARD");
        roleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        roleLabel.setForeground(new Color(255, 255, 255, 230));

        JLabel userLabel = new JLabel("User: " + username);
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        userLabel.setForeground(new Color(255, 255, 255, 200));

        welcomePanel.add(welcomeLabel, BorderLayout.NORTH);
        welcomePanel.add(roleLabel, BorderLayout.CENTER);
        welcomePanel.add(userLabel, BorderLayout.SOUTH);

        // Enhanced logout button with modern styling
        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setBackground(new Color(220, 53, 69, 200));
        logoutButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 255, 255, 120), 2),
            BorderFactory.createEmptyBorder(12, 25, 12, 25)
        ));
        logoutButton.setFocusPainted(false);
        logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutButton.setOpaque(false);

        // Enhanced hover effects
        logoutButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                logoutButton.setBackground(new Color(200, 35, 51, 220));
                logoutButton.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(255, 255, 255, 150), 2),
                    BorderFactory.createEmptyBorder(12, 25, 12, 25)
                ));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                logoutButton.setBackground(new Color(220, 53, 69, 200));
                logoutButton.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(255, 255, 255, 120), 2),
                    BorderFactory.createEmptyBorder(12, 25, 12, 25)
                ));
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                logoutButton.setBackground(new Color(180, 25, 41, 240));
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                logoutButton.setBackground(new Color(200, 35, 51, 220));
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(
                    DashboardFrame.this,
                    "Are you sure you want to logout?",
                    "Confirm Logout",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
                );
                if (choice == JOptionPane.YES_OPTION) {
                    logout();
                }
            }
        });

        headerPanel.add(welcomePanel, BorderLayout.WEST);
        headerPanel.add(logoutButton, BorderLayout.EAST);

        // Enhanced tabbed pane with modern styling
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabbedPane.setBackground(new Color(255, 255, 255, 240));
        tabbedPane.setForeground(new Color(33, 37, 41));
        tabbedPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Customize tab appearance with modern colors
        UIManager.put("TabbedPane.selected", new Color(0, 123, 255));
        UIManager.put("TabbedPane.contentAreaColor", new Color(255, 255, 255));
        UIManager.put("TabbedPane.tabsOverlapBorder", true);
        UIManager.put("TabbedPane.tabAreaBackground", new Color(248, 249, 250));
        UIManager.put("TabbedPane.unselectedBackground", new Color(241, 243, 244));

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        add(mainPanel);
    }

    // Panel creation methods (stubs for now)
    private JPanel createUserManagementPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("User Management - Add/Edit/Delete Users"));
        return panel;
    }

    private JPanel createAccountOverviewPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Account Overview - View all accounts"));
        return panel;
    }

    private JPanel createTransactionHistoryPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Title
        JLabel titleLabel = new JLabel("System Transaction History", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel, BorderLayout.NORTH);

        // Table columns for admin transaction history
        String[] columnNames = {"ID", "Date", "Account", "Customer", "Type", "Amount", "Description", "Related Account"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };

        JTable transactionTable = new JTable(model);
        transactionTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        transactionTable.setRowHeight(32);
        transactionTable.setGridColor(new Color(230, 230, 230));
        transactionTable.setShowGrid(true);
        transactionTable.setIntercellSpacing(new Dimension(1, 1));
        transactionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        transactionTable.setSelectionBackground(new Color(0, 123, 255, 30));
        transactionTable.setSelectionForeground(Color.BLACK);

        // Enhanced table header styling
        transactionTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        transactionTable.getTableHeader().setBackground(new Color(248, 249, 250));
        transactionTable.getTableHeader().setForeground(new Color(33, 37, 41));
        transactionTable.getTableHeader().setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 123, 255)),
            BorderFactory.createEmptyBorder(12, 8, 12, 8)
        ));
        transactionTable.getTableHeader().setPreferredSize(new Dimension(-1, 45));

        // Set column widths for better display
        transactionTable.getColumnModel().getColumn(0).setPreferredWidth(70);   // ID
        transactionTable.getColumnModel().getColumn(1).setPreferredWidth(160);  // Date
        transactionTable.getColumnModel().getColumn(2).setPreferredWidth(130);  // Account
        transactionTable.getColumnModel().getColumn(3).setPreferredWidth(130);  // Customer
        transactionTable.getColumnModel().getColumn(4).setPreferredWidth(110);  // Type
        transactionTable.getColumnModel().getColumn(5).setPreferredWidth(110);  // Amount
        transactionTable.getColumnModel().getColumn(6).setPreferredWidth(220);  // Description
        transactionTable.getColumnModel().getColumn(7).setPreferredWidth(130);  // Related Account

        // Enhanced cell renderer for better text alignment and padding
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        centerRenderer.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(JLabel.LEFT);
        leftRenderer.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        rightRenderer.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        // Apply renderers to columns
        transactionTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer); // ID
        transactionTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer); // Date
        transactionTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer); // Account
        transactionTable.getColumnModel().getColumn(3).setCellRenderer(leftRenderer);   // Customer
        transactionTable.getColumnModel().getColumn(4).setCellRenderer(centerRenderer); // Type
        transactionTable.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);  // Amount
        transactionTable.getColumnModel().getColumn(6).setCellRenderer(leftRenderer);   // Description
        transactionTable.getColumnModel().getColumn(7).setCellRenderer(centerRenderer); // Related Account

        // Custom row renderer for alternating colors and hover effects
        transactionTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (!isSelected) {
                    if (row % 2 == 0) {
                        c.setBackground(new Color(255, 255, 255));
                    } else {
                        c.setBackground(new Color(248, 249, 250));
                    }
                }

                // Add padding to all cells
                if (c instanceof JLabel) {
                    ((JLabel) c).setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
                }

                return c;
            }
        });

        // Custom cell renderer for transaction types
        transactionTable.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (value != null) {
                    String type = value.toString();
                    switch (type) {
                        case "DEPOSIT":
                            c.setForeground(new Color(34, 197, 94)); // Green
                            setBackground(new Color(34, 197, 94, 20));
                            break;
                        case "WITHDRAWAL":
                            c.setForeground(new Color(239, 68, 68)); // Red
                            setBackground(new Color(239, 68, 68, 20));
                            break;
                        case "TRANSFER_IN":
                            c.setForeground(new Color(59, 130, 246)); // Blue
                            setBackground(new Color(59, 130, 246, 20));
                            break;
                        case "TRANSFER_OUT":
                            c.setForeground(new Color(245, 158, 11)); // Orange
                            setBackground(new Color(245, 158, 11, 20));
                            break;
                        default:
                            c.setForeground(Color.BLACK);
                            setBackground(Color.WHITE);
                    }
                }
                return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(transactionTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("All System Transaction Records"));
        panel.add(scrollPane, BorderLayout.CENTER);

        // Control panel with filters and controls
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));

        // Search field
        JLabel searchLabel = new JLabel("Search:");
        JTextField searchField = new JTextField(15);
        searchField.setToolTipText("Search by account number, customer name, or description");

        // Type filter dropdown
        JLabel typeLabel = new JLabel("Filter by Type:");
        JComboBox<String> typeFilter = new JComboBox<>();
        typeFilter.addItem("All Types");
        typeFilter.addItem("DEPOSIT");
        typeFilter.addItem("WITHDRAWAL");
        typeFilter.addItem("TRANSFER_IN");
        typeFilter.addItem("TRANSFER_OUT");

        // Date range filter (simplified)
        JLabel dateLabel = new JLabel("Sort by:");
        JComboBox<String> dateSort = new JComboBox<>();
        dateSort.addItem("Most Recent");
        dateSort.addItem("Oldest First");

        // Refresh button
        JButton refreshButton = new JButton("🔄 Refresh");
        refreshButton.setBackground(new Color(0, 102, 204));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.setFont(new Font("Arial", Font.BOLD, 12));

        // Export button
        JButton exportButton = new JButton("📊 Export");
        exportButton.setBackground(new Color(40, 167, 69));
        exportButton.setForeground(Color.WHITE);
        exportButton.setFocusPainted(false);
        exportButton.setFont(new Font("Arial", Font.BOLD, 12));

        controlPanel.add(searchLabel);
        controlPanel.add(searchField);
        controlPanel.add(typeLabel);
        controlPanel.add(typeFilter);
        controlPanel.add(dateLabel);
        controlPanel.add(dateSort);
        controlPanel.add(refreshButton);
        controlPanel.add(exportButton);

        panel.add(controlPanel, BorderLayout.NORTH);

        // Load transactions function
        Runnable loadTransactions = () -> {
            try {
                System.out.println("DEBUG: Loading all transactions for admin view");

                service.TransactionService transactionService = new service.TransactionService();
                java.util.List<model.Transaction> transactions = transactionService.getAllTransactions();

                // Sort transactions by date (most recent first by default)
                transactions.sort((t1, t2) -> t2.getTransactionDate().compareTo(t1.getTransactionDate()));

                // Clear existing rows
                model.setRowCount(0);

                if (transactions.isEmpty()) {
                    model.addRow(new Object[]{"No transactions found", "", "", "", "", "", "", ""});
                } else {
                    for (model.Transaction transaction : transactions) {
                        // Get account number for display
                        String accountNumber = getAccountNumberById(transaction.getAccountId());

                        // Get customer name for the account
                        String customerName = getCustomerNameByAccountId(transaction.getAccountId());

                        // Format related account
                        String relatedAccount = transaction.getRelatedAccountId() != null ?
                            getAccountNumberById(transaction.getRelatedAccountId()) : "N/A";

                        // Format amount with color indication
                        String amountStr = "₹" + String.format("%.2f", transaction.getAmount());

                        model.addRow(new Object[]{
                            transaction.getTransactionId(),
                            transaction.getTransactionDate().toString(),
                            accountNumber,
                            customerName,
                            transaction.getTransactionType(),
                            amountStr,
                            transaction.getDescription(),
                            relatedAccount
                        });
                    }

                    // Update title with count
                    scrollPane.setBorder(BorderFactory.createTitledBorder(
                        "All System Transaction Records (" + transactions.size() + " transactions)"));
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel,
                    "Error loading transactions: " + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        };

        // Refresh button action
        refreshButton.addActionListener(e -> loadTransactions.run());

        // Search functionality
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { applySearchAndFilters(model, searchField, typeFilter, dateSort); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { applySearchAndFilters(model, searchField, typeFilter, dateSort); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { applySearchAndFilters(model, searchField, typeFilter, dateSort); }
        });

        // Filter actions
        typeFilter.addActionListener(e -> applySearchAndFilters(model, searchField, typeFilter, dateSort));
        dateSort.addActionListener(e -> applySearchAndFilters(model, searchField, typeFilter, dateSort));

        // Export button action
        exportButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(panel,
                "Export functionality would be implemented here.\n" +
                "This would export the current filtered results to CSV/Excel format.",
                "Export Feature",
                JOptionPane.INFORMATION_MESSAGE);
        });

        // Auto-refresh on panel creation
        SwingUtilities.invokeLater(loadTransactions::run);

        return panel;
    }

    private String getCustomerNameByAccountId(int accountId) {
        try {
            service.AccountService accountService = new service.AccountService();
            model.Account account = accountService.getAccountById(accountId);
            if (account != null) {
                return getCustomerName(account.getCustomerId());
            }
            return "Unknown";
        } catch (Exception e) {
            return "Unknown";
        }
    }

    private void applySearchAndFilters(DefaultTableModel model, JTextField searchField,
                                     JComboBox<String> typeFilter, JComboBox<String> dateSort) {
        try {
            // Load all transactions for admin view
            service.TransactionService transactionService = new service.TransactionService();
            java.util.List<model.Transaction> allTransactions = transactionService.getAllTransactions();

            // Apply search filter
            String searchText = searchField.getText().trim().toLowerCase();
            if (!searchText.isEmpty()) {
                allTransactions = allTransactions.stream()
                    .filter(transaction -> {
                        String accountNumber = getAccountNumberById(transaction.getAccountId()).toLowerCase();
                        String customerName = getCustomerNameByAccountId(transaction.getAccountId()).toLowerCase();
                        String description = transaction.getDescription().toLowerCase();
                        return accountNumber.contains(searchText) ||
                               customerName.contains(searchText) ||
                               description.contains(searchText);
                    })
                    .collect(java.util.stream.Collectors.toList());
            }

            // Apply type filter
            String selectedType = (String) typeFilter.getSelectedItem();
            if (selectedType != null && !selectedType.equals("All Types")) {
                allTransactions = allTransactions.stream()
                    .filter(transaction -> transaction.getTransactionType().equals(selectedType))
                    .collect(java.util.stream.Collectors.toList());
            }

            // Apply date sorting
            String sortOrder = (String) dateSort.getSelectedItem();
            if (sortOrder != null) {
                if (sortOrder.equals("Most Recent")) {
                    allTransactions.sort((t1, t2) -> t2.getTransactionDate().compareTo(t1.getTransactionDate()));
                } else if (sortOrder.equals("Oldest First")) {
                    allTransactions.sort((t1, t2) -> t1.getTransactionDate().compareTo(t2.getTransactionDate()));
                }
            }

            // Update the table model with filtered results
            model.setRowCount(0);

            if (allTransactions.isEmpty()) {
                model.addRow(new Object[]{"No transactions found", "", "", "", "", "", "", ""});
            } else {
                for (model.Transaction transaction : allTransactions) {
                    // Get account number for display
                    String accountNumber = getAccountNumberById(transaction.getAccountId());

                    // Get customer name for the account
                    String customerName = getCustomerNameByAccountId(transaction.getAccountId());

                    // Format related account
                    String relatedAccount = transaction.getRelatedAccountId() != null ?
                        getAccountNumberById(transaction.getRelatedAccountId()) : "N/A";

                    // Format amount with color indication
                    String amountStr = "₹" + String.format("%.2f", transaction.getAmount());

                    model.addRow(new Object[]{
                        transaction.getTransactionId(),
                        transaction.getTransactionDate().toString(),
                        accountNumber,
                        customerName,
                        transaction.getTransactionType(),
                        amountStr,
                        transaction.getDescription(),
                        relatedAccount
                    });
                }
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                "Error applying filters: " + ex.getMessage(),
                "Filter Error",
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private JPanel createReportsPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Reports - Generate system reports"));
        return panel;
    }

    private JPanel createCustomerAccountsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Title
        JLabel titleLabel = new JLabel("Customer Accounts Overview", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titleLabel, BorderLayout.NORTH);

        // Table columns for teller customer dashboard
        String[] columnNames = {"Customer ID", "Account Number", "Account Type", "Balance", "Status", "Customer Name"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };

        JTable accountsTable = new JTable(model);
        accountsTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        accountsTable.setRowHeight(32);
        accountsTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        accountsTable.getTableHeader().setBackground(new Color(248, 249, 250));
        accountsTable.getTableHeader().setForeground(new Color(33, 37, 41));
        accountsTable.getTableHeader().setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 123, 255)),
            BorderFactory.createEmptyBorder(12, 8, 12, 8)
        ));
        accountsTable.getTableHeader().setPreferredSize(new Dimension(-1, 45));
        accountsTable.setGridColor(new Color(230, 230, 230));
        accountsTable.setShowGrid(true);
        accountsTable.setIntercellSpacing(new Dimension(1, 1));
        accountsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        accountsTable.setSelectionBackground(new Color(0, 123, 255, 30));
        accountsTable.setSelectionForeground(Color.BLACK);

        // Set column widths for better display
        accountsTable.getColumnModel().getColumn(0).setPreferredWidth(80);  // Customer ID
        accountsTable.getColumnModel().getColumn(1).setPreferredWidth(120); // Account Number
        accountsTable.getColumnModel().getColumn(2).setPreferredWidth(100); // Account Type
        accountsTable.getColumnModel().getColumn(3).setPreferredWidth(100); // Balance
        accountsTable.getColumnModel().getColumn(4).setPreferredWidth(80);  // Status
        accountsTable.getColumnModel().getColumn(5).setPreferredWidth(150); // Customer Name

        JScrollPane scrollPane = new JScrollPane(accountsTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(0, 123, 255), 2),
            "All Customer Accounts",
            javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
            javax.swing.border.TitledBorder.DEFAULT_POSITION,
            new Font("Segoe UI", Font.BOLD, 14),
            new Color(0, 123, 255)
        ));
        panel.add(scrollPane, BorderLayout.CENTER);

        // Refresh button
        JButton refreshButton = new JButton("Refresh Accounts");
        refreshButton.setBackground(new Color(0, 102, 204));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        refreshButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));

        refreshButton.addActionListener(e -> {
            try {
                service.AccountService accountService = new service.AccountService();
                java.util.List<model.Account> accounts = accountService.getAllAccounts();

                // Clear existing rows
                model.setRowCount(0);

                if (accounts.isEmpty()) {
                    model.addRow(new Object[]{"No accounts found", "", "", "", "", ""});
                } else {
                    for (model.Account account : accounts) {
                        // Get customer name (simplified - in real app you'd join tables)
                        String customerName = getCustomerName(account.getCustomerId());

                        model.addRow(new Object[]{
                            account.getCustomerId(),
                            account.getAccountNumber(),
                            account.getAccountType(),
                            "₹" + String.format("%.2f", account.getBalance()),
                            account.getStatus(),
                            customerName
                        });
                    }

                    // Update title with count
                    scrollPane.setBorder(BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(0, 123, 255), 2),
                        "All Customer Accounts (" + accounts.size() + " accounts)",
                        javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                        javax.swing.border.TitledBorder.DEFAULT_POSITION,
                        new Font("Segoe UI", Font.BOLD, 14),
                        new Color(0, 123, 255)
                    ));
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel,
                    "Error loading accounts: " + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        // Auto-refresh on panel creation
        refreshButton.doClick();

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(refreshButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private String getCustomerName(int customerId) {
        // Simple mapping for demo - in real app, you'd query the database
        switch (customerId) {
            case 1: return "John Doe";
            case 2: return "Jane Smith";
            case 3: return "Bob Johnson";
            default: return "Customer " + customerId;
        }
    }

    private JPanel createTransactionsPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Transactions - Process deposits/withdrawals"));
        return panel;
    }

    private JPanel createAccountManagementPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Account Management - Open/Close accounts"));
        return panel;
    }

    private JPanel createMyAccountsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Title
        JLabel titleLabel = new JLabel("My Accounts", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        panel.add(titleLabel, BorderLayout.NORTH);

        // Table columns for customer accounts
        String[] columnNames = {"Account Number", "Account Type", "Balance", "Status"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };

        JTable accountsTable = new JTable(model);
        accountsTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        accountsTable.setRowHeight(32);
        accountsTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        accountsTable.getTableHeader().setBackground(new Color(248, 249, 250));
        accountsTable.getTableHeader().setForeground(new Color(33, 37, 41));
        accountsTable.getTableHeader().setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 123, 255)),
            BorderFactory.createEmptyBorder(12, 8, 12, 8)
        ));
        accountsTable.getTableHeader().setPreferredSize(new Dimension(-1, 45));
        accountsTable.setGridColor(new Color(230, 230, 230));
        accountsTable.setShowGrid(true);
        accountsTable.setIntercellSpacing(new Dimension(1, 1));
        accountsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        accountsTable.setSelectionBackground(new Color(0, 123, 255, 30));
        accountsTable.setSelectionForeground(Color.BLACK);

        // Set column widths for better display
        accountsTable.getColumnModel().getColumn(0).setPreferredWidth(150);  // Account Number
        accountsTable.getColumnModel().getColumn(1).setPreferredWidth(150);  // Account Type
        accountsTable.getColumnModel().getColumn(2).setPreferredWidth(100);  // Balance
        accountsTable.getColumnModel().getColumn(3).setPreferredWidth(100);  // Status

        JScrollPane scrollPane = new JScrollPane(accountsTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(0, 123, 255), 2),
            "Your Accounts",
            javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
            javax.swing.border.TitledBorder.DEFAULT_POSITION,
            new Font("Segoe UI", Font.BOLD, 14),
            new Color(0, 123, 255)
        ));
        panel.add(scrollPane, BorderLayout.CENTER);

        // Refresh button
        JButton refreshButton = new JButton("🔄 Refresh Accounts");
        refreshButton.setBackground(new Color(0, 102, 204));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        refreshButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));

        refreshButton.addActionListener(e -> {
            try {
                System.out.println("DEBUG: Refreshing accounts for username: " + username);
                service.AccountService accountService = new service.AccountService();
                java.util.List<model.Account> accounts = accountService.getAccountsByCustomerUsername(username);
                System.out.println("DEBUG: Found " + accounts.size() + " accounts for username: " + username);

                // Clear existing rows
                model.setRowCount(0);

                if (accounts.isEmpty()) {
                    model.addRow(new Object[]{"No accounts found", "", "", ""});
                } else {
                    for (model.Account account : accounts) {
                        model.addRow(new Object[]{
                            account.getAccountNumber(),
                            account.getAccountType(),
                            "₹" + String.format("%.2f", account.getBalance()),
                            account.getStatus()
                        });
                    }

                    // Update title with count
                    scrollPane.setBorder(BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(0, 123, 255), 2),
                        "Your Accounts (" + accounts.size() + " accounts)",
                        javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                        javax.swing.border.TitledBorder.DEFAULT_POSITION,
                        new Font("Segoe UI", Font.BOLD, 14),
                        new Color(0, 123, 255)
                    ));
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel,
                    "Error loading accounts: " + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        // Auto-refresh on panel creation
        refreshButton.doClick();

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(refreshButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createTransferPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Transfer Funds - Transfer between accounts"));
        return panel;
    }

    private JPanel createCustomerTransactionHistory() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Title
        JLabel titleLabel = new JLabel("My Transaction History", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(new Color(0, 123, 255));
        panel.add(titleLabel, BorderLayout.NORTH);

        // Table columns for customer transaction history
        String[] columnNames = {"Date", "Account", "Type", "Amount", "Description", "Related Account"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };

        JTable transactionTable = new JTable(model);
        transactionTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        transactionTable.setRowHeight(32);
        transactionTable.setGridColor(new Color(230, 230, 230));
        transactionTable.setShowGrid(true);
        transactionTable.setIntercellSpacing(new Dimension(1, 1));
        transactionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        transactionTable.setSelectionBackground(new Color(0, 123, 255, 30));
        transactionTable.setSelectionForeground(Color.BLACK);

        // Enhanced table header styling
        transactionTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        transactionTable.getTableHeader().setBackground(new Color(248, 249, 250));
        transactionTable.getTableHeader().setForeground(new Color(33, 37, 41));
        transactionTable.getTableHeader().setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 123, 255)),
            BorderFactory.createEmptyBorder(12, 8, 12, 8)
        ));
        transactionTable.getTableHeader().setPreferredSize(new Dimension(-1, 45));

        // Set column widths for better display
        transactionTable.getColumnModel().getColumn(0).setPreferredWidth(160);  // Date
        transactionTable.getColumnModel().getColumn(1).setPreferredWidth(130);  // Account
        transactionTable.getColumnModel().getColumn(2).setPreferredWidth(110);  // Type
        transactionTable.getColumnModel().getColumn(3).setPreferredWidth(110);  // Amount
        transactionTable.getColumnModel().getColumn(4).setPreferredWidth(220);  // Description
        transactionTable.getColumnModel().getColumn(5).setPreferredWidth(130);  // Related Account

        // Enhanced cell renderer for better text alignment and padding
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        centerRenderer.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(JLabel.LEFT);
        leftRenderer.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        rightRenderer.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        // Apply renderers to columns
        transactionTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer); // Date
        transactionTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer); // Account
        transactionTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer); // Type
        transactionTable.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);  // Amount
        transactionTable.getColumnModel().getColumn(4).setCellRenderer(leftRenderer);   // Description
        transactionTable.getColumnModel().getColumn(5).setCellRenderer(centerRenderer); // Related Account

        // Custom row renderer for alternating colors and hover effects
        transactionTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (!isSelected) {
                    if (row % 2 == 0) {
                        c.setBackground(new Color(255, 255, 255));
                    } else {
                        c.setBackground(new Color(248, 249, 250));
                    }
                }

                // Add padding to all cells
                if (c instanceof JLabel) {
                    ((JLabel) c).setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
                }

                return c;
            }
        });

        // Custom cell renderer for transaction types with background colors
        transactionTable.getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (value != null) {
                    String type = value.toString();
                    switch (type) {
                        case "DEPOSIT":
                            c.setForeground(new Color(34, 197, 94)); // Green
                            setBackground(new Color(34, 197, 94, 20));
                            break;
                        case "WITHDRAWAL":
                            c.setForeground(new Color(239, 68, 68)); // Red
                            setBackground(new Color(239, 68, 68, 20));
                            break;
                        case "TRANSFER_IN":
                            c.setForeground(new Color(59, 130, 246)); // Blue
                            setBackground(new Color(59, 130, 246, 20));
                            break;
                        case "TRANSFER_OUT":
                            c.setForeground(new Color(245, 158, 11)); // Orange
                            setBackground(new Color(245, 158, 11, 20));
                            break;
                        default:
                            c.setForeground(Color.BLACK);
                            setBackground(Color.WHITE);
                    }
                }
                return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(transactionTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(0, 123, 255), 2),
            "Your Transaction Records",
            javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
            javax.swing.border.TitledBorder.DEFAULT_POSITION,
            new Font("Segoe UI", Font.BOLD, 14),
            new Color(0, 123, 255)
        ));
        panel.add(scrollPane, BorderLayout.CENTER);

        // Control panel with filters and controls
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));

        // Search field
        JLabel searchLabel = new JLabel("Search:");
        JTextField searchField = new JTextField(15);
        searchField.setToolTipText("Search by account number or description");

        // Account filter dropdown
        JLabel accountLabel = new JLabel("Filter by Account:");
        JComboBox<String> accountFilter = new JComboBox<>();
        accountFilter.addItem("All Accounts");

        // Type filter dropdown
        JLabel typeLabel = new JLabel("Filter by Type:");
        JComboBox<String> typeFilter = new JComboBox<>();
        typeFilter.addItem("All Types");
        typeFilter.addItem("DEPOSIT");
        typeFilter.addItem("WITHDRAWAL");
        typeFilter.addItem("TRANSFER_IN");
        typeFilter.addItem("TRANSFER_OUT");

        // Sort by date
        JLabel dateLabel = new JLabel("Sort by:");
        JComboBox<String> dateSort = new JComboBox<>();
        dateSort.addItem("Most Recent");
        dateSort.addItem("Oldest First");

        // Refresh button
        JButton refreshButton = new JButton("🔄 Refresh");
        refreshButton.setBackground(new Color(0, 102, 204));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        refreshButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));

        // Export button
        JButton exportButton = new JButton("📊 Export");
        exportButton.setBackground(new Color(40, 167, 69));
        exportButton.setForeground(Color.WHITE);
        exportButton.setFocusPainted(false);
        exportButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        exportButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));

        controlPanel.add(searchLabel);
        controlPanel.add(searchField);
        controlPanel.add(accountLabel);
        controlPanel.add(accountFilter);
        controlPanel.add(typeLabel);
        controlPanel.add(typeFilter);
        controlPanel.add(dateLabel);
        controlPanel.add(dateSort);
        controlPanel.add(refreshButton);
        controlPanel.add(exportButton);

        panel.add(controlPanel, BorderLayout.NORTH);

        // Load transactions function
        Runnable loadTransactions = () -> {
            try {
                System.out.println("DEBUG: Loading transactions for username: " + username);

                // Get customer's accounts
                service.AccountService accountService = new service.AccountService();
                java.util.List<model.Account> accounts = accountService.getAccountsByCustomerUsername(username);

                // Populate account filter
                accountFilter.removeAllItems();
                accountFilter.addItem("All Accounts");
                for (model.Account account : accounts) {
                    accountFilter.addItem(account.getAccountNumber() + " (" + account.getAccountType() + ")");
                }

                // Store customer accounts for filtering
                customerAccounts.clear();
                customerAccounts.addAll(accounts);

                // Get transactions for all accounts
                service.TransactionService transactionService = new service.TransactionService();
                java.util.List<model.Transaction> allTransactions = new java.util.ArrayList<>();

                for (model.Account account : accounts) {
                    java.util.List<model.Transaction> accountTransactions =
                        transactionService.getTransactionsByAccountId(account.getAccountId());
                    allTransactions.addAll(accountTransactions);
                }

                // Store original transactions for filtering
                originalTransactions.clear();
                originalTransactions.addAll(allTransactions);

                // Sort transactions by date (most recent first)
                allTransactions.sort((t1, t2) -> t2.getTransactionDate().compareTo(t1.getTransactionDate()));

                // Clear existing rows
                model.setRowCount(0);

                if (allTransactions.isEmpty()) {
                    model.addRow(new Object[]{"No transactions found", "", "", "", "", ""});
                } else {
                    for (model.Transaction transaction : allTransactions) {
                        // Get account number for display
                        String accountNumber = getAccountNumberById(transaction.getAccountId());

                        // Format related account
                        String relatedAccount = transaction.getRelatedAccountId() != null ?
                            getAccountNumberById(transaction.getRelatedAccountId()) : "N/A";

                        // Format amount with color indication
                        String amountStr = "₹" + String.format("%.2f", transaction.getAmount());

                        model.addRow(new Object[]{
                            transaction.getTransactionDate().toString(),
                            accountNumber,
                            transaction.getTransactionType(),
                            amountStr,
                            transaction.getDescription(),
                            relatedAccount
                        });
                    }

                    // Update title with count
                    scrollPane.setBorder(BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(0, 123, 255), 2),
                        "Your Transaction Records (" + allTransactions.size() + " transactions)",
                        javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                        javax.swing.border.TitledBorder.DEFAULT_POSITION,
                        new Font("Segoe UI", Font.BOLD, 14),
                        new Color(0, 123, 255)
                    ));
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel,
                    "Error loading transactions: " + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        };

        // Refresh button action
        refreshButton.addActionListener(e -> loadTransactions.run());

        // Search functionality
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { applyCustomerFilters(model, searchField, accountFilter, typeFilter, dateSort); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { applyCustomerFilters(model, searchField, accountFilter, typeFilter, dateSort); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { applyCustomerFilters(model, searchField, accountFilter, typeFilter, dateSort); }
        });

        // Filter actions
        accountFilter.addActionListener(e -> applyCustomerFilters(model, searchField, accountFilter, typeFilter, dateSort));
        typeFilter.addActionListener(e -> applyCustomerFilters(model, searchField, accountFilter, typeFilter, dateSort));
        dateSort.addActionListener(e -> applyCustomerFilters(model, searchField, accountFilter, typeFilter, dateSort));

        // Export button action
        exportButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(panel,
                "Export functionality would be implemented here.\n" +
                "This would export your transaction history to CSV/Excel format.",
                "Export Feature",
                JOptionPane.INFORMATION_MESSAGE);
        });

        // Auto-refresh on panel creation
        SwingUtilities.invokeLater(loadTransactions::run);

        return panel;
    }

    private String getAccountNumberById(int accountId) {
        try {
            service.AccountService accountService = new service.AccountService();
            model.Account account = accountService.getAccountById(accountId);
            return account != null ? account.getAccountNumber() : "Unknown";
        } catch (Exception e) {
            return "Unknown";
        }
    }

    private void applyFilters(DefaultTableModel model, JComboBox<String> accountFilter, JComboBox<String> typeFilter) {
        // This would require storing the original data and filtering it
        // For now, just refresh the data
        // In a full implementation, you'd filter the existing data without re-querying
    }

    private void applyCustomerFilters(DefaultTableModel model, JTextField searchField,
                                     JComboBox<String> accountFilter, JComboBox<String> typeFilter,
                                     JComboBox<String> dateSort) {
        try {
            // Start with all original transactions
            java.util.List<model.Transaction> filteredTransactions = new java.util.ArrayList<>(originalTransactions);

            // Apply search filter
            String searchText = searchField.getText().trim().toLowerCase();
            if (!searchText.isEmpty()) {
                filteredTransactions = filteredTransactions.stream()
                    .filter(transaction -> {
                        String accountNumber = getAccountNumberById(transaction.getAccountId()).toLowerCase();
                        String description = transaction.getDescription().toLowerCase();
                        return accountNumber.contains(searchText) || description.contains(searchText);
                    })
                    .collect(java.util.stream.Collectors.toList());
            }

            // Apply account filter
            String selectedAccount = (String) accountFilter.getSelectedItem();
            if (selectedAccount != null && !selectedAccount.equals("All Accounts")) {
                // Extract account number from the selected item (format: "ACC123 (SAVINGS)")
                String accountNumber = selectedAccount.split(" ")[0];
                filteredTransactions = filteredTransactions.stream()
                    .filter(transaction -> {
                        String transactionAccountNumber = getAccountNumberById(transaction.getAccountId());
                        return transactionAccountNumber.equals(accountNumber);
                    })
                    .collect(java.util.stream.Collectors.toList());
            }

            // Apply type filter
            String selectedType = (String) typeFilter.getSelectedItem();
            if (selectedType != null && !selectedType.equals("All Types")) {
                filteredTransactions = filteredTransactions.stream()
                    .filter(transaction -> transaction.getTransactionType().equals(selectedType))
                    .collect(java.util.stream.Collectors.toList());
            }

            // Apply date sorting
            String sortOrder = (String) dateSort.getSelectedItem();
            if (sortOrder != null) {
                if (sortOrder.equals("Most Recent")) {
                    filteredTransactions.sort((t1, t2) -> t2.getTransactionDate().compareTo(t1.getTransactionDate()));
                } else if (sortOrder.equals("Oldest First")) {
                    filteredTransactions.sort((t1, t2) -> t1.getTransactionDate().compareTo(t2.getTransactionDate()));
                }
            }

            // Update the table model with filtered results
            model.setRowCount(0);

            if (filteredTransactions.isEmpty()) {
                model.addRow(new Object[]{"No transactions found", "", "", "", "", ""});
            } else {
                for (model.Transaction transaction : filteredTransactions) {
                    // Get account number for display
                    String accountNumber = getAccountNumberById(transaction.getAccountId());

                    // Format related account
                    String relatedAccount = transaction.getRelatedAccountId() != null ?
                        getAccountNumberById(transaction.getRelatedAccountId()) : "N/A";

                    // Format amount with color indication
                    String amountStr = "₹" + String.format("%.2f", transaction.getAmount());

                    model.addRow(new Object[]{
                        transaction.getTransactionDate().toString(),
                        accountNumber,
                        transaction.getTransactionType(),
                        amountStr,
                        transaction.getDescription(),
                        relatedAccount
                    });
                }
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                "Error applying filters: " + ex.getMessage(),
                "Filter Error",
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void logout() {
        this.dispose();
        LoginForm loginForm = new LoginForm();
        loginForm.setVisible(true);
    }
}
