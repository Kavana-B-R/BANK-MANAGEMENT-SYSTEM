import service.AccountService;
import model.Account;
import java.util.List;

public class CheckAccounts {
    public static void main(String[] args) {
        AccountService accountService = new AccountService();

        List<Account> accounts = accountService.getAllAccounts();

        System.out.println("All accounts in the system:");
        for (Account account : accounts) {
            System.out.println("Account ID: " + account.getAccountId() +
                             ", Customer ID: " + account.getCustomerId() +
                             ", Account Number: " + account.getAccountNumber() +
                             ", Type: " + account.getAccountType() +
                             ", Balance: " + account.getBalance() +
                             ", Status: " + account.getStatus());
        }

        if (accounts.isEmpty()) {
            System.out.println("No accounts found in the database.");
        }
    }
}
