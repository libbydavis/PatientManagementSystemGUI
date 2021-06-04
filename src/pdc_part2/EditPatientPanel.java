package pdc_part2;

import java.io.IOException;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author libst
 */
public class EditPatientPanel extends JPanel{
    private PatientsController controller;
    private JButton searchButton;
    private BrowsePatientsPanel pickPatient;
    
    public EditPatientPanel(PatientManagementView frame, double width, double height, PatientsController controller) throws IOException, SQLException {
        this.controller = controller;
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
    
    public void setDelete(MedicalPatient patient, PatientManagementView frame) throws SQLException, IOException {
        String message = "Are you sure you want to delete patient " + patient.getfName() + " " + patient.getlName() + "?";
        int choice = Confirmation.createErrorMessage(message, "Delete Patient?", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            patient.deletePatientFromDB();
            frame.removeAll();
            frame.add(new MenuIconsPanel(frame));
            frame.revalidate();
        }
    }
}
