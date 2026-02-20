package ui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.util.List;
import service.UserService;
import model.User;

public class AdminUserManagementPanel extends JPanel {
    private JTable userTable;
    private JButton addButton, editButton, deleteButton, refreshButton;
    
    public AdminUserManagementPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(new Color(248, 249, 250));
        
        initComponents();
        layoutComponents();
        styleComponents();
    }
    
    private void initComponents() {
        // Create table with sample data
        String[] columnNames = {"User ID", "Username", "Role", "Status", "Created Date"};
        Object[][] data = {
            {1, "admin", "ADMIN", "Active", "2024-01-15"},
            {2, "teller1", "TELLER", "Active", "2024-01-16"},
            {3, "customer1", "CUSTOMER", "Active", "2024-01-17"}
        };

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };

        userTable = new JTable(model);
        userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Create buttons with icons
        addButton = new JButton("➕ Add User");
        editButton = new JButton("✏️ Edit User");
        deleteButton = new JButton("🗑️ Delete User");
        refreshButton = new JButton("🔄 Refresh");

        // Add action listeners
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddUserDialog();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = userTable.getSelectedRow();
                if (selectedRow >= 0) {
                    showEditUserDialog(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(AdminUserManagementPanel.this,
                        "Please select a user to edit", "Info", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = userTable.getSelectedRow();
                if (selectedRow >= 0) {
                    showDeleteUserDialog(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(AdminUserManagementPanel.this,
                        "Please select a user to delete", "Info", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshUserList();
            }
        });
    }
    
    private void layoutComponents() {
        // Table with scroll pane
        JScrollPane scrollPane = new JScrollPane(userTable);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(0, 123, 255), 2),
                "User Management",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 14),
                new Color(0, 123, 255)
            ),
            new EmptyBorder(10, 10, 10, 10)
        ));
        add(scrollPane, BorderLayout.CENTER);
        
        // Button panel with better styling
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        buttonPanel.setBackground(new Color(248, 249, 250));
        buttonPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Add a welcome panel at the top
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(248, 249, 250));
        headerPanel.setBorder(new EmptyBorder(0, 0, 15, 0));
        
        JLabel titleLabel = new JLabel("User Management Dashboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(new Color(0, 123, 255));
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        
        JLabel infoLabel = new JLabel("Manage system users and their permissions", SwingConstants.CENTER);
        infoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        infoLabel.setForeground(new Color(108, 117, 125));
        headerPanel.add(infoLabel, BorderLayout.SOUTH);
        
        headerPanel.setBorder(new EmptyBorder(0, 0, 10, 0));
        add(headerPanel, BorderLayout.NORTH);
    }
    
    private void styleComponents() {
        // Style buttons with modern look
        Color primaryColor = new Color(0, 123, 255);
        Color successColor = new Color(40, 167, 69);
        Color dangerColor = new Color(220, 53, 69);
        Color secondaryColor = new Color(108, 117, 125);
        
        addButton.setText("Add User");
        addButton.setBackground(successColor);
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 255, 255, 100), 1),
            new EmptyBorder(8, 15, 8, 15)
        ));
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        editButton.setText("Edit User");
        editButton.setBackground(primaryColor);
        editButton.setForeground(Color.WHITE);
        editButton.setFocusPainted(false);
        editButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 255, 255, 100), 1),
            new EmptyBorder(8, 15, 8, 15)
        ));
        editButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        deleteButton.setText("Delete User");
        deleteButton.setBackground(dangerColor);
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);
        deleteButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 255, 255, 100), 1),
            new EmptyBorder(8, 15, 8, 15)
        ));
        deleteButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        refreshButton.setText("Refresh");
        refreshButton.setBackground(secondaryColor);
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 255, 255, 100), 1),
            new EmptyBorder(8, 15, 8, 15)
        ));
        refreshButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Style table with modern look
        userTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        userTable.setRowHeight(30);
        userTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        userTable.getTableHeader().setBackground(new Color(248, 249, 250));
        userTable.getTableHeader().setForeground(new Color(33, 37, 41));
        userTable.setGridColor(new Color(222, 226, 230));
        userTable.setShowGrid(true);
        userTable.setShowVerticalLines(false);
        userTable.setIntercellSpacing(new Dimension(0, 0));
        
        // Alternating row colors and better styling
        userTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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
    
    private void showAddUserDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Add New User", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));
        
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        formPanel.add(new JLabel("Username:"));
        JTextField usernameField = new JTextField();
        formPanel.add(usernameField);
        
        formPanel.add(new JLabel("Password:"));
        JPasswordField passwordField = new JPasswordField();
        formPanel.add(passwordField);
        
        formPanel.add(new JLabel("Role:"));
        JComboBox<String> roleCombo = new JComboBox<>(new String[]{"ADMIN", "TELLER", "CUSTOMER"});
        formPanel.add(roleCombo);
        
        JButton saveButton = new JButton("Save User");
        saveButton.setBackground(new Color(40, 167, 69));
        saveButton.setForeground(Color.WHITE);
        
        saveButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            String role = (String) roleCombo.getSelectedItem();
            
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Create user using UserService
            UserService userService = new UserService();
            String errorMessage = userService.createUser(username, password, role);
            
            if (errorMessage == null) {
                JOptionPane.showMessageDialog(dialog, "User created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                refreshUserList(); // Refresh the table
                dialog.dispose();
            } else {
                // Show detailed error message from database
                String errorText;
                if (errorMessage.contains("Duplicate entry") && errorMessage.contains("username")) {
                    errorText = "Username '" + username + "' already exists. Please choose a different username.";
                } else {
                    errorText = "Failed to create user: " + errorMessage;
                }
                JOptionPane.showMessageDialog(dialog, errorText, "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(saveButton, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
    
    private void showEditUserDialog(int rowIndex) {
        // Get the selected user data from the table
        int userId = (Integer) userTable.getValueAt(rowIndex, 0);
        String username = (String) userTable.getValueAt(rowIndex, 1);
        String role = (String) userTable.getValueAt(rowIndex, 2);
        String status = (String) userTable.getValueAt(rowIndex, 3);
        boolean isActive = "Active".equals(status);
        
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Edit User", true);
        dialog.setSize(400, 350);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));
        
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        formPanel.add(new JLabel("User ID:"));
        JTextField userIdField = new JTextField(String.valueOf(userId));
        userIdField.setEditable(false);
        formPanel.add(userIdField);
        
        formPanel.add(new JLabel("Username:"));
        JTextField usernameField = new JTextField(username);
        formPanel.add(usernameField);
        
        formPanel.add(new JLabel("Role:"));
        JComboBox<String> roleCombo = new JComboBox<>(new String[]{"ADMIN", "TELLER", "CUSTOMER"});
        roleCombo.setSelectedItem(role);
        formPanel.add(roleCombo);
        
        formPanel.add(new JLabel("Status:"));
        JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Active", "Inactive"});
        statusCombo.setSelectedItem(status);
        formPanel.add(statusCombo);
        
        JButton saveButton = new JButton("Save Changes");
        saveButton.setBackground(new Color(0, 102, 204));
        saveButton.setForeground(Color.WHITE);
        
        saveButton.addActionListener(e -> {
            String newUsername = usernameField.getText().trim();
            String newRole = (String) roleCombo.getSelectedItem();
            boolean newIsActive = "Active".equals((String) statusCombo.getSelectedItem());
            
            if (newUsername.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Username cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Update user using UserService
            UserService userService = new UserService();
            boolean success = userService.updateUser(userId, newUsername, newRole, newIsActive);
            
            if (success) {
                JOptionPane.showMessageDialog(dialog, "User updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                refreshUserList(); // Refresh the table
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Failed to update user. Username might already exist.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(saveButton, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
    
    private void showDeleteUserDialog(int rowIndex) {
        int userId = (Integer) userTable.getValueAt(rowIndex, 0);
        String username = (String) userTable.getValueAt(rowIndex, 1);
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete user '" + username + "' (ID: " + userId + ")?", 
            "Confirm Delete", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            // Delete user using UserService
            UserService userService = new UserService();
            boolean success = userService.deleteUser(userId);
            
            if (success) {
                JOptionPane.showMessageDialog(this, "User deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                refreshUserList(); // Refresh the table
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete user. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void refreshUserList() {
        UserService userService = new UserService();
        List<User> users = userService.getAllUsers();

        // Convert user list to table data
        String[] columnNames = {"User ID", "Username", "Role", "Status", "Created Date"};
        Object[][] data = new Object[users.size()][5];

        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            data[i][0] = user.getUserId();
            data[i][1] = user.getUsername();
            data[i][2] = user.getRole();
            data[i][3] = user.isActive() ? "Active" : "Inactive";
            data[i][4] = "N/A"; // Placeholder for created date
        }

        // Update the table model
        userTable.setModel(new DefaultTableModel(data, columnNames));

        // Re-apply styling
        userTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        userTable.setRowHeight(30);
        userTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        userTable.getTableHeader().setBackground(new Color(248, 249, 250));
        userTable.getTableHeader().setForeground(new Color(33, 37, 41));
        userTable.setGridColor(new Color(222, 226, 230));
        userTable.setShowGrid(true);
        userTable.setShowVerticalLines(false);
        userTable.setIntercellSpacing(new Dimension(0, 0));

        JOptionPane.showMessageDialog(this, "User list refreshed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void styleButton(JButton button, Color bgColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 255, 255, 100), 1),
            new EmptyBorder(8, 15, 8, 15)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}
