package pdc_part2;

import java.io.IOException;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author libst
 */
public class EditPatientPanel extends JPanel{
    private PatientsController controller;
    private JButton searchButton;
    private JButton selectButton;
    private BrowsePatientsPanel pickPatient;
    private EditorPanel editPanel;
    
    public EditPatientPanel(PatientManagementView frame, double width, double height, PatientsController controller) throws IOException, SQLException {
        this.controller = controller;
        pickPatient = new BrowsePatientsPanel(frame, width, height, controller);
        searchButton = pickPatient.getSearchButton();
        selectButton = pickPatient.getSelectButton();
        
        add(pickPatient);
    }
    
    public JButton getSearchButton() {
        return searchButton;
    }
    
    public JButton getSelectButton() {
        return selectButton;
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
    
    public void setEditor(MedicalPatient patient) {
        remove(pickPatient);
        editPanel = new EditorPanel(patient);
        add(editPanel);
        revalidate();
    }
    
    public class EditorPanel extends JPanel {
        
        public EditorPanel(MedicalPatient patient) {
            JLabel patientName = new JLabel(patient.getfName() + " " + patient.getlName());
            add(patientName);
        }
    }
}
