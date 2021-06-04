/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdc_part2;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JTextField;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author libst
 */
public class MedicalPatientTest {
    
    static DatabaseConnection DBconnect;
    static Connection conn; 
    Statement statement1;
    String query;
    
    public MedicalPatientTest() {
    }
    
    @Before
    public void setUp() throws SQLException {
        DBconnect = new DatabaseConnection();
        conn = DBconnect.getConnectionPatients();
    }
    
    @After
    public void tearDown() throws SQLException {
        query = "DELETE FROM ADMIN1.APPOINTMENT WHERE REASONS = 'Testing JUnit4, '";
        Statement statement2 = conn.createStatement();
        statement2.executeUpdate(query);
        
        query = "DELETE FROM ADMIN1.MEASUREMENTS WHERE NAME = 'testing JUnit 4'";
        statement2.executeUpdate(query);
        statement2.close();
    }
    
    /**
     * Test of getPatientFromDatabase method, of class MedicalPatient.
     */
    @Test
    public void testGetPatientFromDatabase_3args() throws Exception {
        System.out.println("getPatientFromDatabase");
        String input = "aui768";
        Object option = "NHI";
        Object matchingPanel = null;
        MedicalPatient instance = new MedicalPatient();
        instance.getPatientFromDatabase(input, option, matchingPanel);
        String name = instance.getfName();
        assertEquals("Poppy", name);
    }
    

    /*
    /**
     * Test of saveAppointmentToDB method, of class MedicalPatient.
     */
    @Test
    public void testSaveAppointmentToDB() throws Exception {
        System.out.println("saveAppointmentToDB");
        Appointment app = new Appointment();
        app.NHI = "aui768";
        JTextField reasonsField = new JTextField();
        app.setReasons("Testing JUnit4", reasonsField);
        
        MedicalPatient instance = new MedicalPatient();
        instance.setNHI("aui768");
        instance.saveAppointmentToDB(app);
        
        
        //get appointment that was just saved
        statement1 = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        
        query = "";
        query = "SELECT * FROM ADMIN1.APPOINTMENT WHERE REASONS='Testing JUnit4,'";
        ResultSet rs = statement1.executeQuery(query);
        rs.beforeFirst();
        rs.next();
        String reasons = rs.getString("REASONS");
        
        assertEquals("Testing JUnit4, ", reasons);
        
        
    }
    
    
     /**
     * Test of updateMeasurements method, of class MedicalPatient.
     */
    @Test
    public void testUpdateMeasurements() throws Exception {
        System.out.println("updateMeasurements");
        MedicalPatient instance = new MedicalPatient();
        Measurements newMeasurement = new Measurements();
        newMeasurement.measurement = 3.3;
        String testString ="testing JUnit 4";
        newMeasurement.name = testString;
        newMeasurement.units = "units";
        instance.setNHI("aui768");
        instance.setMeasurement(newMeasurement);
        
        instance.updateMeasurements();
        //assert(true);
        
        MedicalPatient instance2 = new MedicalPatient();
        instance2.setNHI("aui768");
        Statement statement3 = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        query = "SELECT * FROM ADMIN1.MEASUREMENTS WHERE NAME = '" + testString + "'";
        ResultSet rs = statement3.executeQuery(query);
        rs.beforeFirst();
        rs.next();
        String actualString = rs.getString("NAME");
        assertEquals(testString, actualString);
    }
    

    

    
}
