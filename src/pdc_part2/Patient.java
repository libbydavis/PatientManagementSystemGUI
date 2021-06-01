/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdc_part2;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Vector;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import org.apache.derby.shared.common.error.DerbySQLIntegrityConstraintViolationException;

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
    private getPatientPopUp patientPopUp;
    
    public Patient() throws SQLException 
    {
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

    public void setfName(String fName) 
    {
        this.fName = fName;
    }

    public void setlName(String lName) 
    {
        this.lName = lName;
    }

    public void setAge(int age) 
    {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public HashSet getConditions() {
        return conditions;
    }
    
    public void setPhoneNumber(String phoneNumber) 
    {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getCurrentMedications() {
        String currentMedsInDB = "";
        String currentMeds = "";

        try {
            currentMeds = currentMedications.toString();

        } catch (NullPointerException e) {
            currentMeds = "\"No current medication\"";
        }

        Scanner scan = new Scanner(currentMeds);
        scan.useDelimiter("\\[\\[*|\\]\\]*");

        while (scan.hasNext()) {
            currentMedsInDB += scan.next();
        }

        return currentMedsInDB;
    }
    
    public void setAddress(String address) 
    {
        this.address = address;
    }

    public void setNHI(String NHI) 
    {
        this.NHI = NHI;
    }

    public void setConditions(HashSet conditions) 
    {
        this.conditions = conditions;
    }

    public void setCurrentMedications(HashSet currentMedications) 
    {
        this.currentMedications = currentMedications;
    }

    public void setMeasurements(ArrayList<Measurements> measurements) 
    {
        this.measurements = measurements;
    }

    public void setPopUp(getPatientPopUp patientPopUp) {
        this.patientPopUp = patientPopUp;
    }
    
    public static ArrayList<String> paitentNHIList() throws SQLException
    {
        ArrayList<String> nhiList = new ArrayList<String>();
        
        DatabaseConnection dbc = new DatabaseConnection();
        String sqlQuery = "SELECT NHI FROM PATIENTS";
        PreparedStatement prepstmt = dbc.getConnectionPatients().prepareStatement(sqlQuery);
        ResultSet rs = prepstmt.executeQuery();
        nhiList.add("choose patients NHI");
        while (rs.next()) {
            nhiList.add(rs.getString("NHI").toString());
        }
        
        return nhiList;
    }
    
    /**
     * 
     * @param newPat
     * @throws SQLException 
     */
    public void insertPatientToDatabase(Patient newPat) throws SQLException, IOException
    {
        // Just need to add stuff to the query when adding measurements and conditions
        DatabaseConnection dbc = new DatabaseConnection();
        String sqlQuery = "INSERT INTO PATIENTS (NHI, FIRSTNAME, LASTNAME, AGE, PHONENO ,STREET, CURRENTMEDS)"
                + "VALUES (\'" + newPat.getNHI() + "\', \'" + newPat.getfName() + "\', \'" + newPat.getlName() + "\'," + newPat.getAge()+ ", " + newPat.getPhoneNumber() + ",\'" + newPat.getAddress() + "\',\'" + newPat.getCurrentMedications() + "\')";
        try {
            PreparedStatement prepstmt = dbc.getConnectionPatients().prepareStatement(sqlQuery);
            prepstmt.executeUpdate();
        } catch (DerbySQLIntegrityConstraintViolationException ex) {
            ex.printStackTrace();
        }
                
    }
    
    public void getPatientFromDatabase(String input, Object option) throws SQLException {
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
        Timestamp ts = Timestamp.from(app.date);
        String query1 = "INSERT INTO ADMIN1.APPOINTMENT (NHI, REASONS, MEASUREMENTS, NOTES, DATETIME) VALUES ('" + app.NHI + "', '" + app.getReasonsString() + "', '" + app.getMeasurementsString() + "', '" + app.getNotesString() +  "', '" + ts + "')";
        statement2.executeUpdate(query1);
    }
    
    public String toString() {
        return fName + " " + lName + " NHI: " + NHI;
    }
}