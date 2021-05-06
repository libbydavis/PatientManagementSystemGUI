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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class AppointmentsPanel extends JPanel {
    AppointmentsPanel panel = this;
    public AppointmentsPanel(PatientManagementView frame, int width, int height) throws IOException {
        setLayout(new GridBagLayout());
        setBackground(new Color(18, 29, 94));
        GridBagConstraints c = new GridBagConstraints();
        BufferedImage backImage = ImageIO.read(new File("src\\Images\\backButtonArrow.png"));
        JButton backButton = new JButton(new ImageIcon(backImage));
        backButton.setBorder(new EmptyBorder(30, 30, 10, 10));
        backButton.setContentAreaFilled(false);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object source = e.getSource();
                try {
                    MenuIconsPanel menuIconsPanel = new MenuIconsPanel(frame);
                    frame.remove(panel);
                    frame.add(menuIconsPanel);
                    frame.revalidate();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        JButton finishAppointment = new JButton("Save and Exit");
        finishAppointment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    MenuIconsPanel menuIconsPanel = new MenuIconsPanel(frame);
                    frame.remove(panel);
                    frame.add(menuIconsPanel);
                    frame.revalidate();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        add(backButton, c);

        c.anchor = GridBagConstraints.CENTER;
        JLabel title = new JLabel("Appointment");
        title.setFont(new Font("Arial", Font.BOLD, 40));
        title.setForeground(Color.WHITE);
        add(title, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        AppointmentsForm aForm = new AppointmentsForm(width, height);
        aForm.setMinimumSize(new Dimension(width, (height - 400)));
        add(aForm, c);

        c.gridx = 0;
        c.gridy = 2;
        c.fill = GridBagConstraints.FIRST_LINE_START;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.insets = new Insets(10, 10, 0,0);
        add (finishAppointment, c);


    }
}


