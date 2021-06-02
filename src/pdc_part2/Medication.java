package pdc_part2;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Raj
 */
public class Medication {

    private String name;
    private String sideEffects;
    private String conditions;

    public Medication(String name, String sideEffects, String conditions) {
        this.name = name;
        this.sideEffects = sideEffects;
        this.conditions = conditions;
    }

    public String getName() {
        return name;
    }

    public String getSideEffects() {
        return sideEffects;
    }

    public String getConditions() {
        return conditions;
    }

    public static String[] medList() 
    {
        String[] medList = new String[8];
        try {
            DatabaseConnection dbc = new DatabaseConnection();
            String sqlQuery = "SELECT MEDNAME FROM MEDICATION";
            PreparedStatement prepstmt = dbc.getConnectionMedication().prepareStatement(sqlQuery);
            ResultSet rs = prepstmt.executeQuery();
            int i = 1;
            medList[0] = "select an option";

            while (rs.next()) {
                medList[i] = rs.getString("MEDNAME").toString();
                i++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddPatientsView.class.getName()).log(Level.SEVERE, null, ex);
        }

        return medList;
    }

    public String toString() {
        return "Medication Name: " + name + "\nSide Effects: " + sideEffects + "\nConditions: " + conditions;
    }
}
