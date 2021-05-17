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
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author libst
 */
public class AppointmentButtonsPanel extends JPanel{
    AppointmentButtonsPanel store = this;
    
    public AppointmentButtonsPanel(PatientManagementView frame) throws IOException {
        setLayout(new GridBagLayout());
        setBackground(new Color(18, 29, 94));
        GridBagConstraints c = new GridBagConstraints();
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        Dimension preferredD = new Dimension(screenSize.width/2, screenSize.height/2);
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
                    frame.remove(store);
                    frame.add(menuIconsPanel);
                    frame.revalidate();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (SQLException ex) {
                    ex.printStackTrace();
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
        JLabel appointmentTitle = new JLabel("Appointment");
        appointmentTitle.setFont(new Font("Arial", Font.BOLD, 40));
        appointmentTitle.setForeground(Color.WHITE);
        add(appointmentTitle, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        ButtonsPane buttonsPane = new ButtonsPane(frame, preferredD);
        add(buttonsPane, c);
        
    }
    
    private class ButtonsPane extends JPanel{
        ButtonsPane storeButtons = this;
        
    
        public ButtonsPane(PatientManagementView frame, Dimension d) {
             //set layout
            setLayout(new GridBagLayout());
            setBackground(Color.WHITE);
            GridBagConstraints c = new GridBagConstraints();
            this.setPreferredSize(d);
            this.setMinimumSize(d);

           Font buttonFont = new Font("Arial", Font.BOLD, 25);
           Color dBlue = new Color(18, 29, 94);
            c.gridx = 0;
            c.weightx = 0.5;
            c.gridwidth = 2;
            c.gridy = 2;
            JButton createAppointmentB = new JButton("Create Appointment");
            createAppointmentB.setBackground(dBlue);
            createAppointmentB.setForeground(Color.WHITE);
            createAppointmentB.setBorderPainted(false);
            createAppointmentB.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            createAppointmentB.setFont(buttonFont);
            createAppointmentB.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    AppointmentsPanel appointmentsPanel;
                    try {
                        appointmentsPanel = new AppointmentsPanel(frame, frame.getWidth(), frame.getHeight());
                        Patient patient1 = appointmentsPanel.getPatient(appointmentsPanel.titleAP);
                        frame.remove(store);
                        frame.remove(storeButtons);
                        appointmentsPanel.setPreferredSize(new Dimension(frame.getWidth(), (frame.getHeight() - 200)));
                        frame.add(appointmentsPanel);
                        frame.revalidate();
                    } catch (IOException ex) {
                        Logger.getLogger(AppointmentButtonsPanel.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(AppointmentButtonsPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            });
            add(createAppointmentB, c);

            c.gridx = 2;
            JButton browseAppointmentB = new JButton("Browse Appointment History");
            browseAppointmentB.setBackground(dBlue);
            browseAppointmentB.setForeground(Color.WHITE);
            browseAppointmentB.setBorderPainted(false);
            browseAppointmentB.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            browseAppointmentB.setFont(buttonFont);
            browseAppointmentB.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                }
            });
            add(browseAppointmentB, c);
        }
    }
    
}
