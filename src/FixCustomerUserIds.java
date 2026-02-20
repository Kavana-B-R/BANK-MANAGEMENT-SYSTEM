import java.sql.*;
import util.DatabaseUtil;

public class FixCustomerUserIds {
    public static void main(String[] args) {
        System.out.println("Fixing customer user_id values...");

        // Map customer_id to user_id
        // Assuming customer_id 1 -> user_id 7 (john_doe)
        // customer_id 2 -> user_id 8 (jane_smith)
        // customer_id 3 -> user_id 3 (customer1)
        // customer_id 4 -> user_id 9 (bob_johnson)

        String[] updates = {
            "UPDATE customers SET user_id = 7 WHERE customer_id = 1", // john_doe
            "UPDATE customers SET user_id = 8 WHERE customer_id = 2", // jane_smith
            "UPDATE customers SET user_id = 3 WHERE customer_id = 3", // customer1
            "UPDATE customers SET user_id = 9 WHERE customer_id = 4"  // bob_johnson
        };

        try (Connection conn = DatabaseUtil.getConnection()) {
            for (String sql : updates) {
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    int rowsAffected = pstmt.executeUpdate();
                    System.out.println("Executed: " + sql + " - Rows affected: " + rowsAffected);
                }
            }
            System.out.println("Customer user_id fix completed.");
        } catch (SQLException e) {
            System.err.println("Error fixing customer user_ids: " + e.getMessage());
        }
    }
}
