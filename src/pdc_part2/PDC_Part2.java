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
public class PDC_Part2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {
                Connection conn = null;
                conn = DriverManager.getConnection("jdbc:derby://localhost:1527/PatientDB; create=true", "admin1", "admin123");
                System.out.println("Connected Successfully");
                Statement statement1 = conn.createStatement();
                statement1.executeUpdate("INSERT INTO PATIENTS VALUES (Jill, Hill, 20, 4548594, Lane)");
                conn.close();
                
            } catch (SQLException e) 
            {
                System.out.println(e.getMessage());
            }
    }

}
