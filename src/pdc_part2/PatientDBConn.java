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
public class PatientDBConn {

    /**
     * @param args the command line arguments
     */
    
    public void printPatients(ResultSet rs) throws SQLException
    {
        rs.beforeFirst();
        
        while(rs.next())
        {
            System.out.println("NHI: " + rs.getString("NHI"));
            System.out.println("First Name: " + rs.getString("FIRSTNAME"));
            System.out.println("Last Name: " + rs.getString("LASTNAME"));
            System.out.println("Age: " + rs.getInt("AGE"));
            System.out.println("Phone Number: " + rs.getString("PHONENUMBER"));
            System.out.println("Address: " + rs.getString("ADDRESS"));
            System.out.println("Conditions: " + rs.getString("CONDITIONS"));
            System.out.println("Current Medication: " + rs.getString("CURRENTMEDS"));
            System.out.println("Measurements: " + rs.getString("MEASUREMENTS"));
            System.out.println("Prescriptions: " + rs.getString("PRESCRIPTIONS"));
            System.out.println("Appointment History: " + rs.getString("APTHISTORY"));
            System.out.println("\n");
        }
    }
    
    public static void main(String[] args) 
    {
        try 
        {
            Connection conn = null;
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/PatientDB; create=true", "admin1", "admin123");
            System.out.println("Connected Successfully");
            ResultSet rs;
            Statement statement1 = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String tableName = "ADMIN1.PATIENT";
            String sqlQuery = "SELECT * FROM " + tableName; // PrintAllPatients Method needs this query to work
            rs = statement1.executeQuery(sqlQuery);
            PatientDBConn pdbc = new PatientDBConn();
            
            pdbc.printPatients(rs);

            //statement1.executeUpdate("INSERT INTO PATIENTS VALUES (Jill, Hill, 20, 4548594, Lane)");
            conn.close();

        } 
        catch (SQLException e) 
        {
            System.out.println(e.getMessage());
        }
    }

}
