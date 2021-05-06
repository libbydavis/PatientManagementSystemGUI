/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdc_part2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author libst
 */
public class Patient {
    
    public void getPatientFromDatabase(String nhi) throws SQLException {
            Connection conn = null;
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/PatientDB;", "admin1", "admin123");
            System.out.println("Connected Successfully");
            ResultSet rs;
            Statement statement1 = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String tableName = "ADMIN1.PATIENTS";
            String sqlQuery = "SELECT * FROM " + tableName + " WHERE NHI='" + nhi + "'"; // PrintAllPatients Method needs this query to work
            rs = statement1.executeQuery(sqlQuery);
            PatientDBConn pdbc = new PatientDBConn();
            
            pdbc.printPatients(rs);
    }
    
}
