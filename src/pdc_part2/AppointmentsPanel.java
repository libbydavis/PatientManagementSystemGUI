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
        getPatientPopUp patientFrame = new getPatientPopUp(title, this);
        patientFrame.setVisible(true);
        Patient p1 = patientFrame.getPatientObj();
        return p1;
    }

   
    
}



