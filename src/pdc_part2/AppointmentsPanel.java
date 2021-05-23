/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdc_part2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author libst
 */
public class AppointmentsPanel extends JPanel{
    AppointmentsPanel panel = this;
    private JLabel titleAP;
    Patient patient1;
    private int width;
    private int height;
    private AppointmentsController controller;
    private ButtonsPane buttonsPane;
    private GridBagConstraints c;
    private JButton backButton;
    private JButton finishAppointment;
    Color dBlue = new Color(18, 29, 94);
    private JButton createAppointmentB;
    private JButton viewHistoryB;
   
    
    public AppointmentsPanel(PatientManagementView frame, int width, int height) throws IOException, SQLException {
        setLayout(new GridBagLayout());
        setBackground(new Color(18, 29, 94));
        c = new GridBagConstraints();
        
        this.width = width;
        this.height = height;
        this.controller = new AppointmentsController(frame, this);
        patient1 = new Patient();
        
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        Dimension preferredD = new Dimension(screenSize.width/2, screenSize.height/2);
        
        BufferedImage backImage = ImageIO.read(new File("src\\Images\\backButtonArrow.png"));
        backButton = new JButton(new ImageIcon(backImage));
        backButton.setBorder(new EmptyBorder(30, 30, 10, 10));
        backButton.setContentAreaFilled(false);
        backButton.addActionListener(controller);

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
        buttonsPane = new ButtonsPane(frame, preferredD);
        add(buttonsPane, c);
    }
    
    public JButton getFinishAppointment() {
        return finishAppointment;
    }
    
    public JButton getCreateAppointmentB() {
        return createAppointmentB;
    }

    public JLabel getTitleAP() {
        return titleAP;
    }
    
    public void setTitleAP(String text) {
        titleAP.setText(text);
    }

    public Patient getPatient1() {
        return patient1;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public JButton getViewHistoryB() {
        return viewHistoryB;
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
    
    private class ButtonsPane extends JPanel{
        public ButtonsPane(PatientManagementView frame, Dimension d) {
         //set layout
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        GridBagConstraints c = new GridBagConstraints();
        this.setPreferredSize(d);
        this.setMinimumSize(d);
        
        Font buttonFont = new Font("Arial", Font.BOLD, 25);

        c.gridx = 0;
        c.weightx = 0.5;
        c.gridwidth = 2;
        c.gridy = 2;
        createAppointmentB = new JButton("Create Appointment");
        createAppointmentB.setBackground(dBlue);
        createAppointmentB.setForeground(Color.WHITE);
        createAppointmentB.setBorderPainted(false);
        createAppointmentB.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        createAppointmentB.setFont(buttonFont);
        createAppointmentB.addActionListener(controller);
        add(createAppointmentB, c);
        
        c.gridx = 2;
        viewHistoryB = new JButton("View Appointment History");
        viewHistoryB.setBackground(dBlue);
        viewHistoryB.setForeground(Color.WHITE);
        viewHistoryB.setBorderPainted(false);
        viewHistoryB.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        viewHistoryB.setFont(buttonFont);
        viewHistoryB.addActionListener(controller);
        add(viewHistoryB, c);
        }
    }
    
    public void removeButtons() {
        remove(buttonsPane);
    }
    
    public void addComponentAppointments(JComponent pane) {
        add(pane, c);
    }
    
    public void setConstraintsSaveButton() {
        Font font = new Font("Arial", Font.BOLD, 16);
        c.gridx = 0;
        c.gridy = 2;
        c.fill = GridBagConstraints.FIRST_LINE_END;
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        c.insets = new Insets(0, 0, 0, 15);
        finishAppointment = new JButton("Save and Exit");
        finishAppointment.setBackground(Color.WHITE);
        finishAppointment.setForeground(dBlue);
        finishAppointment.setBorderPainted(false);
        finishAppointment.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        finishAppointment.setFont(font);
        finishAppointment.addActionListener(controller);
    }
}



