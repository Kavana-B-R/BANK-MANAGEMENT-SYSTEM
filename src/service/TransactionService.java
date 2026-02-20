package service;

import model.Transaction;
import model.Account;
import util.DatabaseUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class TransactionService {

    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions ORDER BY transaction_date DESC";

        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setTransactionId(rs.getInt("transaction_id"));
                transaction.setAccountId(rs.getInt("account_id"));
                transaction.setTransactionType(rs.getString("transaction_type"));
                transaction.setAmount(rs.getBigDecimal("amount"));
                transaction.setDescription(rs.getString("description"));
                transaction.setRelatedAccountId(rs.getObject("related_account_id", Integer.class));
                transaction.setTransactionDate(rs.getTimestamp("transaction_date"));
                transactions.add(transaction);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching transactions: " + e.getMessage());
        }

        return transactions;
    }

    public List<Transaction> getTransactionsByAccountId(int accountId) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE account_id = ? ORDER BY transaction_date DESC";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, accountId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Transaction transaction = new Transaction();
                    transaction.setTransactionId(rs.getInt("transaction_id"));
                    transaction.setAccountId(rs.getInt("account_id"));
                    transaction.setTransactionType(rs.getString("transaction_type"));
                    transaction.setAmount(rs.getBigDecimal("amount"));
                    transaction.setDescription(rs.getString("description"));
                    transaction.setRelatedAccountId(rs.getObject("related_account_id", Integer.class));
                    transaction.setTransactionDate(rs.getTimestamp("transaction_date"));
                    transactions.add(transaction);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error fetching transactions for account: " + e.getMessage());
        }

        return transactions;
    }

    public String processTransaction(String accountNumber, String transactionType, BigDecimal amount, String description) {
        Connection conn = null;
        try {
            conn = DatabaseUtil.getConnection();
            conn.setAutoCommit(false); // Start transaction

            // First, get the account by account number
            AccountService accountService = new AccountService();
            Account account = accountService.getAccountByNumber(accountNumber);

            if (account == null) {
                return "Account not found: " + accountNumber;
            }

            // Check account status
            if (!"ACTIVE".equals(account.getStatus())) {
                return "Account is not active: " + accountNumber;
            }

            BigDecimal newBalance = account.getBalance();

            // Calculate new balance based on transaction type
            if ("DEPOSIT".equals(transactionType)) {
                newBalance = newBalance.add(amount);
            } else if ("WITHDRAWAL".equals(transactionType)) {
                if (newBalance.subtract(amount).compareTo(account.getMinimumBalance()) < 0) {
                    return "Insufficient funds. Minimum balance requirement not met.";
                }
                newBalance = newBalance.subtract(amount);
            } else {
                return "Invalid transaction type: " + transactionType;
            }

            // Update account balance
            boolean balanceUpdated = accountService.updateAccountBalance(account.getAccountId(), newBalance);
            if (!balanceUpdated) {
                conn.rollback();
                return "Failed to update account balance";
            }

            // Record the transaction
            String insertSql = "INSERT INTO transactions (account_id, transaction_type, amount, description, transaction_date) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
                pstmt.setInt(1, account.getAccountId());
                pstmt.setString(2, transactionType);
                pstmt.setBigDecimal(3, amount);
                pstmt.setString(4, description);
                pstmt.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));

                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected == 0) {
                    conn.rollback();
                    return "Failed to record transaction";
                }
            }

            conn.commit();
            return null; // Success

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    System.err.println("Error rolling back transaction: " + rollbackEx.getMessage());
                }
            }
            System.err.println("Error processing transaction: " + e.getMessage());
            return e.getMessage();
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Error closing connection: " + e.getMessage());
                }
            }
        }
    }

    public String processTransfer(String fromAccountNumber, String toAccountNumber, BigDecimal amount, String description) {
        Connection conn = null;
        try {
            conn = DatabaseUtil.getConnection();
            conn.setAutoCommit(false); // Start transaction

            // Get both accounts
            AccountService accountService = new AccountService();
            Account fromAccount = accountService.getAccountByNumber(fromAccountNumber);
            Account toAccount = accountService.getAccountByNumber(toAccountNumber);

            if (fromAccount == null) {
                return "Source account not found: " + fromAccountNumber;
            }
            if (toAccount == null) {
                return "Destination account not found: " + toAccountNumber;
            }

            // Check account statuses
            if (!"ACTIVE".equals(fromAccount.getStatus())) {
                return "Source account is not active: " + fromAccountNumber;
            }
            if (!"ACTIVE".equals(toAccount.getStatus())) {
                return "Destination account is not active: " + toAccountNumber;
            }

            // Check sufficient funds
            if (fromAccount.getBalance().subtract(amount).compareTo(fromAccount.getMinimumBalance()) < 0) {
                return "Insufficient funds in source account";
            }

            // Update balances
            BigDecimal newFromBalance = fromAccount.getBalance().subtract(amount);
            BigDecimal newToBalance = toAccount.getBalance().add(amount);

            boolean fromUpdated = accountService.updateAccountBalance(fromAccount.getAccountId(), newFromBalance);
            boolean toUpdated = accountService.updateAccountBalance(toAccount.getAccountId(), newToBalance);

            if (!fromUpdated || !toUpdated) {
                conn.rollback();
                return "Failed to update account balances";
            }

            // Record transactions for both accounts
            Timestamp now = Timestamp.valueOf(LocalDateTime.now());
            String insertSql = "INSERT INTO transactions (account_id, transaction_type, amount, description, related_account_id, transaction_date) VALUES (?, ?, ?, ?, ?, ?)";

            // Debit transaction for source account
            try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
                pstmt.setInt(1, fromAccount.getAccountId());
                pstmt.setString(2, "TRANSFER_OUT");
                pstmt.setBigDecimal(3, amount);
                pstmt.setString(4, description + " (to " + toAccountNumber + ")");
                pstmt.setInt(5, toAccount.getAccountId());
                pstmt.setTimestamp(6, now);
                pstmt.executeUpdate();
            }

            // Credit transaction for destination account
            try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
                pstmt.setInt(1, toAccount.getAccountId());
                pstmt.setString(2, "TRANSFER_IN");
                pstmt.setBigDecimal(3, amount);
                pstmt.setString(4, description + " (from " + fromAccountNumber + ")");
                pstmt.setInt(5, fromAccount.getAccountId());
                pstmt.setTimestamp(6, now);
                pstmt.executeUpdate();
            }

            conn.commit();
            return null; // Success

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    System.err.println("Error rolling back transaction: " + rollbackEx.getMessage());
                }
            }
            System.err.println("Error processing transfer: " + e.getMessage());
            return e.getMessage();
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Error closing connection: " + e.getMessage());
                }
            }
        }
    }
}
