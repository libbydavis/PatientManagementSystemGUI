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
import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author libst
 */
public class Patient {
    private String fName;
    private String lName;
    private int age;
    private String phoneNumber;
    private String address;
    private String NHI;
    private HashSet conditions;
    private HashSet currentMedications;
    private ArrayList<Measurements> measurements;
    private ArrayList prescriptions;
    private ArrayList<Appointment> appointmentsHistory;
    private Connection conn;
    private String tableName = "ADMIN1.PATIENTS";
    
    public Patient() throws SQLException {
        NHI = "";
        fName = "";
        lName = "";
        DatabaseConnection DBconnect = new DatabaseConnection();
        conn = DBconnect.getConnectionPatients();
    }
    
    public String getNHI() {
        return NHI;
    }
    
    public String getfName() {
        return fName;
    }
    
    public String getlName() {
        return lName;
    }
    
    
    public void getPatientFromDatabase(String nhi) throws SQLException {
            ResultSet rs;
            Statement statement1 = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sqlQuery = "SELECT * FROM " + tableName + " WHERE NHI='" + nhi + "'"; // PrintAllPatients Method needs this query to work
            rs = statement1.executeQuery(sqlQuery);
            
            //set values
            rs.beforeFirst();
            rs.next();
            NHI = rs.getString("NHI");
            fName = rs.getString("FIRSTNAME");
            lName = rs.getString("LASTNAME");
            //PatientDBConn pdbc = new PatientDBConn();
            
            //pdbc.printPatients(rs);
    }
    
    public void saveAppointmentToDB(Appointment app) throws SQLException {
        Statement statement2 = conn.createStatement();
        statement2.executeUpdate("UPDATE " + tableName + " SET ISNULL(APPOINTMENTS_HISTORY, '') = APPOINTMENTS_HISTORY + " + app.toString() + " WHERE NHI = " + this.NHI);
    }
    
}
