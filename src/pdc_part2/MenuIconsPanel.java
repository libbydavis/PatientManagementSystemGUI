package pdc_part2;

import pdc_part2.PatientManagementView;
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
        BufferedImage patientsIcon = ImageIO.read(new File("src\\Images\\iconPDCPatientsSmall.png"));
        JButton patientsButton = new JButton(new ImageIcon(patientsIcon));
        patientsButton.setBorder(BorderFactory.createEmptyBorder());
        patientsButton.setContentAreaFilled(false);
        patientsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object source = e.getSource();
                PatientsPanel patientsPanel = new PatientsPanel(getWidth(), getHeight());
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
        add(appointmentsButton);
        //prescription
        BufferedImage prescriptionIcon = ImageIO.read(new File("src\\Images\\iconPDCPrescriptionSmall.png"));
        JButton prescriptionButton = new JButton(new ImageIcon(prescriptionIcon));
        prescriptionButton.setBorder(BorderFactory.createEmptyBorder());
        prescriptionButton.setContentAreaFilled(false);
        add(prescriptionButton);
    }
}
