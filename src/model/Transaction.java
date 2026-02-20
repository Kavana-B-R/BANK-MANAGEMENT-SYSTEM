package model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Transaction {
    private int transactionId;
    private int accountId;
    private String transactionType;
    private BigDecimal amount;
    private String description;
    private Integer relatedAccountId;
    private Timestamp transactionDate;
    
    public Transaction() {}
    
    public Transaction(int transactionId, int accountId, String transactionType, 
                      BigDecimal amount, String description, Integer relatedAccountId, 
                      Timestamp transactionDate) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.description = description;
        this.relatedAccountId = relatedAccountId;
        this.transactionDate = transactionDate;
    }
    
    // Getters and Setters
    public int getTransactionId() { return transactionId; }
    public void setTransactionId(int transactionId) { this.transactionId = transactionId; }
    
    public int getAccountId() { return accountId; }
    public void setAccountId(int accountId) { this.accountId = accountId; }
    
    public String getTransactionType() { return transactionType; }
    public void setTransactionType(String transactionType) { this.transactionType = transactionType; }
    
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public Integer getRelatedAccountId() { return relatedAccountId; }
    public void setRelatedAccountId(Integer relatedAccountId) { this.relatedAccountId = relatedAccountId; }
    
    public Timestamp getTransactionDate() { return transactionDate; }
    public void setTransactionDate(Timestamp transactionDate) { this.transactionDate = transactionDate; }
    
    @Override
    public String toString() {
        return transactionType + " - ₹" + amount + " - Account: " + accountId +
               (relatedAccountId != null ? " (Related: " + relatedAccountId + ")" : "") +
               " - " + transactionDate;
    }
}
