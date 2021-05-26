/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdc_part2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class BrowsePatientsPanel extends JPanel {
    BrowsePatientsPanel panel = this;
    private PatientsController controller;
    private JButton searchButton;
    private JTextField searchBox;
    private JComponent patientTable;
    private GridBagConstraints c;

    public BrowsePatientsPanel(PatientManagementView frame, double width, double height, PatientsController controller) throws IOException, SQLException {
        setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        setSize((int) width, (int) height);
        setMinimumSize(new Dimension((int) width, (int) height));
        this.controller = controller;
        
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.PAGE_START;
        c.insets = new Insets(30, 0, 20, 0);
        searchBox = new JTextField("enter NHI, patient name or DOB...");
        searchBox.addFocusListener(new FocusListener(){
            @Override
            public void focusGained(FocusEvent e){
                searchBox.setForeground(Color.BLACK);
                searchBox.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });
        add(searchBox, c);
        
        c.gridx = 1;
        searchButton = new JButton("search");
        searchButton.addActionListener(controller);
        add(searchButton, c);

         c.gridx = 0;
         c.gridy = 1;
         c.insets = new Insets(10, 10, 0, 10);
         c.fill = GridBagConstraints.HORIZONTAL;
        Patient allPatients = new Patient();
        patientTable = allPatients.displayAllPatients();
        this.add(patientTable, c);
    }
    
    public JButton getSearchButton() {
        return searchButton;
    }
    
    public String getSearchText() {
        return searchBox.getText();
    }
    
    public void displayIndividualPatient(Patient patient) {
        remove(patientTable);
        remove(searchBox);
        remove(searchButton);
        JComponent patientDetails = patient.displayIndividualPatientDetails();
        add(patientDetails, c);
        revalidate();
    }
}
