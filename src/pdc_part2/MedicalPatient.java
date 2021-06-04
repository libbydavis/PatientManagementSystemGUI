package pdc_part2;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Scanner;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.apache.derby.shared.common.error.DerbySQLIntegrityConstraintViolationException;

/**
 *
 * @author libst
 * @author Raj
 */
public class MedicalPatient extends Patient{
    private String fName;
    private String lName;
    private int age;
    private String phoneNumber;
    private String address;
    private String NHI;
    private HashSet currentMedications;
    private ArrayList<Measurements> measurements;
    private ArrayList<Measurements> newMeasurements;
    private ArrayList<String> prescriptionsStrings;
    private Connection conn;
    private String tableName = "ADMIN1.PATIENTS";
    private JTable matchingPatientsTable;
    
    public MedicalPatient() throws SQLException 
    {
        NHI = "";
        fName = "";
        lName = "";
        measurements = new ArrayList();
        newMeasurements = new ArrayList();
        prescriptionsStrings = new ArrayList();
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
    
    public String getPhoneNumber() {
        return phoneNumber;
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

    public void setPhoneNumber(String phoneNumber) 
    {
        this.phoneNumber = phoneNumber;
    }
    
     public void setNHI(String NHI) 
    {
        this.NHI = NHI;
    }

    public void setCurrentMedications(HashSet currentMedications) 
    {
        this.currentMedications = currentMedications;
    }

    public void setMeasurements(ArrayList<Measurements> measurements) 
    {
        this.measurements = measurements;
    }
    
    public void setAddress(String address) 
    {
        this.address = address;
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
    
    /**
     * @author -LibbyDavis
     * @param input
     * @param option
     * @param matchingPanel
     * Gets a patient from the database, either searching by NHI, first name or last name.
     * Specify what to search by using option.
     * If there is more than one matching patient, 
     * it uses matchingPanel to call other methods to deal with displaying all matching patients
     * and getting the user selection of the correct patient.
     */
    @Override
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
            ArrayList<MedicalPatient> matchingPatients = new ArrayList();
            MedicalPatient iteratePatient = new MedicalPatient();
            while (rsNext = rs.next()) {
                iteratePatient = new MedicalPatient();
                iteratePatient.NHI = rs.getString("NHI");
                iteratePatient.fName = rs.getString("FIRSTNAME");
                iteratePatient.lName = rs.getString("LASTNAME");
                iteratePatient.age = rs.getInt("AGE");
                iteratePatient.phoneNumber = rs.getString("PHONENO");
                iteratePatient.address = rs.getString("STREET");
                getMeasurementsFromDatabase(iteratePatient);
                getPrescriptionsFromDatabase(iteratePatient);
                
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
                prescriptionsStrings = iteratePatient.prescriptionsStrings;
            }
            
            rs.close();
    }
    
    /**
     * @author -LibbyDavis
     * @param patient
     * Gets all the matching measurements for a patient from the MEASUREMENTS DB
     * Then it adds the measurements to the given patient object
     */
    public void getMeasurementsFromDatabase(MedicalPatient patient) throws SQLException {
        ResultSet rs;
        Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        String sqlQuery = "SELECT * FROM ADMIN1.MEASUREMENTS WHERE NHI = '" + patient.NHI + "'";
        rs = statement.executeQuery(sqlQuery);
        
        //set measurements to patient
        rs.beforeFirst();
        Measurements currentMeasurement;
        while(rs.next()) {
           currentMeasurement = new Measurements();
           currentMeasurement.name = rs.getString("NAME");
           currentMeasurement.measurement = rs.getDouble("VALUE");
           currentMeasurement.units = rs.getString("UNIT");
           patient.measurements.add(currentMeasurement);
        }
    }
    
    public void getPrescriptionsFromDatabase(MedicalPatient patient) throws SQLException {
        ResultSet rs;
        Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        String sqlQuery = "SELECT * FROM ADMIN1.PRESCRIPTIONS WHERE NHI = '" + patient.NHI + "'";
        rs = statement.executeQuery(sqlQuery);
        
        String prescriptionString = "";
        while (rs.next()) {
            prescriptionString = (rs.getString("PRESCRIPTION_DETAILS"));
            
            String[] splitRemoveEnd = prescriptionString.split("Side Effects:");
            String[] medicationName = splitRemoveEnd[0].split("Medication Name: ");
            String[] dateTime = medicationName[0].split("Doctors. Name:");
            
            prescriptionString = dateTime[0] + " Medicine: " + medicationName[1];
            patient.prescriptionsStrings.add(prescriptionString);
        }
    }
    
    /**
     * @author -LibbyDavis
     * @param matchingPatients
     * @param patientPopUp
     * Creates a comboBox with all the possible patients (all have the same first name or same last name)
     * and gives it to setPatientPicker() so that the user can select the correct patient
     */
    public void specifyPatientonPopUp(ArrayList<MedicalPatient> matchingPatients, getPatientPopUp patientPopUp) {
        Object[] matchingPatientsArray = matchingPatients.toArray();
        JComboBox patientSelect = new JComboBox(matchingPatientsArray);
        patientPopUp.setPatientPicker(patientSelect);
    }
    
    /**
     * @author -LibbyDavis
     * @param panel
     * @param table
     * Gives a JTable of possible patients (all have the same first name or last name)
     * to setPatientTable() so that the user can select the correct patient
     */
    public void specifyPatientOnTable(BrowsePatientsPanel panel, JComponent table) {
        panel.setPatientTable(table);
    }
    
    /**
     * @author -LibbyDavis
     * @param frame
     * @return JComponent
     * Creates a JPanel which displays all the patient details
     */
    public JComponent displayIndividualPatientDetails(PatientManagementView frame) {
        JPanel patientDetails = new JPanel();
        patientDetails.setLayout(new GridLayout(0, 2));
        patientDetails.setPreferredSize(new Dimension(frame.getWidth()/2, frame.getHeight()/2));
        
        Font normalFont = new Font("Arial", Font.PLAIN, 20);
        Font normalFontPresc = new Font("Arial", Font.PLAIN, 14);
        Font boldFont = new Font("Arial", Font.BOLD, 20);
        
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
        
        JLabel measurementsLabel = new JLabel("Measurements");
        measurementsLabel.setFont(boldFont);
        JLabel measurementsValues = new JLabel(stringCollection(measurements));
        measurementsValues.setFont(normalFont);
        patientDetails.add(measurementsLabel);
        patientDetails.add(measurementsValues);
        
        
        JLabel prescriptionsLabel = new JLabel("Prescriptions");
        prescriptionsLabel.setFont(boldFont);
        JLabel prescriptionsValues = new JLabel();
        patientDetails.add(prescriptionsLabel);
        for (String s : prescriptionsStrings) {
            prescriptionsValues = new JLabel(s);
            prescriptionsValues.setFont(normalFontPresc);
            patientDetails.add(prescriptionsValues);
            patientDetails.add(new JLabel());
        }
        if (prescriptionsStrings.size() < 1) {
            JLabel none = new JLabel("none");
            none.setFont(normalFont);
            patientDetails.add(none);
        }
        
        return patientDetails;
    }
    
    /**
     * @author -LibbyDavis
     * @param collection
     * @return String
     * Makes a string to display all the items in a collection easily
     */
    public String stringCollection(Collection collection) {
        String total = "";
        for (Object o: collection) {
            total += o + ", ";
        }
        if (total.equals("")){
            total = "none";
        }
        return total;
    }
    
    /**
        * Makes some of the columns for the patient table
        * @return returns a DefaultTableModel
        * @author Raj
        **/
    public DefaultTableModel patientColumnNames()
    {
        DefaultTableModel patientColumns = new DefaultTableModel();
        
        patientColumns.addColumn("NHI");
        patientColumns.addColumn("FIRSTNAME");
        patientColumns.addColumn("LASTNAME");
        
        return patientColumns;
    }
   
    /**
     * @author -LibbyDavis
     * @author Raj
     * @param matchingPatients
     * @return JPanel
     * Creates a JTable and fills it with a summary of patients 
     * (either all patients or selected matching patients, depending on matchingPatients parameter)
     */
    public JPanel displayAllPatients(ArrayList<MedicalPatient> matchingPatients)
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
    /**
     * This method retrieves a patient from a database and displays it in a table
     * @param nhi is used to retrieve a patient from the database
     * @throws SQLException 
     * @author Raj
     */
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
    
    /**
     * @author -LibbyDavis
     * @param app
     * @throws SQLException
     * Inserts given appointment into the APPOINTMENT DB 
     */
    @Override
    public void saveAppointmentToDB(Appointment app) throws SQLException {
        updateMeasurements();
        Statement statement2 = conn.createStatement();
        Timestamp ts = Timestamp.from(app.date);
        String query1 = "INSERT INTO ADMIN1.APPOINTMENT (NHI, REASONS, MEASUREMENTS, NOTES, DATETIME) VALUES ('" + app.NHI + "', '" + app.getReasonsString() + "', '" + app.getMeasurementsString() + "', '" + app.getNotesString() +  "', '" + ts + "')";
        statement2.executeUpdate(query1);
    }
    
    /**
     * @author -LibbyDavis
     * @throws SQLException
     * Inserts the current patients measurements into the MEASUREMENTS DB
     */
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
    
    /**
     * @author -LibbyDavis
     * @throws SQLException
     * Deletes current patient from PATIENTS DB
     * Deletes current patient's measurements from MEASUREMENTS DB
     * Deletes all appointment records for the current patient from APPOINTMENT DB
     */
    public void deletePatientFromDB() throws SQLException {
        Statement statement = conn.createStatement();
        String measurements = "DELETE FROM ADMIN1.MEASUREMENTS WHERE NHI = '" + NHI + "'";
        statement.addBatch(measurements);
        String appointments = "DELETE FROM ADMIN1.APPOINTMENT WHERE NHI = '" + NHI + "'";
        statement.addBatch(appointments);
        String prescriptions = "DELETE FROM ADMIN1.PRESCRIPTIONS WHERE NHI = '" + NHI + "'";
        statement.addBatch(prescriptions);
        String patient = "DELETE FROM ADMIN1.PATIENTS WHERE NHI = '" + NHI + "'";
        statement.addBatch(patient);
        
        statement.executeBatch();
    }
    
    public void insertPatientToDatabase(MedicalPatient newPat) throws SQLException, IOException
    {
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
    
    /**
     * Is used to display in a JComboBox
     * @return an array list of all the current patient's in the patient database
     * @throws SQLException 
     * @author Raj
     */
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
}