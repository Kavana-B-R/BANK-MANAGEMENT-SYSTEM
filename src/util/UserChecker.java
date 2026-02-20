package util;

import service.UserService;
import java.util.List;
import model.User;

public class UserChecker {
    public static void main(String[] args) {
        System.out.println("Checking all users in database...");
        
        UserService userService = new UserService();
        List<User> users = userService.getAllUsers();
        
        System.out.println("Total users found: " + users.size());
        System.out.println("User details:");
        System.out.println("--------------------------------------------------");
        System.out.printf("%-8s %-15s %-10s %-8s%n", "ID", "Username", "Role", "Active");
        System.out.println("--------------------------------------------------");
        
        for (User user : users) {
            System.out.printf("%-8d %-15s %-10s %-8s%n", 
                user.getUserId(), 
                user.getUsername(), 
                user.getRole(), 
                user.isActive() ? "Yes" : "No");
        }
        System.out.println("--------------------------------------------------");
    }
}
