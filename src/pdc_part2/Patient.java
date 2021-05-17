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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Vector;
import javax.swing.JComboBox;
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
    
    public void getPatientFromDatabase(String input, Object option, getPatientPopUp patientPopUp) throws SQLException {
            ResultSet rs;
            Statement statement1 = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            Statement statement2 = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sqlQuery = "";
            if (option.equals("NHI")) {
                sqlQuery = "SELECT * FROM " + tableName + " WHERE NHI='" + input + "'"; // PrintAllPatients Method needs this query to work
            }
            else if (option.equals("First Name")) {
                sqlQuery = "SELECT * FROM " + tableName + " WHERE FIRSTNAME='" + input + "'";
            }
            else if (option.equals("Last Name")) {
                sqlQuery = "SELECT * FROM " + tableName + " WHERE LASTNAME='" + input + "'";
            }
            rs = statement1.executeQuery(sqlQuery);
            //set values
            rs.beforeFirst();
            boolean rsNext;
            ArrayList<Patient> matchingPatients = new ArrayList();
            Patient iteratePatient = new Patient();
            while (rsNext = rs.next()) {
                iteratePatient = new Patient();
                iteratePatient.NHI = rs.getString("NHI");
                iteratePatient.fName = rs.getString("FIRSTNAME");
                iteratePatient.lName = rs.getString("LASTNAME");
                iteratePatient.age = rs.getInt("AGE");
                
                matchingPatients.add(iteratePatient);
            }
            if (matchingPatients.size() > 1) {
                specifyPatient(matchingPatients, patientPopUp);
            }
            else {
                NHI = iteratePatient.NHI;
                fName = iteratePatient.fName;
                lName = iteratePatient.lName;
                age = iteratePatient.age;
            }
            
            rs.close();
    }
    
    public void specifyPatient(ArrayList<Patient> matchingPatients, getPatientPopUp patientPopUp) {
        
        Object[] matchingPatientsArray = matchingPatients.toArray();
        JComboBox patientSelect = new JComboBox(matchingPatientsArray);
        patientPopUp.setPatientPicker(patientSelect);
    }
    
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
        Timestamp ts = Timestamp.from(app.date);
        String query1 = "INSERT INTO ADMIN1.APPOINTMENT (NHI, REASONS, MEASUREMENTS, NOTES, DATETIME) VALUES ('" + app.NHI + "', '" + app.getReasonsString() + "', '" + app.getMeasurementsString() + "', '" + app.getNotesString() +  "', '" + ts + "')";
        statement2.executeUpdate(query1);
    }
    
    public String toString() {
        return fName + " " + lName + " NHI: " + NHI;
    }
}
    // nhi of the patient you want to make the prescription for // validate whether this nhi exists
    // medno# for the prescription you want to set
    // dosage for the prescription
    // doseFrequency (eat 3 times a week for breakfast e.g)
    // DocName 
    // Date prescription was made