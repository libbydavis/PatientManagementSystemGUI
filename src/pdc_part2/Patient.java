/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdc_part2;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
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
    private ArrayList<Measurements> newMeasurements;
    private ArrayList prescriptions;
    private ArrayList<Appointment> appointmentsHistory;
    private Connection conn;
    private String tableName = "ADMIN1.PATIENTS";
    private JTable matchingPatientsTable;
    
    public Patient() throws SQLException 
    {
        NHI = "";
        fName = "";
        lName = "";
        measurements = new ArrayList();
        newMeasurements = new ArrayList();
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
    
    public int getAge() {
        return age;
    }
    
    public JTable getMatchingPatientTable() {
        return matchingPatientsTable;
    }
   
    public String getMeasurementsString() {
        String total = "";
        for (Measurements m : measurements) {
            total += m.toString() + ", ";
        }
        return total;
    }
   
    public void setMeasurement(Measurements measurement) {
        newMeasurements.add(measurement);
    }
    
    public void getPatientFromDatabase(String input, Object option, Object matchingPanel) throws SQLException {
            ResultSet rs;
            Statement statement1 = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sqlQuery = "";
            if (option.equals("NHI")) {
                sqlQuery = "SELECT * FROM " + tableName + " WHERE NHI='" + input + "'"; 
            }
            else if (option.equals("First Name")) {
                input = input.toLowerCase();
                input = input.substring(0, 1).toUpperCase() + input.substring(1);
                sqlQuery = "SELECT * FROM " + tableName + " WHERE FIRSTNAME='" + input + "'";
            }
            else if (option.equals("Last Name")) {
                input = input.toLowerCase();
                input = input.substring(0, 1).toUpperCase() + input.substring(1);
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
                iteratePatient.phoneNumber = rs.getString("PHONENO");
                iteratePatient.address = rs.getString("STREET");
                getMeasurementsFromDatabase(iteratePatient);
                String conditionsString = rs.getString("CONDITIONS");
                //iteratePatient.conditions = convertStringToConditions(conditionsString);
                
                
                matchingPatients.add(iteratePatient);
            }
            if (matchingPatients.size() > 1 && matchingPanel instanceof getPatientPopUp) {
                specifyPatientonPopUp(matchingPatients, (getPatientPopUp) matchingPanel);
            }
            else if (matchingPatients.size() > 1 && matchingPanel instanceof BrowsePatientsPanel) {
                JPanel patientsTable = displayAllPatients(matchingPatients);
                specifyPatientOnTable((BrowsePatientsPanel) matchingPanel, patientsTable);
            }
            else {
                NHI = iteratePatient.NHI;
                fName = iteratePatient.fName;
                lName = iteratePatient.lName;
                age = iteratePatient.age;
                measurements = iteratePatient.measurements;
                address = iteratePatient.address;
                phoneNumber = iteratePatient.phoneNumber;
                conditions = iteratePatient.conditions;
            }
            
            rs.close();
    }
    
    public void getMeasurementsFromDatabase(Patient iteratePatient) throws SQLException {
        ResultSet rs;
        Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        String sqlQuery = "SELECT * FROM ADMIN1.MEASUREMENTS WHERE NHI = '" + iteratePatient.NHI + "'";
        rs = statement.executeQuery(sqlQuery);
        
        //set measurements to patient
        rs.beforeFirst();
        Measurements currentMeasurement;
        while(rs.next()) {
           currentMeasurement = new Measurements();
           currentMeasurement.name = rs.getString("NAME");
           currentMeasurement.measurement = rs.getDouble("VALUE");
           currentMeasurement.units = rs.getString("UNIT");
           iteratePatient.measurements.add(currentMeasurement);
        }
    }
    
    public void specifyPatientonPopUp(ArrayList<Patient> matchingPatients, getPatientPopUp patientPopUp) {
        Object[] matchingPatientsArray = matchingPatients.toArray();
        JComboBox patientSelect = new JComboBox(matchingPatientsArray);
        patientPopUp.setPatientPicker(patientSelect);
    }
    
    public void specifyPatientOnTable(BrowsePatientsPanel panel, JComponent table) {
        panel.setPatientTable(table);
    }
    
    public HashSet convertStringToConditions(String conditionsString) {
        HashSet conditionsSet = new HashSet();
        String[] conditionsList = conditionsString.split(", ");
        int indexCounter = 0;
        String addString = "";
        for (int i = 0; i < conditionsList.length; i++) {
            if (indexCounter == 0) {
                addString += conditionsList[i] + ", ";
            }
            else if (indexCounter == 1) {
                addString += conditionsList[i];
                conditionsSet.add(addString);
                addString = "";
                indexCounter = -1;
            }
            indexCounter++;
            
        }
        return conditionsSet;
    }
    
    /*
    public ArrayList convertStringToMeasurements(String measurementString) {
       ArrayList theseMeasurements = new ArrayList();
       if (measurementsString != null && measurementsString.length() > 0) {
        String[] measurementsList = measurementString.split(",");
        Measurements measurement = new Measurements();
        int indexCounter = 0;
        for (int i = 0; i < measurementsList.length; i++) {
            if (indexCounter == 0) {
                String[] splitValue = measurementsList[i].split(": ");
                measurement.name = splitValue[1];
            }
            else if (indexCounter == 1) {
                String[] splitValue = measurementsList[i].split(": ");
                measurement.measurement = Double.parseDouble(splitValue[1]);
            }
            else if (indexCounter == 2) {
                String[] splitValue = measurementsList[i].split(": ");
                measurement.units = splitValue[1];
                indexCounter = -1;
                theseMeasurements.add(measurement);
            }
            indexCounter++;
        }
       }
       return theseMeasurements;
    }
*/
    
    public JComponent displayIndividualPatientDetails() {
        JPanel patientDetails = new JPanel();
        patientDetails.setLayout(new GridLayout(0, 2));
        
        Font normalFont = new Font("Arial", Font.PLAIN, 18);
        Font boldFont = new Font("Arial", Font.BOLD, 18);
        
        JLabel nameLabel = new JLabel("Name");
        JLabel name = new JLabel(fName + " " + lName);
        nameLabel.setFont(boldFont);
        name.setFont(normalFont);
        patientDetails.add(nameLabel);
        patientDetails.add(name);
        
        JLabel nhiLabel = new JLabel("NHI");
        nhiLabel.setFont(boldFont);
        JLabel nhiValue = new JLabel(NHI);
        nhiValue.setFont(normalFont);
        patientDetails.add(nhiLabel);
        patientDetails.add(nhiValue);
                
        JLabel ageLabel = new JLabel("Age");
        ageLabel.setFont(boldFont);
        JLabel ageValue = new JLabel(String.valueOf(age));
        ageValue.setFont(normalFont);
        patientDetails.add(ageLabel);
        patientDetails.add(ageValue);
        
        JLabel addressLabel = new JLabel("Address");
        addressLabel.setFont(boldFont);
        JLabel addressValue = new JLabel(address);
        addressValue.setFont(normalFont);
        patientDetails.add(addressLabel);
        patientDetails.add(addressValue);
        
        JLabel phoneLabel = new JLabel("Phone Number ");
        phoneLabel.setFont(boldFont);
        JLabel phoneValue = new JLabel(phoneNumber);
        phoneValue.setFont(normalFont);
        patientDetails.add(phoneLabel);
        patientDetails.add(phoneValue);
        
        /*
        JLabel conditionsLabel = new JLabel("Conditions");
        conditionsLabel.setFont(boldFont);
        JLabel conditionsValue = new JLabel(stringCollection(conditions));
        conditionsValue.setFont(normalFont);
        patientDetails.add(conditionsLabel);
        patientDetails.add(conditionsValue);
        */
        JLabel measurementsLabel = new JLabel("Measurements");
        measurementsLabel.setFont(boldFont);
        JLabel measurementsValues = new JLabel(stringCollection(measurements));
        measurementsValues.setFont(normalFont);
        patientDetails.add(measurementsLabel);
        patientDetails.add(measurementsValues);
        
        return patientDetails;
    }
    
    public String stringCollection(Collection collection) {
        String total = "";
        for (Object o: collection) {
            total += o + "\n";
        }
        return total;
    }
    
    public DefaultTableModel patientColumnNames()
    {
        DefaultTableModel patientColumns = new DefaultTableModel();
        
        patientColumns.addColumn("NHI");
        patientColumns.addColumn("FIRSTNAME");
        patientColumns.addColumn("LASTNAME");
        
        return patientColumns;
    }
   
    public JPanel displayAllPatients(ArrayList<Patient> matchingPatients)
    {
        DefaultTableModel patientTableModel = patientColumnNames();
        JTable patientTable = new JTable(patientTableModel);
        DatabaseConnection dbc = new DatabaseConnection();
        try 
        {
             PreparedStatement prepstmt = dbc.getConnectionPatients().prepareStatement("SELECT NHI, FIRSTNAME, LASTNAME FROM PATIENTS");
            if (matchingPatients != null) {
               prepstmt = dbc.getConnectionPatients().prepareStatement("SELECT NHI, FIRSTNAME, LASTNAME FROM PATIENTS WHERE FIRSTNAME='" + matchingPatients.get(0).fName + "'");
            }
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
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        JPanel patientPanel = new JPanel();
        patientPanel.add(patientJsp);
        
        matchingPatientsTable = patientTable;
        
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
        updateMeasurements();
        //updateConditions();
        Statement statement2 = conn.createStatement();
        Timestamp ts = Timestamp.from(app.date);
        String query1 = "INSERT INTO ADMIN1.APPOINTMENT (NHI, REASONS, MEASUREMENTS, NOTES, DATETIME) VALUES ('" + app.NHI + "', '" + app.getReasonsString() + "', '" + app.getMeasurementsString() + "', '" + app.getNotesString() +  "', '" + ts + "')";
        statement2.executeUpdate(query1);
    }
    
    public void updateMeasurements() throws SQLException {
        if (newMeasurements.size() > 0) {
            Statement statement1 = conn.createStatement();
            for (Measurements m : newMeasurements) {
                String query = "INSERT INTO ADMIN1.MEASUREMENTS (NHI, NAME, VALUE, UNIT) VALUES ('" + NHI + "', '" + m.name + "', " + m.measurement + ", '" + m.units + "')";
                statement1.addBatch(query);
            }
            statement1.executeBatch();
        }
    }
    
    public String toString() {
        return fName + " " + lName + " NHI: " + NHI;
    }
}