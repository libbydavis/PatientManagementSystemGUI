package pdc_part2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BrowsePatientsPanel extends JPanel {
    BrowsePatientsPanel panel = this;
    private PatientsController controller;
    private JButton searchButton;
    private JTextField searchBox;
    private JComponent patientTable;
    private GridBagConstraints c;
    private Dimension d;
    private JComboBox searchOptions;
    private JButton selectButton;
    
    public BrowsePatientsPanel(PatientManagementView frame, double width, double height, PatientsController controller) throws IOException, SQLException {
        setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        d = new Dimension((int) width, (int) height);
        setSize(d);
        setMinimumSize(d);
        this.controller = controller;
        
        String[] options = {"NHI", "First Name", "Last Name"};
        searchOptions = new JComboBox(options);
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(30, 0, 20, 40);
        add(searchOptions, c);
        
        c.gridx = 1;
        c.insets = new Insets(30, 0, 20, 20);
        searchBox = new JTextField("enter NHI, patient name or DOB...");
        searchBox.addFocusListener(new FocusListener(){
            @Override
            public void focusGained(FocusEvent e){
                searchBox.setForeground(Color.BLACK);
                searchBox.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });
        add(searchBox, c);
        
        c.gridx = 2;
        searchButton = new JButton("search");
        searchButton.addActionListener(controller);
        add(searchButton, c);

         c.gridx = 0;
         c.gridy = 1;
         c.gridwidth = 3;
         c.fill = GridBagConstraints.HORIZONTAL;
        MedicalPatient allPatients = new MedicalPatient();
        patientTable = allPatients.displayAllPatients(null);
        add(patientTable, c);
        
    }
    
    public void setPatientTable(JComponent table) {
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.insets = new Insets(10, 10, 0, 10);
        c.fill = GridBagConstraints.HORIZONTAL;
        JComponent matchingTable = table;
        add(matchingTable, c);
        selectButton = new JButton("Select Patient");
        selectButton.addActionListener(controller);
        c.gridx = 1;
        add(selectButton, c);
        remove(searchBox);
        remove(searchOptions);
        remove(searchButton);
        remove(patientTable);
        revalidate();
    }
    
    public JButton getSearchButton() {
        return searchButton;
    }
    
    public JComboBox getSearchOptions() {
        return searchOptions;
    }
    
    public String getSearchText() {
        return searchBox.getText();
    }
    
    public JButton getSelectButton() {
        return selectButton;
    }
    
    /**
     * @author -LibbyDavis
     * @param patient
     * Gets a panel displaying details of a patient and adds it to the main window
     */
    public void displayIndividualPatient(MedicalPatient patient) {
        JComponent patientDetails = patient.displayIndividualPatientDetails();
        removeAll();
        add(patientDetails, c);
        revalidate();
    }
}
