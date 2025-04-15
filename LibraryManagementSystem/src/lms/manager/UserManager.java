package lms.manager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import lms.database.MySQL;
import lms.factory.UserFactory;
import lms.interfaces.UserOperations;
import lms.model.User;
import lms.utils.PasswordUtils;

public class UserManager implements UserOperations {
    private static UserManager instance;

    private UserManager() { }

    public static UserManager getInstance() {
        if (instance == null) {
           instance = new UserManager();
        }
        return instance;
    }
    
    public User login(String id, String password) {
        ResultSet resultSet = MySQL.executeQuery("SELECT * FROM users WHERE id = ?", new Object[]{id});
        try {
            if (resultSet.next()) {
                int userId = resultSet.getInt("ID");
                String userName = resultSet.getString("Name");
                String userRole = resultSet.getString("Role");
                String userEmail = resultSet.getString("Email");
                String userHashedPassword = resultSet.getString("Password");

                if (PasswordUtils.verifyPassword(password, userHashedPassword)) {
                    return UserFactory.createUser(userId, userName, userRole, userEmail, userHashedPassword);
                }
            }
        }catch(SQLException e) {
            System.out.println("UserManager: SQLException.");
        }
        return null;
    }
    
    public boolean updateUser(int id, String name, String role, String email) {
        MySQL.executeUpdate("UPDATE users SET Name = ?, Role = ?, Email = ? WHERE ID = ?", new Object[]{name, role, email, id});
        JOptionPane.showMessageDialog(null, "User updated successfully!", "", JOptionPane.INFORMATION_MESSAGE);
        return true;
    }
    
    public boolean createUser(String name, String role, String email, String password) {
        if (doesEmailExist(email)) {
            JOptionPane.showMessageDialog(null, "Email already exists!", "", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        MySQL.executeUpdate("INSERT INTO users (Name, Role, Email, Password) VALUES (?, ?, ?, ?)", new Object[]{name, role, email, PasswordUtils.hashPassword(password)});
        JOptionPane.showMessageDialog(null, "Member created successfully!", "", JOptionPane.INFORMATION_MESSAGE);
        return true;
    }
    
    public boolean deleteUser(int id) {
        MySQL.executeUpdate("DELETE FROM users WHERE ID = ?;", new Object[]{id});
        JOptionPane.showMessageDialog(null, "User deleted successfully!", "", JOptionPane.INFORMATION_MESSAGE);
        return true;
    }
    
    public ArrayList<User> getAllUsers() {
        ResultSet resultSet = MySQL.executeQuery("SELECT * FROM users", new Object[]{});
        
        try {
            ArrayList<User> users = new ArrayList<User>();
            while (resultSet.next()) {
                int userId = resultSet.getInt("ID");
                String userName = resultSet.getString("Name");
                String userRole = resultSet.getString("Role");
                String userEmail = resultSet.getString("Email");
                String userHashedPassword = resultSet.getString("Password");
                
                users.add(UserFactory.createUser(userId, userName, userRole, userEmail, userHashedPassword));
            }
            return users;
        }catch(SQLException e) {
            System.out.println("UserManager: SQLException.");
        }
        return null;
    }
    
    private boolean doesEmailExist(String email) {
        ResultSet resultSet = MySQL.executeQuery("SELECT COUNT(*) FROM users WHERE email = ?", new Object[]{email});
        
        try {
            if (resultSet.next())
                return resultSet.getInt(1) > 0;
        }catch(SQLException e) {
            System.out.println("UserManager: SQLException.");
        }
        return false;
    }
}
