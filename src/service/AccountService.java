package service;

import model.Account;
import util.DatabaseUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class AccountService {

    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM accounts ORDER BY account_id";

        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Account account = new Account();
                account.setAccountId(rs.getInt("account_id"));
                account.setCustomerId(rs.getInt("customer_id"));
                account.setAccountNumber(rs.getString("account_number"));
                account.setAccountType(rs.getString("account_type"));
                account.setBalance(rs.getBigDecimal("balance"));
                account.setStatus(rs.getString("status"));
                account.setMinimumBalance(rs.getBigDecimal("minimum_balance"));
                accounts.add(account);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching accounts: " + e.getMessage());
        }

        return accounts;
    }

    public List<Account> getAccountsByCustomerId(int customerId) {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM accounts WHERE customer_id = ? ORDER BY account_id";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, customerId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Account account = new Account();
                    account.setAccountId(rs.getInt("account_id"));
                    account.setCustomerId(rs.getInt("customer_id"));
                    account.setAccountNumber(rs.getString("account_number"));
                    account.setAccountType(rs.getString("account_type"));
                    account.setBalance(rs.getBigDecimal("balance"));
                    account.setStatus(rs.getString("status"));
                    account.setMinimumBalance(rs.getBigDecimal("minimum_balance"));
                    accounts.add(account);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error fetching accounts for customer: " + e.getMessage());
        }

        return accounts;
    }

    public Account getAccountById(int accountId) {
        String sql = "SELECT * FROM accounts WHERE account_id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, accountId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Account account = new Account();
                    account.setAccountId(rs.getInt("account_id"));
                    account.setCustomerId(rs.getInt("customer_id"));
                    account.setAccountNumber(rs.getString("account_number"));
                    account.setAccountType(rs.getString("account_type"));
                    account.setBalance(rs.getBigDecimal("balance"));
                    account.setStatus(rs.getString("status"));
                    account.setMinimumBalance(rs.getBigDecimal("minimum_balance"));
                    return account;
                }
            }

        } catch (SQLException e) {
            System.err.println("Error fetching account: " + e.getMessage());
        }

        return null;
    }

    public Account getAccountByNumber(String accountNumber) {
        String sql = "SELECT * FROM accounts WHERE account_number = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, accountNumber);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Account account = new Account();
                    account.setAccountId(rs.getInt("account_id"));
                    account.setCustomerId(rs.getInt("customer_id"));
                    account.setAccountNumber(rs.getString("account_number"));
                    account.setAccountType(rs.getString("account_type"));
                    account.setBalance(rs.getBigDecimal("balance"));
                    account.setStatus(rs.getString("status"));
                    account.setMinimumBalance(rs.getBigDecimal("minimum_balance"));
                    return account;
                }
            }

        } catch (SQLException e) {
            System.err.println("Error fetching account by number: " + e.getMessage());
        }

        return null;
    }

    public boolean accountExists(int customerId, String accountType) {
        String sql = "SELECT COUNT(*) FROM accounts WHERE customer_id = ? AND account_type = ? AND status = 'ACTIVE'";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, customerId);
            pstmt.setString(2, accountType);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            System.err.println("Error checking account existence: " + e.getMessage());
        }

        return false;
    }

    public String createAccount(int customerId, String accountType, BigDecimal initialDeposit) {
        // Check if customer already has this type of account
        if (accountExists(customerId, accountType)) {
            return "Customer already has an active " + accountType.toLowerCase() + " account. Cannot create duplicate account type.";
        }

        String sql = "INSERT INTO accounts (customer_id, account_number, account_type, balance, status, minimum_balance) VALUES (?, ?, ?, ?, 'ACTIVE', ?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Generate unique account number
            String accountNumber;
            do {
                accountNumber = "ACC" + System.currentTimeMillis() + (int)(Math.random() * 1000);
            } while (getAccountByNumber(accountNumber) != null); // Ensure uniqueness

            pstmt.setInt(1, customerId);
            pstmt.setString(2, accountNumber);
            pstmt.setString(3, accountType);
            pstmt.setBigDecimal(4, initialDeposit);
            pstmt.setBigDecimal(5, BigDecimal.ZERO); // Default minimum balance

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                return null; // Success
            } else {
                return "Failed to create account for unknown reasons.";
            }

        } catch (SQLException e) {
            System.err.println("Error creating account: " + e.getMessage());
            return e.getMessage();
        }
    }

    public String createAccountWithNumber(int customerId, String accountNumber, String accountType, BigDecimal initialDeposit) {
        // Check if account number already exists
        if (getAccountByNumber(accountNumber) != null) {
            return "Account number already exists. Please choose a different account number.";
        }

        // Check if customer already has this type of account
        if (accountExists(customerId, accountType)) {
            return "Customer already has an active " + accountType.toLowerCase() + " account. Cannot create duplicate account type.";
        }

        String sql = "INSERT INTO accounts (customer_id, account_number, account_type, balance, status, minimum_balance) VALUES (?, ?, ?, ?, 'ACTIVE', ?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, customerId);
            pstmt.setString(2, accountNumber);
            pstmt.setString(3, accountType);
            pstmt.setBigDecimal(4, initialDeposit);
            pstmt.setBigDecimal(5, BigDecimal.ZERO); // Default minimum balance

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                return null; // Success
            } else {
                return "Failed to create account for unknown reasons.";
            }

        } catch (SQLException e) {
            System.err.println("Error creating account: " + e.getMessage());
            return e.getMessage();
        }
    }

    public boolean updateAccountBalance(int accountId, BigDecimal newBalance) {
        String sql = "UPDATE accounts SET balance = ? WHERE account_id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setBigDecimal(1, newBalance);
            pstmt.setInt(2, accountId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error updating account balance: " + e.getMessage());
            return false;
        }
    }

    public boolean updateAccountStatus(int accountId, String status) {
        String sql = "UPDATE accounts SET status = ? WHERE account_id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, status);
            pstmt.setInt(2, accountId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error updating account status: " + e.getMessage());
            return false;
        }
    }

    public List<Account> getAccountsByCustomerUsername(String username) {
        List<Account> accounts = new ArrayList<>();

        // First get userId from username
        String userSql = "SELECT user_id FROM users WHERE username = ?";
        int userId = -1;

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement userPstmt = conn.prepareStatement(userSql)) {

            userPstmt.setString(1, username);

            try (ResultSet userRs = userPstmt.executeQuery()) {
                if (userRs.next()) {
                    userId = userRs.getInt("user_id");
                } else {
                    return accounts; // No user found
                }
            }

            // Then get customerId from userId
            String customerSql = "SELECT customer_id FROM customers WHERE user_id = ?";
            int customerId = -1;

            try (PreparedStatement customerPstmt = conn.prepareStatement(customerSql)) {
                customerPstmt.setInt(1, userId);

                try (ResultSet customerRs = customerPstmt.executeQuery()) {
                    if (customerRs.next()) {
                        customerId = customerRs.getInt("customer_id");
                    } else {
                        return accounts; // No customer found
                    }
                }
            }

            // Finally get accounts by customerId
            String accountSql = "SELECT * FROM accounts WHERE customer_id = ? ORDER BY account_id";

            try (PreparedStatement accountPstmt = conn.prepareStatement(accountSql)) {
                accountPstmt.setInt(1, customerId);

                try (ResultSet rs = accountPstmt.executeQuery()) {
                    while (rs.next()) {
                        Account account = new Account();
                        account.setAccountId(rs.getInt("account_id"));
                        account.setCustomerId(rs.getInt("customer_id"));
                        account.setAccountNumber(rs.getString("account_number"));
                        account.setAccountType(rs.getString("account_type"));
                        account.setBalance(rs.getBigDecimal("balance"));
                        account.setStatus(rs.getString("status"));
                        account.setMinimumBalance(rs.getBigDecimal("minimum_balance"));
                        accounts.add(account);
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Error fetching accounts for customer username: " + e.getMessage());
        }

        return accounts;
    }
}
