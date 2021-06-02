package pdc_part2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author libst
 */
public class PatientsController implements ActionListener{
    PatientsPanel panel;
    PatientManagementView frame;
    PatientsController control = this;
    private BrowsePatientsPanel browsePanel;
    private EditPatientPanel editPatientPanel;
    private MedicalPatient findPatient;
    
    public PatientsController(PatientsPanel panel, PatientManagementView frame) throws IOException, SQLException {
        this.panel = panel;
        this.frame = frame;
        browsePanel = new BrowsePatientsPanel(frame, frame.getWidth(), frame.getHeight(), control);
        editPatientPanel = new EditPatientPanel(frame, frame.getWidth(), frame.getHeight(), control);
        findPatient = new MedicalPatient();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        
        if (source == panel.getBackButton()) {
            try {
                MenuIconsPanel menuIconsPanel = new MenuIconsPanel(frame);
                frame.remove(panel);
                frame.add(menuIconsPanel);
                frame.revalidate();
            } catch (IOException ex) {
                Logger.getLogger(PatientsController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(PatientsController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        else if (source == panel.getAddPatientB()) 
        {
            AddPatientsView addPanel;
            try 
            {
                addPanel = new AddPatientsView(frame, panel, frame.getWidth(), frame.getHeight());
                panel.remove(panel.getButtonsPane1());
                panel.add(addPanel, panel.getConstraints());
                frame.revalidate();
            } 
            catch (IOException ex) 
            {
                Logger.getLogger(PatientsPanel.class.getName()).log(Level.SEVERE, null, ex);
            } 
            catch (SQLException ex) 
            {
                Logger.getLogger(PatientsPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if (source == panel.getEditPatientB()) {
            panel.remove(panel.getButtonsPane1());
            panel.add(editPatientPanel, panel.getConstraints());
            panel.setPatientsLabel("Edit Patient");
            frame.revalidate();
        }
        else if (source == panel.getBrowsePatientsB()) {
            panel.remove(panel.getButtonsPane1());
            panel.add(browsePanel, panel.getConstraints());
            panel.setPatientsLabel("Browse Patients");
            frame.revalidate();
       
        }
        
        else if (source == browsePanel.getSearchButton()) {
            try {
                String searchText = browsePanel.getSearchText();
                findPatient.getPatientFromDatabase(searchText, browsePanel.getSearchOptions().getSelectedItem(), browsePanel);
                if (findPatient.getNHI().length() > 0) {
                    browsePanel.displayIndividualPatient(findPatient);
                }
            } catch (SQLException ex) {
                Logger.getLogger(PatientsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        else if (source == editPatientPanel.getSearchButton()) {
            String searchText = editPatientPanel.getSearchText();
            try {
                findPatient.getPatientFromDatabase(searchText, editPatientPanel.getSelected(), editPatientPanel.getBrowsePanel());
                if (findPatient.getNHI().length() > 0) {
                    editPatientPanel.setEditor(findPatient);
                }
            } catch (SQLException ex) {
                Logger.getLogger(PatientsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        else if (source == browsePanel.getSelectButton()) {
            int row = findPatient.getMatchingPatientTable().getSelectedRow();
            String nhi = findPatient.getMatchingPatientTable().getModel().getValueAt(row, 0).toString();
            try {
                findPatient.getPatientFromDatabase(nhi, "NHI", browsePanel);
            } catch (SQLException ex) {
                Logger.getLogger(PatientsController.class.getName()).log(Level.SEVERE, null, ex);
            }
            browsePanel.displayIndividualPatient(findPatient);
        }
        
        else if (source == editPatientPanel.getSelectButton()) {
            int row = findPatient.getMatchingPatientTable().getSelectedRow();
            String nhi = findPatient.getMatchingPatientTable().getModel().getValueAt(row, 0).toString();
            try {
                findPatient.getPatientFromDatabase(nhi, "NHI", editPatientPanel.getBrowsePanel());
            } catch (SQLException ex) {
                Logger.getLogger(PatientsController.class.getName()).log(Level.SEVERE, null, ex);
            }
            editPatientPanel.setEditor(findPatient);
        }
    }
    
}
