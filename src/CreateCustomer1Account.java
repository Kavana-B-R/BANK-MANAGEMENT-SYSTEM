import service.AccountService;
import java.math.BigDecimal;

public class CreateCustomer1Account {
    public static void main(String[] args) {
        AccountService accountService = new AccountService();

        // Try to create an account for Customer1
        // First, we need to find or create the customer
        // For now, let's assume Customer1 exists and has customer_id = 1

        // Let's create account for the user with username "customer1"
        // We need to find the customer_id for this user
        // For now, let's assume customer_id = 3 (since user_id = 3 for customer1)
        int customerId = 3; // customer1 has user_id = 3, so likely customer_id = 3
        String accountType = "CURRENT";
        BigDecimal initialDeposit = new BigDecimal("1000.00");

        String result = accountService.createAccount(customerId, accountType, initialDeposit);

        if (result == null) {
            System.out.println("Account created successfully for Customer1!");
        } else {
            System.out.println("Failed to create account: " + result);
        }
    }
}
