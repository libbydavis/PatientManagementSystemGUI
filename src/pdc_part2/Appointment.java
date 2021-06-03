/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdc_part2;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;

public class Appointment {
    Instant date;
    String NHI;
    private String[] reasons;
    private Measurements[] measurements;
    private String[] notes;

    public Appointment() {
        date = Instant.now();
        reasons = new String[10];
        measurements = new Measurements[10];
        notes = new String[10];
    }

    /**
     * @author -LibbyDavis
     * @param list
     * @returns Object[]
     * Expands an array to double the size
     */
    public Object[] expandCapacity(Object[] list) {
        Object[] newList = new Object[list.length * 2];
        for (int i = 0; i < list.length; i++) {
            newList[i] = list[i];
        }

        return newList;
    }

    /**
     * @author -LibbyDavis
     * @param r
     * @param rField
     * @returns Boolean
     * Makes sure reason has been entered then sets reason input in the reasons array (expands array if necessary)
     */
    public boolean setReasons(String r, JTextField rField) {
        if (r.length() < 2) {
            rField.setForeground(Color.RED);
            rField.setText("Please enter a reason");
            return false;
        }
        if (reasons[reasons.length-1] != null) {
            reasons = (String[]) expandCapacity(reasons);
        }
        for (int i = 0; i < reasons.length; i++) {
            if (reasons[i] == null) {
                reasons[i] = r;
                return true;
            }
        }
        return true;
    }

    /**
     * @author -LibbyDavis
     * @param n
     * @param nField
     * @returns Boolean
     * Makes sure note has been entered then sets note input in the notes array (expands array if necessary)
     */
    public boolean setNotes(String n, JTextField nField) {
        if (n.length() < 2) {
            nField.setForeground(Color.RED);
            nField.setText("Please enter a note");
            return false;
        }
        if (notes[notes.length-1] != null) {
            notes = (String[]) expandCapacity(notes);
        }
        for (int i = 0; i < notes.length; i++) {
            if (notes[i] == null) {
                notes[i] = n;
                return true;
            }
        }
        return true;
    }

    /**
     * @author -LibbyDavis
     * @param name
     * @param unit
     * @param uField
     * @param nField
     * @param vField
     * @param patient
     * @returns Boolean
     * Makes sure measurement value has been entered, makes new measurement with input and adds it to measurements array (expands if necessary)
     */
    public boolean setMeasurement(String name, String value, String unit, JTextField uField, JTextField nField, JTextField vField, MedicalPatient patient) {
        Measurements currentMeasurement = new Measurements(name, value, unit, uField, nField, vField);
        if (currentMeasurement.measurement == null) {
            return false;
        }
        if (measurements[measurements.length-1] != null) {
            measurements = (Measurements[]) expandCapacity(measurements);
        }

        for (int i = 0; i < measurements.length; i++) {
            if (measurements[i] == null) {
                measurements[i] = currentMeasurement;
                patient.setMeasurement(currentMeasurement);
                return true;
            }
        }
        return true;
    }

    /**
     * @author -LibbyDavis
     * @returns String[]
     * Returns string of all measurements in easy to read format
     */
    public String[] getMeasurementArrayToString() {
        String[] measurementsStrings = new String[measurements.length];
        for (int i = 0; i < measurements.length; i++) {
            if (measurements[i] != null) {
                measurementsStrings[i] = measurements[i].toString();
            }
            else {
                return measurementsStrings;
            }
        }
        return measurementsStrings;
    }

    public String[] getReasons() {
        return reasons;
    }

    public Measurements[] getMeasurements() {
        return measurements;
    }

    public String[] getNotes() {
        return notes;
    }
    
    public String getReasonsString() {
        String reasonsString = "";
        for (int i = 0; i < reasons.length; i++) {
            if (reasons[i] != null) {
                reasonsString += reasons[i];
                if (i < reasons.length - 1) {
                    reasonsString += ", ";
                }
            }
        }
        return reasonsString;
    }
    
    public String getNotesString() {
        String notesString = "";
        for (int i = 0; i < notes.length; i++) {
            if (notes[i] != null) {
                notesString += notes[i];
                if (i < notes.length - 1) {
                    notesString += ", ";
                }
            }
        }
        return notesString;
    }
   
    public String getMeasurementsString() {
        String measurementsString = "";
        String[] measureList = getMeasurementArrayToString();
        for (int i = 0; i < measureList.length; i++) {
            if (measureList[i] != null) {
                measurementsString += measureList[i];
                if (i < reasons.length - 1) {
                    measurementsString += ", ";
                }
            }
        }
        return measurementsString;
    }
    
    public void deleteReason(int index) {
        reasons[index] = null;
    }
    
    public void deleteMeasurement(int index) {
        measurements[index] = null;
    }
    
    public void deleteNote(int index) {
        notes[index] = null;
    }
    
    /**
     * @author -LibbyDavis
     * @param select
     * @returns ResultSet
     * gets appointment information from database 
     * (either all appointments in DB or all appointments for a patient based on an NHI identifier)
     */
    public static ResultSet getAppointmentHistory(String select) throws SQLException {
        DatabaseConnection DBconnect = new DatabaseConnection();
        Connection conn = DBconnect.getConnectionPatients();
        
        Statement statement1 = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        
        String query = "";
        if (select.equals("ALL")) {
            query = "SELECT * FROM ADMIN1.APPOINTMENT";
        }
        else {
            query = "SELECT * FROM ADMIN1.APPOINTMENT WHERE NHI='" + select + "'";
        }
        ResultSet rs = statement1.executeQuery(query);
        
        return rs;
    }
    
    /**
     * @author -LibbyDavis
     * @returns ArrayList
     * Creates an ArrayList from the appointment history of all patients to be used for created a JTable
     */
    public static ArrayList displayAppointmentHistorySummary() throws SQLException {
        ResultSet rs = getAppointmentHistory("ALL");
        HashMap<String, Integer> map = new HashMap();
        ArrayList<Object[]> list = new ArrayList();
        MedicalPatient currentPatient = new MedicalPatient();
        
        rs.beforeFirst();
        
        //get number of appointments
        while (rs.next()) {
            String nhi = rs.getString("NHI");
            if (map.containsKey(nhi)) {
                int num = map.get(nhi) + 1;
                map.put(nhi, num);
            }
            else {
                map.put(nhi, 1);
            }
            
        }
        
        //get arraylist of each unique person in appointment history
        for (String key : map.keySet()) {
            Object[] row = new Object[4];
            row[0] = key;
            currentPatient.getPatientFromDatabase(key, "NHI", null);
            row[1] = currentPatient.getfName();
            row[2] = currentPatient.getlName();
            row[3] = map.get(key);
            
            list.add(row);
            currentPatient = new MedicalPatient();
        }
        
        
        return list;
    }
    
    /**
     * @author -LibbyDavis
     * @param nhi
     * @returns ArrayList
     * Creates an ArrayList holding each appointment of a patient to be used to create a JTable
     */
    public static ArrayList displayAppointmentHistoryForPatient(String nhi) throws SQLException {
        ResultSet rs = getAppointmentHistory(nhi);
        
        rs.beforeFirst();
        ArrayList<Object[]> appointments = new ArrayList();
        Object[] singleAppointment;
        
        while (rs.next()) {
            singleAppointment = new Object[4];
            singleAppointment[0] = rs.getDate("DATETIME");
            singleAppointment[1] = rs.getString("REASONS");
            singleAppointment[2] = rs.getString("MEASUREMENTS");
            singleAppointment[3] = rs.getString("NOTES");
            appointments.add(singleAppointment);
        }
        rs.close();
        
        return appointments;
    }
    
}


