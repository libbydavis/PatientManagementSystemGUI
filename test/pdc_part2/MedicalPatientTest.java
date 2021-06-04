/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdc_part2;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author libst
 */
public class MedicalPatientTest {
    
    public MedicalPatientTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
   
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getPatientFromDatabase method, of class MedicalPatient.
     */
    @Test
    public void testGetPatientFromDatabase_3args() throws Exception {
        System.out.println("getPatientFromDatabase");
        String input = "jnv758";
        Object option = "NHI";
        Object matchingPanel = null;
        MedicalPatient instance = new MedicalPatient();
        instance.getPatientFromDatabase(input, option, matchingPanel);
        String name = instance.getfName();
        assertEquals("Harry", name);
    }
    

    /*
    /**
     * Test of saveAppointmentToDB method, of class MedicalPatient.
     */
    @Test
    public void testSaveAppointmentToDB() throws Exception {
        System.out.println("saveAppointmentToDB");
        Appointment app = new Appointment();
        app.NHI = "jnv758";
        JTextField reasonsField = new JTextField();
        app.setReasons("Feeling sick", reasonsField);
        
        MedicalPatient instance = new MedicalPatient();
        instance.setNHI("jnv758");
        instance.saveAppointmentToDB(app);
        
        
        
        //get appointment that was just saved
        DatabaseConnection DBconnect = new DatabaseConnection();
        Connection conn = DBconnect.getConnectionPatients();
        
        Statement statement1 = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        
        String query = "";
        query = "SELECT * FROM ADMIN1.APPOINTMENT WHERE REASONS='Feeling sick,'";
        ResultSet rs = statement1.executeQuery(query);
        rs.beforeFirst();
        rs.next();
        String reasons = rs.getString("REASONS");
        
        assertEquals("Feeling sick, ", reasons);
        
        
    }
    

    
}
