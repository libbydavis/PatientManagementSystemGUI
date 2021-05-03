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
 * @author Raj
 */
public class MedDBConn 
{
    public static Connection conn;
    public static String url = "jdbc:derby://localhost:1527/MedicationDB; create = true";    
    public static String username = "admin1";
    public static String password = "admin123";
    
    public static void main(String[] args) 
    {
        try 
        {
            conn = DriverManager.getConnection(url, username, password);
            Statement stmt = conn.createStatement();
            ResultSet rs;
            String sqlQuery = "SELECT MEDNO, MEDNAME, CONDITIONS, SIDE_EFFECTS FROM MEDICATION WHERE MEDNO = 5";
            rs = stmt.executeQuery(sqlQuery);
            
            while(rs.next())
            {
                String medNo = rs.getString("MEDNO");
                String medName = rs.getString("MEDNAME");
                String conditions = rs.getString("CONDITIONS");
                String sideEffects = rs.getString("SIDE_EFFECTS");
                System.out.println(medNo + " " + medName + " " + conditions +  " " + sideEffects);   
            }
            
            System.out.println("Connected Successfully");
            conn.close();
        } 
        catch (SQLException e) 
        {
            System.out.println(e.getMessage());
        }
    }
}
