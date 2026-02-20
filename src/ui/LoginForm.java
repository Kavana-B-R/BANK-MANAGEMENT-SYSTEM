package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import service.UserService;

public class LoginForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    
    public LoginForm() {
        setTitle("Banking System - Secure Login");
        setSize(500, 450); // Increased height to accommodate buttons
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245, 250, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel title = new JLabel("BANK");
        title.setFont(new Font("Arial", Font.BOLD, 48));
        title.setForeground(new Color(0, 102, 204));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(title, gbc);

        JLabel subtitle = new JLabel("Banking System");
        subtitle.setFont(new Font("Arial", Font.BOLD, 24));
        subtitle.setForeground(new Color(0, 102, 204));
        gbc.gridy = 1;
        panel.add(subtitle, gbc);

        JLabel portal = new JLabel("Secure Login Portal");
        portal.setFont(new Font("Arial", Font.PLAIN, 16));
        portal.setForeground(Color.GRAY);
        gbc.gridy = 2;
        panel.add(portal, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridy = 3; gbc.gridx = 0;
        panel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        usernameField = new JTextField(15);
        panel.add(usernameField, gbc);

        gbc.gridy = 4; gbc.gridx = 0; gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        passwordField = new JPasswordField(15);
        panel.add(passwordField, gbc);

        gbc.gridy = 5; gbc.gridx = 0; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        JLabel demo = new JLabel("<html><i>Demo: admin / admin123</i></html>");
        demo.setForeground(Color.GRAY);
        panel.add(demo, gbc);

        // Button panel with smaller buttons
        gbc.gridy = 6; gbc.gridx = 0; gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        buttonPanel.setOpaque(false);
        
        // Login button - smaller size
        JButton loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(80, 30)); // Smaller button
        loginButton.setBackground(new Color(0, 102, 204));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setFont(new Font("Arial", Font.BOLD, 12));
        
        // Cancel button - smaller size
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(80, 30)); // Smaller button
        cancelButton.setBackground(new Color(108, 117, 125));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        cancelButton.setFont(new Font("Arial", Font.BOLD, 12));
        
        // Add action listeners
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
        
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usernameField.setText("");
                passwordField.setText("");
            }
        });
        
        buttonPanel.add(loginButton);
        buttonPanel.add(cancelButton);
        panel.add(buttonPanel, gbc);

        add(panel);
    }
    
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password", 
                "Login Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        UserService userService = new UserService();
        boolean authenticated = userService.authenticate(username, password);
        
        if (authenticated) {
            String role = userService.getUserRole(username);
            JOptionPane.showMessageDialog(this, "Login successful! Welcome " + role, 
                "Success", JOptionPane.INFORMATION_MESSAGE);
            
            // Open dashboard (placeholder - would need to implement DashboardFrame)
            this.dispose();
            DashboardFrame dashboard = new DashboardFrame(role, username);
            dashboard.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password", 
                "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
    }
}
