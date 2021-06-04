/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdc_part2;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Raj
 */
public class CreatePrescriptionController 
{
    Prescription objPresc = new Prescription();
    
    public CreatePrescriptionController() {
    }
    
    public String makeTimeDate()
    {
        Date currentTimeDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy h:mm aa");
        objPresc.setDateTime(sdf.format(currentTimeDate));
        return sdf.format(currentTimeDate);
    }
    
    /**
        * Sets the name of the patient by searching for their name using the nhi in the database
        * @param nhi what's used to search for the patient
        **/
    public void setPatName(String nhi) {
        try {
            DatabaseConnection dbc = new DatabaseConnection();
            PreparedStatement prepstmt2 = dbc.getConnectionPatients().prepareStatement("SELECT FIRSTNAME, LASTNAME FROM PATIENTS WHERE NHI = \'" + nhi + "\'");
            ResultSet rs2 = prepstmt2.executeQuery();

            while (rs2.next()) {
                String patFName = rs2.getString("FIRSTNAME");
                String patLName = rs2.getString("LASTNAME");
                objPresc.setPatientName(patFName.concat(" " + patLName));
            }
        } catch (SQLException ex) {
            Logger.getLogger(CreatePrescriptionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
        * Connects to the medication database to search for the medication using the string that's entered
        * @param meds Sets the patient's medication using this string
        **/
    public void setPatMeds(String meds)
    {
        try {
            DatabaseConnection dbc = new DatabaseConnection();
            PreparedStatement prepstmt = dbc.getConnectionMedication().prepareStatement("SELECT MEDNAME, SIDE_EFFECTS, CONDITIONS FROM MEDICATION WHERE MEDNAME = \'" + meds + "\'");
            ResultSet rs = prepstmt.executeQuery();
            
            while (rs.next()) {
                String medName = rs.getString(1);
                String medSideEff = rs.getString(2);
                String medConditions = rs.getString(3);
                objPresc.setMeds(new Medication(medName, medSideEff, medConditions));
            }
            prepstmt.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(CreatePrescriptionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean checkNullString(String mnp) {
        int enteredNameLength = mnp.length();
        return ((enteredNameLength == 0) ? true : false);
    }
    
    public boolean isBoolean(String repMeds) {
        if (checkNullString(repMeds)) {
            return false;
        } else if (repMeds.toLowerCase().toCharArray()[0] == 't') {
            objPresc.setRepeat(true);
            return true;
        } else if (repMeds.toLowerCase().toCharArray()[0] == 'f') {
            objPresc.setRepeat(false);
            return true;
        } else {
            return false;
        }
    }
}
