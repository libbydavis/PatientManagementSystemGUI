/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdc_part2;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Raj
 */
public class UnitTestTwo {

    @Test
    public void testNhiList() throws SQLException {
        ArrayList<String> currPatientNhis = new ArrayList<String>(MedicalPatient.paitentNHIList());
        assertEquals(currPatientNhis, MedicalPatient.paitentNHIList());
    }

    @Test
    public void testMedList() {
        String[] testMedList = new String[]{"select an option", "Ibuprofen", "Panadol", "Vicodin", "Oxycontin", "Percocet", "Valium", "Xanax"};
        Assert.assertArrayEquals(testMedList, Medication.medList());
    }

    @Test
    public void testMain() throws IOException, SQLException {
        testMedList();
        testNhiList();
    }

}
