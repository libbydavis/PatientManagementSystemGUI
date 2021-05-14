/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdc_part2;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Vector;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

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
    
    public String getNHI() 
    {
        return NHI;
    }
    
    public String getfName() 
    {
        return fName;
    }
    
    public String getlName() 
    {
        return lName;
    }
    

    public void setFName(String fname)
    {
        //TODO
    }
    
    public void setAge(int age)
    {
        
    }
//    public Connection connectToPatientDB() throws SQLException 
//    {
//        Connection conn;
//        return conn = DriverManager.getConnection("jdbc:derby://localhost:1527/PatientDB; create = true","admin1", "admin123");
//    }
    
    public DefaultTableModel patientColumnNames()
    {
        DefaultTableModel patientColumns = new DefaultTableModel();
        
        patientColumns.addColumn("NHI");
        patientColumns.addColumn("FIRSTNAME");
        patientColumns.addColumn("LASTNAME");
        
        return patientColumns;
    }
   
    public JPanel displayAllPatients()
    {
        DefaultTableModel patientTableModel = patientColumnNames();
        JTable patientTable = new JTable(patientTableModel);
        DatabaseConnection dbc = new DatabaseConnection();
        try 
        {
      
            PreparedStatement prepstmt = dbc.getConnectionPatients().prepareStatement("SELECT NHI, FIRSTNAME, LASTNAME FROM PATIENTS");
            ResultSet rs = prepstmt.executeQuery();
            while (rs.next()) 
            {            
                patientTableModel.addRow(new Object[]{rs.getString(1), rs.getString(2), rs.getString(3)});
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        
        
        JScrollPane patientJsp = new JScrollPane(patientTable);
        patientJsp.setPreferredSize(new Dimension(300,600));
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        JPanel patientPanel = new JPanel();
        patientPanel.setSize((screenSize.width/2), (screenSize.height/2));
        patientPanel.add(patientJsp);
        
        return patientPanel;
    }
    
    public void getPatientFromDatabase(String nhi) throws SQLException 
    {
        DefaultTableModel model = new DefaultTableModel();
        DatabaseConnection dbc = new DatabaseConnection();
        //static final String tableName = "ADMIN1.PATIENTS"; 
        
        try 
        {
            PreparedStatement prepstmt = dbc.getConnectionPatients().prepareStatement("SELECT * FROM PATIENTS WHERE NHI = " + "\'" +nhi.toLowerCase()+ "\'");
            ResultSet rs = prepstmt.executeQuery();


            while (rs.next()) 
            {
                model.addRow(new Object[]{rs.getString(3), rs.getString(1), rs.getString(2), rs.getInt(11), rs.getInt(4),
                    rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10)});
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }   
    
    public void saveAppointmentToDB(Appointment app) throws SQLException {
        Statement statement2 = conn.createStatement();
        String query1 = "INSERT INTO ADMIN1.APPOINTMENT (NHI) VALUES ('" + app.NHI + "')";
        statement2.executeUpdate(query1);
    }
    
}
    // nhi of the patient you want to make the prescription for // validate whether this nhi exists
    // medno# for the prescription you want to set
    // dosage for the prescription
    // doseFrequency (eat 3 times a week for breakfast e.g)
    // DocName 
    // Date prescription was made