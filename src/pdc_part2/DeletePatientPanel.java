package pdc_part2;

import java.awt.Color;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * @author -LibbyDavis
 * Uses a BrowsePatientsPanel for user to select a patient,
 * then allows user to delete a patient from the database
 */

public class DeletePatientPanel extends JPanel{
    private PatientsController controller;
    private JButton searchButton;
    private BrowsePatientsPanel pickPatient;
    private DeletePatientPanel store;
    
    public DeletePatientPanel(PatientManagementView frame, double width, double height, PatientsController controller) throws IOException, SQLException {
        this.controller = controller;
        store = this;
        pickPatient = new BrowsePatientsPanel(frame, width, height, controller);
        searchButton = pickPatient.getSearchButton();
        
        add(pickPatient);
    }
    
    public JButton getSearchButton() {
        return searchButton;
    }
    
    public JButton getSelectButton() {
        return pickPatient.getSelectButton();
    }
    
    public String getSearchText() {
        return pickPatient.getSearchText();
    }
    
    public BrowsePatientsPanel getBrowsePanel() {
        return pickPatient;
    }
    
    public Object getSelected() {
        return pickPatient.getSearchOptions().getSelectedItem();
    }
    
    /**
     * @author -LibbyDavis
     * @param patient
     * @param frame
     * This checks if the user actually wants to delete a patient
     * Then calls deletePatientFromDB to delete patient
     * Then returns to home screen and shows confirmation
     */
    public void setDelete(MedicalPatient patient, PatientManagementView frame, PatientsPanel panel) throws SQLException, IOException {
        String message = "Are you sure you want to delete patient " + patient.getfName() + " " + patient.getlName() + "?";
        int choice = Confirmation.createErrorMessage(message, "Delete Patient?", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            patient.deletePatientFromDB();
            
            JPanel confirmation = Confirmation.createConfirmation("Patient Deleted", frame, Color.RED);
            Timer t = new Timer();
                t.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Confirmation.removeConfirmation(frame, confirmation);
                    }
                }, 3000);
                
            panel.remove(store);
            frame.remove(panel);
            frame.add(new MenuIconsPanel(frame));    
            frame.revalidate();
        }
    }
}
