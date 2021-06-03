package pdc_part2;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author libst
 */
public class getPatientPopUp extends JDialog {
        MedicalPatient p1;
        private JTextField patientField;
        getPatientPopUp store = this;
        private JButton findPatient;
        private JPanel changePanel;
        private JLabel title;
        
        /**
        * @author -LibbyDavis
        * @param title
        * @param appointmentPanel
        * This is a JDialog popup that gets user to select a patient
        * The user can't continue without interacting with the popup
        */ 
        public getPatientPopUp(JLabel title, JPanel changePanel) {
            setTitle("Select a Patient For The Appointment");
            setModal(true);
            setSize(500, 300);
            setLocation(200, 200);
            setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            
            this.changePanel = changePanel;
            this.title = title;

            JLabel patientLabel = new JLabel("Search Patient By ");
            String[] options = {"NHI", "First Name", "Last Name"};
            JComboBox searchOptions = new JComboBox(options);
            patientField = new JTextField();
            patientField.addFocusListener(new FocusListener(){
                @Override
                public void focusGained(FocusEvent e){
                    patientField.setForeground(Color.BLACK);
                    patientField.setText("");
                }
                @Override
                public void focusLost(FocusEvent e) {

                }
            });
            findPatient = new JButton("Find Patient");
            findPatient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputString = patientField.getText();
                if ((inputString.length() == 6 && searchOptions.getSelectedItem().equals("NHI")) || (inputString.length() > 2 && searchOptions.getSelectedItem().equals("First Name")) || (inputString.length() > 2 && searchOptions.getSelectedItem().equals("Last Name"))) {
                    try {
                        p1 = new MedicalPatient();
                    } catch (SQLException ex) {
                        Logger.getLogger(AppointmentsPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        p1.getPatientFromDatabase(inputString, searchOptions.getSelectedItem(), store);
                        if (p1.getNHI().length() == 6) {
                            setAndClose();
                        }
                        else {
                            patientField.setText("Patient Not Found. Try Again.");
                            patientField.setForeground(Color.RED);
                        }
                        
                    } catch (SQLException ex) {
                        Logger.getLogger(AppointmentsPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else {
                    patientField.setText("Enter a 6 character code (3 letters, 3 numbers)");
                    patientField.setForeground(Color.RED);
                }
            }
            });

            c.gridx = 0;
            c.gridy = 0;
            c.weightx = 0.2;
            c.fill = GridBagConstraints.HORIZONTAL;
            c.anchor = GridBagConstraints.FIRST_LINE_START;
            c.insets = new Insets(0, 15, 0, 0);
            add(patientLabel, c);
            c.gridx = 1;
            c.insets = new Insets(0, 0, 0, 0);
            c.fill = GridBagConstraints.FIRST_LINE_START;
            add(searchOptions, c);
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 2;
            c.weightx = 1;
            c.insets = new Insets(0, 0, 0, 15);
            add(patientField, c);
            c.gridx = 2;
            c.gridy = 2;
            c.weightx = 1;
            c.insets = new Insets(10, 0, 0, 15);
            c.fill = GridBagConstraints.PAGE_END;
            c.anchor = GridBagConstraints.EAST;
            add(findPatient, c);
        }
        
        public MedicalPatient getPatientObj() {
            return p1;
        }
        
        /**
        * @author -LibbyDavis
        * @param comboBox
        * This resets the JDialog window to let user select a patient from a list of matching patients
        * This method is used when there are multiple patients with the same name
        */                         
        public void setPatientPicker(JComboBox comboBox) {
            GridBagConstraints c = new GridBagConstraints();
            remove(patientField);
            remove(findPatient);
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 2;
            c.weightx = 1;
            c.gridy = 0;
            c.insets = new Insets(0, 0, 0, 15);
            add(comboBox, c);
            
            c.gridx = 2;
            c.gridy = 2;
            c.weightx = 1;
            c.insets = new Insets(10, 0, 0, 15);
            c.fill = GridBagConstraints.PAGE_END;
            c.anchor = GridBagConstraints.EAST;
            JButton select = new JButton("Select Patient");
            select.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    p1 = (MedicalPatient) comboBox.getSelectedItem();
                    setAndClose();
                }
            });
            add(select, c);
            revalidate();
        }
        
        /**
        * @author -LibbyDavis
        * Set appointment title to have title with patient's name and close JDialog window
        */
        public void setAndClose() {
            if (p1.getNHI().length() == 6) {
                setVisible(false);
                if (changePanel instanceof AppointmentsPanel) {
                    title.setText("Appointment - " + p1.getfName() + " " + p1.getlName());
                    AppointmentsPanel appPanel = (AppointmentsPanel) changePanel;
                    appPanel.setPatient(p1);
                }
                else if (changePanel instanceof CreatePrescriptionPanel) {
                    title.setText("Patient Name: " + p1.getfName() + " " + p1.getlName());
                    CreatePrescriptionPanel createpPanel = (CreatePrescriptionPanel) changePanel;
                    createpPanel.setPatFName(p1.getfName());
                    createpPanel.setPatLName(p1.getlName());
                    createpPanel.setPrescNHI(p1.getNHI());
                }
            }
        }
    }
