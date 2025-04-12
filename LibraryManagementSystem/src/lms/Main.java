package lms;

import lms.database.MySQL;
import lms.menu.MainMenu;
import java.sql.Connection;
import java.sql.SQLException;
public class Main {
    
    private static Connection connection;
    private static MainMenu menu;
    
    public static void main(String[] args) throws SQLException {
        connection = MySQL.getConnection(); // Establish MySQL connection
        
        menu = new MainMenu();
        menu.setTitle("Library Management System");
        menu.setLocationRelativeTo(null);  // Center menu
        menu.setVisible(true); // Make menu visible
    }
    
}
