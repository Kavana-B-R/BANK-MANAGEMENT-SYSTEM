import service.UserService;
import model.Customer;
import java.util.List;
import java.sql.*;
import util.DatabaseUtil;

public class CheckCustomers {
    public static void main(String[] args) {
        System.out.println("All customers in the system:");
        String sql = "SELECT * FROM customers ORDER BY customer_id";

        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println("Customer ID: " + rs.getInt("customer_id") +
                                 ", User ID: " + rs.getInt("user_id") +
                                 ", First Name: " + rs.getString("first_name") +
                                 ", Last Name: " + rs.getString("last_name") +
                                 ", Email: " + rs.getString("email"));
            }

        } catch (SQLException e) {
            System.err.println("Error fetching customers: " + e.getMessage());
        }
    }
}
