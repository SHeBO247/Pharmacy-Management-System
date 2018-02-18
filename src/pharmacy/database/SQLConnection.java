package pharmacy.database;

import java.sql.*;

public class SQLConnection {
    
    private static final String DB_URL = "jdbc:mysql://localhost:3306/pharmacy";
    private static final String USER = "root";
    private static final String PASS = "root@root";
    
    static Connection connection;
    
    public static Connection SQLConnector(){
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            
            return connection;
        } catch (SQLException ex) {
            return null;
        }
    }
    
    public static boolean isDbConnected(){
        try {
            return !connection.isClosed();
        } catch (SQLException ex) {
            return false;
        }
    }
    
}
