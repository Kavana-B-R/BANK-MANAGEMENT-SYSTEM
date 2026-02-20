package model;

import java.math.BigDecimal;

public class Account {
    private int accountId;
    private int customerId;
    private String accountNumber;
    private String accountType;
    private BigDecimal balance;
    private String status;
    private BigDecimal minimumBalance;
    
    public Account() {}
    
    public Account(int accountId, int customerId, String accountNumber, String accountType, 
                  BigDecimal balance, String status, BigDecimal minimumBalance) {
        this.accountId = accountId;
        this.customerId = customerId;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = balance;
        this.status = status;
        this.minimumBalance = minimumBalance;
    }
    
    // Getters and Setters
    public int getAccountId() { return accountId; }
    public void setAccountId(int accountId) { this.accountId = accountId; }
    
    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    
    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
    
    public String getAccountType() { return accountType; }
    public void setAccountType(String accountType) { this.accountType = accountType; }
    
    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public BigDecimal getMinimumBalance() { return minimumBalance; }
    public void setMinimumBalance(BigDecimal minimumBalance) { this.minimumBalance = minimumBalance; }
    
    @Override
    public String toString() {
        return accountNumber + " - " + accountType + " - ₹" + balance;
    }
}
