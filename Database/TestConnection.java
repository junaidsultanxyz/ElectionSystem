// Test database connection for Election System
import java.sql.*;

public class TestConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/election_system";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "junaid.s"; // Update if different
    
    public static void main(String[] args) {
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Establish connection
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            
            if (connection != null) {
                System.out.println("✅ Database connection successful!");
                
                // Test query
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("SHOW TABLES");
                
                System.out.println("📋 Tables in election_system database:");
                while (rs.next()) {
                    System.out.println("  - " + rs.getString(1));
                }
                
                // Test sample data
                rs = stmt.executeQuery("SELECT COUNT(*) as count FROM provinces");
                if (rs.next()) {
                    System.out.println("🏢 Provinces count: " + rs.getInt("count"));
                }
                
                rs = stmt.executeQuery("SELECT COUNT(*) as count FROM parties");
                if (rs.next()) {
                    System.out.println("🎯 Parties count: " + rs.getInt("count"));
                }
                
                connection.close();
                System.out.println("✅ Connection test completed successfully!");
                
            } else {
                System.out.println("❌ Failed to establish database connection!");
            }
            
        } catch (ClassNotFoundException e) {
            System.out.println("❌ MySQL JDBC Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("❌ Database connection error!");
            e.printStackTrace();
        }
    }
}
