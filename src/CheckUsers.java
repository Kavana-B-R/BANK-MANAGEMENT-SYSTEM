import service.UserService;
import model.User;
import java.util.List;

public class CheckUsers {
    public static void main(String[] args) {
        UserService userService = new UserService();

        List<User> users = userService.getAllUsers();

        System.out.println("All users in the system:");
        for (User user : users) {
            System.out.println("ID: " + user.getUserId() +
                             ", Username: " + user.getUsername() +
                             ", Role: " + user.getRole() +
                             ", Active: " + user.isActive());
        }
    }
}
