package lms.database;

import java.sql.*;

public class MySQL {
    private static final String URL = "jdbc:mysql://localhost:3306/lms";
    private static final String USER = "root";
    private static final String PASSWORD = "admin";

    private static Connection connection;

    private MySQL() {}

    // Get connection for MySQL database
    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException e) {
                System.out.println("MySQL: Failed to connect.");
                return null;
            }
        }
        return connection;
    }

    // Close connection for MySQL database
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("MySQL: Failed to close.");
        }
    }
    
    // Execute query
    public static ResultSet executeQuery(String query, Object[] params) {
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }

            return stmt.executeQuery();
        } catch (SQLException e) {
            return null;
        }
    }
    
    // Execute update
    public static int executeUpdate(String query, Object[] params) {
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }

            return stmt.executeUpdate();
        } catch (SQLException e) {
            return 0;
        }
    }
}
