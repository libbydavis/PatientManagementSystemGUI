package pdc_part2;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Prescription 
{
    private String dateTime;
    private Medication meds;
    private String doctorName;
    private String patientName;
    private Boolean repeat;

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
 
    public Prescription() 
    {	
        this.dateTime = dateTime;
        this.meds = meds;
        this.doctorName = doctorName;
        this.patientName = patientName;
        this.repeat = repeat;
    }
}