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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class PatientsPanel extends JPanel 
{
    PatientsPanel panel = this;
    private int width;
    private int height;
    private GridBagConstraints c1;
    private PatientsController controller;
    private JButton addPatientB;
    private JButton editPatientB;
    private JButton browsePatientsB;
    private PatientButtonsPane buttonsPane1;
    private JButton backButton;
    private JLabel patientsLabel;

    public PatientsPanel(PatientManagementView frame, int width, int height) throws IOException, SQLException {
        setLayout(new GridBagLayout());
        setBackground(new Color(18, 29, 94));
        c1 = new GridBagConstraints();
        controller = new PatientsController(this, frame);
        
        this.width = width;
        this.height = height;
        
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        Dimension preferredD = new Dimension(screenSize.width/2, screenSize.height/2);
        
        BufferedImage backImage = ImageIO.read(new File("src\\Images\\backButtonArrow.png"));
        backButton = new JButton(new ImageIcon(backImage));
        backButton.setBorder(new EmptyBorder(30, 30, 10, 10));
        backButton.setContentAreaFilled(false);
        backButton.addActionListener(controller);

        c1.gridx = 0;
        c1.gridy = 0;
        c1.weightx = 1;
        c1.weighty = 1;
        c1.anchor = GridBagConstraints.FIRST_LINE_START;
        add(backButton, c1);

        
        c1.anchor = GridBagConstraints.CENTER;
        patientsLabel = new JLabel("Patients");
        patientsLabel.setFont(new Font("Arial", Font.BOLD, 40));
        patientsLabel.setForeground(Color.WHITE);
        add(patientsLabel, c1);
        

        c1.fill = GridBagConstraints.HORIZONTAL;
        c1.gridx = 0;
        c1.gridy = 1;
        buttonsPane1 = new PatientButtonsPane(frame, preferredD, this);
        add(buttonsPane1, c1);
    }

    public JButton getBackButton() {
        return backButton;
    }

    public PatientButtonsPane getButtonsPane1() {
        return buttonsPane1;
    }
    
    public GridBagConstraints getConstraints() {
        return c1;
    }

    public JButton getAddPatientB() {
        return addPatientB;
    }

    public JButton getEditPatientB() {
        return editPatientB;
    }

    public JButton getBrowsePatientsB() {
        return browsePatientsB;
    }
    
    public void setPatientsLabel(String text) {
        patientsLabel.setText(text);
    }
    
    public class PatientButtonsPane extends JPanel{
        PatientButtonsPane store = this;
        PatientsPanel panel;
        
        public PatientButtonsPane(PatientManagementView frame, Dimension d, PatientsPanel panel) {
        this.panel = panel;
        
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
        addPatientB = new JButton("Add Patient");
        addPatientB.setBackground(dBlue);
        addPatientB.setForeground(Color.WHITE);
        addPatientB.setBorderPainted(false);
        addPatientB.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        addPatientB.setFont(buttonFont);
        addPatientB.addActionListener(controller);
        add(addPatientB, c);
        
        c.gridx = 2;
        editPatientB = new JButton("Delete Patient");
        editPatientB.setBackground(dBlue);
        editPatientB.setForeground(Color.WHITE);
        editPatientB.setBorderPainted(false);
        editPatientB.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        editPatientB.setFont(buttonFont);
        editPatientB.addActionListener(controller);
        add(editPatientB, c);
        
        c.gridx = 4;
        browsePatientsB = new JButton("Browse Patients");
        browsePatientsB.setBackground(dBlue);
        browsePatientsB.setForeground(Color.WHITE);
        browsePatientsB.setBorderPainted(false);
        browsePatientsB.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        browsePatientsB.setFont(buttonFont);
        browsePatientsB.addActionListener(controller);
        add(browsePatientsB, c);    
    }
    
    }
}
