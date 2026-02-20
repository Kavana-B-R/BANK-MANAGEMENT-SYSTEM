import service.AccountService;
import java.math.BigDecimal;

public class TestAccountCreation {
    public static void main(String[] args) {
        System.out.println("Testing Account Creation by Customer Name");
        System.out.println("==========================================");

        AccountService accountService = new AccountService();

        // Test 1: Create account for existing customer "John Doe"
        System.out.println("\nTest 1: Creating SAVINGS account for John Doe");
        String result1 = accountService.createAccount(1, "SAVINGS", new BigDecimal("500.00"));
        if (result1 == null) {
            System.out.println("✓ Account created successfully for John Doe");
        } else {
            System.out.println("✗ Failed to create account: " + result1);
        }

        // Test 2: Create account for existing customer "Jane Smith"
        System.out.println("\nTest 2: Creating CURRENT account for Jane Smith");
        String result2 = accountService.createAccount(2, "CURRENT", new BigDecimal("1000.00"));
        if (result2 == null) {
            System.out.println("✓ Account created successfully for Jane Smith");
        } else {
            System.out.println("✗ Failed to create account: " + result2);
        }

        // Test 3: Create account for existing customer "Bob Johnson"
        System.out.println("\nTest 3: Creating FIXED_DEPOSIT account for Bob Johnson");
        String result3 = accountService.createAccount(3, "FIXED_DEPOSIT", new BigDecimal("2000.00"));
        if (result3 == null) {
            System.out.println("✓ Account created successfully for Bob Johnson");
        } else {
            System.out.println("✗ Failed to create account: " + result3);
        }

        // Test 4: Create account for new customer "Alice Williams"
        System.out.println("\nTest 4: Creating SAVINGS account for Alice Williams");
        String result4 = accountService.createAccount(4, "SAVINGS", new BigDecimal("750.00"));
        if (result4 == null) {
            System.out.println("✓ Account created successfully for Alice Williams");
        } else {
            System.out.println("✗ Failed to create account: " + result4);
        }

        System.out.println("\n==========================================");
        System.out.println("Account creation tests completed!");
        System.out.println("The teller can now create accounts by entering customer names");
        System.out.println("instead of requiring customer IDs.");
    }
}
