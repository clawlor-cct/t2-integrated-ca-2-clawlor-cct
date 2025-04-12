package lms.manager;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import lms.database.MySQL;
import lms.model.User;
import lms.utils.PasswordUtils;

public class UserManager {
    private static UserManager instance;

    private UserManager() { }

    public static UserManager getInstance() {
        if (instance == null) {
           instance = new UserManager();
        }
        return instance;
    }
    
    private boolean doesEmailExist(String email) {
        ResultSet resultSet = MySQL.executeQuery("SELECT COUNT(*) FROM Users WHERE email = ?", new Object[]{email});
        
        try {
            if (resultSet.next()) return true;
        }catch(SQLException e) {
            System.out.println("UserManager: SQLException.");
        }
        return false;
    }
    
    public boolean createUser(String name, String role, String email, String password) {
        if (doesEmailExist(email)) {
            JOptionPane.showMessageDialog(null, "Email already exists!", "", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        MySQL.executeUpdate("INSERT INTO Users (Name, Role, Email, Password) VALUES (?, ?, ?, ?)", new Object[]{name, role, email, PasswordUtils.hashPassword(password)});
        JOptionPane.showMessageDialog(null, "Member created successfully!", "", JOptionPane.INFORMATION_MESSAGE);
        return true;
    }
}
