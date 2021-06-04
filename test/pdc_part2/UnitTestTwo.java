package pdc_part2;

import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Raj
 */
public class UnitTestTwo {

    @Test
    public void testNhiList() throws SQLException {
        ArrayList<String> currPatientNhis = MedicalPatient.paitentNHIList();
        assertEquals(currPatientNhis, MedicalPatient.paitentNHIList());
    }

    @Test
    public void testMedList() {
        String[] testMedList = new String[]{"select an option", "Ibuprofen", "Panadol", "Vicodin", "Oxycontin", "Percocet", "Valium", "Xanax"};
        assertArrayEquals(testMedList, Medication.medList());
    }



}
