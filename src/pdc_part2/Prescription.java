package pdc_part2;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class Prescription 
{
    private String dateTime;
    private Medication meds;
    private Dosage dosage;
    private String doctorName;
    private String patientName;
    private Boolean repeat;

    public Prescription(String dateTime, Medication meds, Dosage dosage, String doctorName, String patientName, Boolean repeat) 
    {
        this.dateTime = dateTime;
        this.meds = meds;
        this.dosage = dosage;
        this.doctorName = doctorName;
        this.patientName = patientName;
        this.repeat = repeat;
    }

    public Prescription() 
    {
        
    }
    
    public String getDateTime() 
    {
        return dateTime;
    }

    public void setDateTime(String dateTime) 
    {
        this.dateTime = dateTime;
    }

    public Medication getMeds() 
    {
        return meds;
    }

    public void setMeds(Medication meds) 
    {
        this.meds = meds;
    }

    public Dosage getDosage() 
    {
        return dosage;
    }

    public void setDosage(Dosage dosage) 
    {
        this.dosage = dosage;
    }

    public String getDoctorName() 
    {
        return doctorName;
    }

    public void setDoctorName(String doctorName) 
    {
        this.doctorName = doctorName;
    }

    public String getPatientName() 
    {
        return patientName;
    }

    public void setPatientName(String patientName) 
    {
        this.patientName = patientName;
    }

    public Boolean getRepeat() 
    {
        return repeat;
    }

    public void setRepeat(Boolean repeat) 
    {
        this.repeat = repeat;
    }
    
    public ResultSet getPrescriptions(String nhi) throws SQLException {
        DatabaseConnection dbc = new DatabaseConnection();
        Statement stmt = dbc.getConnectionPatients().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = stmt.executeQuery("SELECT PRESCRIPTIONNO, PRESCRIPTION_DETAILS FROM PRESCRIPTIONS WHERE NHI = \'" + nhi + "\'");
        return rs;
    }
    
    public void removePrescription(int intPrescNo) throws SQLException {
        DatabaseConnection dbc = new DatabaseConnection();
        String sqlQuery = "DELETE FROM PRESCRIPTIONS WHERE PRESCRIPTIONNO = " + intPrescNo;
        PreparedStatement prepstmt = dbc.getConnectionPatients().prepareStatement(sqlQuery);
        prepstmt.executeUpdate();
    }
    
        public void insertPrescToDatabase(Prescription presc, String patNhi) throws SQLException
    {
        String originalPresc = presc.toString();
        String cleanPresc = "";
        Scanner scan = new Scanner(originalPresc);
        scan.useDelimiter("\\'\\'*");
        
        while (scan.hasNext()) {
            cleanPresc += scan.next();
        }

        DatabaseConnection dbc = new DatabaseConnection();
        String sqlQuery = "SELECT PRESCRIPTIONNO FROM PRESCRIPTIONS WHERE NHI = \'" + patNhi + "\'";

        PreparedStatement prepstmt = dbc.getConnectionPatients().prepareStatement(sqlQuery);
        ResultSet rs = prepstmt.executeQuery();
        int rsPrescNo = 1;

        while (rs.next()) {
            rsPrescNo = rs.getInt(1);

            if (rsPrescNo == 0) {
                rsPrescNo = 1;
            }
            rsPrescNo++;
        }
        
        sqlQuery = "INSERT INTO ADMIN1.PRESCRIPTIONS (NHI, PRESCRIPTIONNO, PRESCRIPTION_DETAILS) VALUES (" + "\'" + patNhi + "\'," + rsPrescNo + ",\'" + cleanPresc + "\')";
        prepstmt = dbc.getConnectionPatients().prepareStatement(sqlQuery);
        int i = prepstmt.executeUpdate();
        
    }

    @Override
    public String toString() 
    {
        return "Date and Time:" + dateTime + "Doctors. Name: " + doctorName + "Patients Name: " + patientName + "Medication: " + meds + "Dosage: " + dosage + "Repeat: " + repeat;
    } 
}