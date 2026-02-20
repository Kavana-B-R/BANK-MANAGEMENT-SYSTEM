package service;

import model.User;
import util.DatabaseUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    
    public boolean authenticate(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password_hash = ? AND is_active = TRUE";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            pstmt.setString(2, password); // In real application, use password hashing
            
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); // If user exists with matching credentials
            }
            
        } catch (SQLException e) {
            System.err.println("Authentication error: " + e.getMessage());
            return false;
        }
    }
    
    public String getUserRole(String username) {
        String sql = "SELECT role FROM users WHERE username = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("role");
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting user role: " + e.getMessage());
        }
        
        return null;
    }
    
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users ORDER BY user_id";
        
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPasswordHash(rs.getString("password_hash"));
                user.setRole(rs.getString("role"));
                user.setActive(rs.getBoolean("is_active"));
                // user.setCreatedAt(rs.getTimestamp("created_at")); // Removed for now
                users.add(user);
            }
            
        } catch (SQLException e) {
            System.err.println("Error fetching users: " + e.getMessage());
        }
        
        return users;
    }
    
public String createUser(String username, String password, String role) {
    String sql = "INSERT INTO users (username, password_hash, role) VALUES (?, ?, ?)";
    
    try (Connection conn = DatabaseUtil.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setString(1, username);
        pstmt.setString(2, password); // Hash password in real application
        pstmt.setString(3, role);
        
        int rowsAffected = pstmt.executeUpdate();
        if (rowsAffected > 0) {
            return null; // null means success
        } else {
            return "Failed to create user for unknown reasons.";
        }
        
    } catch (SQLException e) {
        System.err.println("Error creating user: " + e.getMessage());
        return e.getMessage();
    }
}
    
    public boolean updateUser(int userId, String username, String role, boolean isActive) {
        String sql = "UPDATE users SET username = ?, role = ?, is_active = ? WHERE user_id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            pstmt.setString(2, role);
            pstmt.setBoolean(3, isActive);
            pstmt.setInt(4, userId);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating user: " + e.getMessage());
            return false;
        }
    }
    
    public boolean deleteUser(int userId) {
        String sql = "DELETE FROM users WHERE user_id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error deleting user: " + e.getMessage());
            return false;
        }
    }
}
