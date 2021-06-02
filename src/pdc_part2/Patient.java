package pdc_part2;

import java.sql.SQLException;

/**
 *
 * @author libst
 */
public abstract class Patient {
    
    public abstract void getPatientFromDatabase(String input, Object option, Object matchingPanel) throws SQLException;
    
    public abstract void saveAppointmentToDB(Appointment a) throws SQLException;
}
