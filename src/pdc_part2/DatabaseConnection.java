package pdc_part2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author libst
 */
public class DatabaseConnection {
    public DatabaseConnection() {
        
    }
    
    public Connection getConnectionPatients() throws SQLException {
        Connection conn = null;
        conn = DriverManager.getConnection("jdbc:derby://localhost:1527/PatientDB;", "admin1", "admin123");
        return conn;
    }
    
    public Connection getConnectionMedication() throws SQLException
    {
        Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/MedicationDB;", "admin1", "admin123");
        return conn;
    }
}
