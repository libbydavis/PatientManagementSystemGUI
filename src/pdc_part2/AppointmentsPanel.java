/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdc_part2;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppointmentsPanel extends JPanel {
    AppointmentsPanel panel = this;
    Patient patient1;
    JLabel titleAP;
    private AppointmentsController controller;
    private JButton finishAppointment;
    private JButton backButton;
    
    public AppointmentsPanel(PatientManagementView frame, int width, int height) throws IOException, SQLException {
        patient1 = new Patient();
        controller = new AppointmentsController(frame, this);
        
        setLayout(new GridBagLayout());
        setBackground(new Color(18, 29, 94));
        GridBagConstraints c = new GridBagConstraints();
        BufferedImage backImage = ImageIO.read(new File("src\\Images\\backButtonArrow.png"));
        backButton = new JButton(new ImageIcon(backImage));
        backButton.setBorder(new EmptyBorder(30, 30, 10, 10));
        backButton.setContentAreaFilled(false);
        backButton.addActionListener(controller);

        finishAppointment = new JButton("Save and Exit");
        finishAppointment.addActionListener(controller);

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        add(backButton, c);

        c.anchor = GridBagConstraints.CENTER;
        titleAP = new JLabel("Appointment" + patient1.getfName() + " " + patient1.getlName());
        titleAP.setFont(new Font("Arial", Font.BOLD, 40));
        titleAP.setForeground(Color.WHITE);
        add(titleAP, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        AppointmentsForm aForm = new AppointmentsForm(width, height, controller);
        aForm.setMinimumSize(new Dimension(width, (height - 400)));
        add(aForm, c);

        c.gridx = 0;
        c.gridy = 2;
        c.fill = GridBagConstraints.FIRST_LINE_START;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.insets = new Insets(10, 10, 0,0);
        add (finishAppointment, c);


    }
    
    public JButton getFinishAppointment() {
        return finishAppointment;
    }
    
    public JButton getBackButton() {
        return backButton;
    }
    
    public void setPatient(Patient p) {
        patient1 = p;
        controller.setNHI(p.getNHI());
    }
    
    public Patient getPatient(JLabel title) {
        getPatientPopup patientFrame = new getPatientPopup(title);
        patientFrame.setVisible(true);
        Patient p1 = patientFrame.getPatientObj();
        return p1;
    }

    private class getPatientPopup extends JFrame {
        Patient p1;
        
        public getPatientPopup(JLabel title) {
            super("Patient");
            setSize(500, 300);
            setLocation(200, 200);
            setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();

            JLabel patientLabel = new JLabel("Search Patient By ");
            String[] options = {"NHI", "First Name", "Last Name"};
            JComboBox searchOptions = new JComboBox(options);
            JTextField patientField = new JTextField();
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
            JButton findPatient = new JButton("Find Patient");
            findPatient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputString = patientField.getText();
                if ((inputString.length() == 6 && searchOptions.getSelectedItem().equals("NHI")) || (inputString.length() > 2 && searchOptions.getSelectedItem().equals("First Name")) || (inputString.length() > 2 && searchOptions.getSelectedItem().equals("Last Name"))) {
                    try {
                        p1 = new Patient();
                    } catch (SQLException ex) {
                        Logger.getLogger(AppointmentsPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        p1.getPatientFromDatabase(inputString, searchOptions.getSelectedItem());
                        if (p1.getNHI().length() == 6) {
                            setVisible(false);
                            title.setText("Appointment - " + p1.getfName() + " " + p1.getlName());
                            setPatient(p1);
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
        
        public Patient getPatientObj() {
            return p1;
        }
    }
    
}



