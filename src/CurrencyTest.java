import java.math.BigDecimal;
import model.Account;
import model.Transaction;
import java.sql.Timestamp;

public class CurrencyTest {
    public static void main(String[] args) {
        System.out.println("=== Currency Display Test ===");

        // Test Account model
        Account account = new Account(1, 1, "ACC001", "SAVINGS",
                                    new BigDecimal("2500.00"), "ACTIVE", new BigDecimal("1000.00"));
        System.out.println("Account toString(): " + account.toString());

        // Test Transaction model
        Transaction transaction = new Transaction(1, 1, "DEPOSIT",
                                                new BigDecimal("500.00"), "Test deposit",
                                                null, new Timestamp(System.currentTimeMillis()));
        System.out.println("Transaction toString(): " + transaction.toString());

        // Test currency formatting
        BigDecimal amount = new BigDecimal("1234.56");
        System.out.println("Formatted amount: ₹" + amount);

        System.out.println("=== Test completed successfully! ===");
    }
}
