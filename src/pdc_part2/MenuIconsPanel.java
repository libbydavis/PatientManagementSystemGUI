package pdc_part2;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MenuIconsPanel extends JPanel {
    MenuIconsPanel store = this;
    public MenuIconsPanel(PatientManagementView frame) throws IOException {
        setLayout(new GridLayout(0, 5));
        //see patients
        BufferedImage patientsIcon = ImageIO.read(new File("C:\\Users\\libst\\IdeaProjects\\TestingPart2Code\\src\\iconPDCPatientsSmall.png"));
        JButton patientsButton = new JButton(new ImageIcon(patientsIcon));
        patientsButton.setBorder(BorderFactory.createEmptyBorder());
        patientsButton.setContentAreaFilled(false);
        patientsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object source = e.getSource();
                PatientsPanel patientsPanel = new PatientsPanel(frame, getWidth(), getHeight());
                frame.remove(store);
                frame.add(patientsPanel);
                frame.revalidate();

            }
        });
        add(patientsButton);
        //appointments
        BufferedImage appointmentsIcon = ImageIO.read(new File("C:\\Users\\libst\\IdeaProjects\\TestingPart2Code\\src\\iconPDCAppointmentsSmall.png"));
        JButton appointmentsButton = new JButton(new ImageIcon(appointmentsIcon));
        appointmentsButton.setBorder(BorderFactory.createEmptyBorder());
        appointmentsButton.setContentAreaFilled(false);
        appointmentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object source = e.getSource();
                AppointmentsPanel appointmentsPanel = new AppointmentsPanel(frame);
                frame.remove(store);
                frame.add(appointmentsPanel);
                frame.revalidate();
            }
        });
        add(appointmentsButton);
        //prescription
        BufferedImage prescriptionIcon = ImageIO.read(new File("C:\\Users\\libst\\IdeaProjects\\TestingPart2Code\\src\\iconPDCPrescriptionSmall.png"));
        JButton prescriptionButton = new JButton(new ImageIcon(prescriptionIcon));
        prescriptionButton.setBorder(BorderFactory.createEmptyBorder());
        prescriptionButton.setContentAreaFilled(false);
        prescriptionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object source = e.getSource();
                PrescriptionPanel prescriptionPanel = new PrescriptionPanel(frame);
                frame.remove(store);
                frame.add(prescriptionPanel);
                frame.revalidate();
            }
        });
        add(prescriptionButton);
    }
}
