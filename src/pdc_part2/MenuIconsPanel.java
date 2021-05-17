package pdc_part2;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MenuIconsPanel extends JPanel {
    MenuIconsPanel store = this;
    public MenuIconsPanel(PatientManagementView frame) throws IOException, SQLException {
        setLayout(new GridLayout(0, 5));
        //see patients
        BufferedImage patientsIcon = ImageIO.read(new File("src\\Images\\iconPDCPatientsSmall.png"));
        JButton patientsButton = new JButton(new ImageIcon(patientsIcon));
        patientsButton.setBorder(BorderFactory.createEmptyBorder());
        patientsButton.setContentAreaFilled(false);
        patientsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object source = e.getSource();
                PatientsPanel patientsPanel = null;
                try {
                    patientsPanel = new PatientsPanel(frame, getWidth(), getHeight());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (SQLException ex) {
                    Logger.getLogger(MenuIconsPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                frame.remove(store);
                frame.add(patientsPanel);
                frame.revalidate();

            }
        });
        add(patientsButton);
        //appointments
        BufferedImage appointmentsIcon = ImageIO.read(new File("src\\Images\\iconPDCAppointmentsSmall.png"));
        JButton appointmentsButton = new JButton(new ImageIcon(appointmentsIcon));
        appointmentsButton.setBorder(BorderFactory.createEmptyBorder());
        appointmentsButton.setContentAreaFilled(false);
        appointmentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(store);
                AppointmentButtonsPanel appointmentButtons;
                try {
                    appointmentButtons = new AppointmentButtonsPanel(frame);
                    frame.add(appointmentButtons);
                    frame.revalidate();
                } catch (IOException ex) {
                    Logger.getLogger(MenuIconsPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });
        add(appointmentsButton);
        //prescription
        BufferedImage prescriptionIcon = ImageIO.read(new File("src\\Images\\iconPDCPrescriptionSmall.png"));
        JButton prescriptionButton = new JButton(new ImageIcon(prescriptionIcon));
        prescriptionButton.setBorder(BorderFactory.createEmptyBorder());
        prescriptionButton.setContentAreaFilled(false);
        prescriptionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object source = e.getSource();
                PrescriptionPanel prescriptionPanel = null;
                try {
                    prescriptionPanel = new PrescriptionPanel(frame, (int) frame.getWidth(), (int) frame.getHeight());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                frame.remove(store);
                frame.add(prescriptionPanel);
                frame.revalidate();
            }
        });
        add(prescriptionButton);
    }
}


