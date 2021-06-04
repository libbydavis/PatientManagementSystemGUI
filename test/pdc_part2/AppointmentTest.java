/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdc_part2;

import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JTextField;
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
public class AppointmentTest {
    
    public AppointmentTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

   

    /**
     * Test of setReasons method, of class Appointment.
     */
    @Test
    public void testSetReasons() {
        System.out.println("setReasons");
        String r = "itchy skin";
        JTextField rField = new JTextField("itchy skin");
        Appointment instance = new Appointment();
        instance.setReasons(r, rField);
        String i = instance.getReasons()[0];
        
        assertEquals("itchy skin", i);
    }

        /**
     * Test of getAppointmentHistory method, of class Appointment.
     */
    @Test
    public void testGetAppointmentHistory() throws Exception {
        System.out.println("getAppointmentHistory");
        String select = "ALL";
        ResultSet result = Appointment.getAppointmentHistory(select);
        result.beforeFirst();
        result.next();
        String r = result.getString("NHI");
        assertNotNull(r);
    }
}
