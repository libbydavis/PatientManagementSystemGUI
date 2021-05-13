/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
        System.out.println("Connected Successfully");
        return conn;
    }
}
