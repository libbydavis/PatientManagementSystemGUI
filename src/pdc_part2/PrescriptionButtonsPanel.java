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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author libst
 */
public class PrescriptionButtonsPanel extends JPanel{
    PrescriptionButtonsPanel store = this;
    PrescriptionPanel panel;
    public PrescriptionButtonsPanel(PatientManagementView frame, int width, int height, PrescriptionPanel panel) {
        this.panel = panel;
         //set layout
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        GridBagConstraints c = new GridBagConstraints();
        this.setPreferredSize(new Dimension(width, height - 300));
        this.setMinimumSize(new Dimension(width, height - 300));

       Font buttonFont = new Font("Arial", Font.BOLD, 25);
       Color dBlue = new Color(18, 29, 94);
        c.gridx = 0;
        c.weightx = 0.5;
        c.gridwidth = 2;
        c.gridy = 2;
        JButton createPrescriptionB = new JButton("Create Prescription");
        createPrescriptionB.setBackground(dBlue);
        createPrescriptionB.setForeground(Color.WHITE);
        createPrescriptionB.setBorderPainted(false);
        createPrescriptionB.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        createPrescriptionB.setFont(buttonFont);
        createPrescriptionB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object source = e.getSource();
            }
        });
        add(createPrescriptionB, c);
        
        c.gridx = 2;
        JButton viewMedsB = new JButton("View Medications");
        viewMedsB.setBackground(dBlue);
        viewMedsB.setForeground(Color.WHITE);
        viewMedsB.setBorderPainted(false);
        viewMedsB.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        viewMedsB.setFont(buttonFont);
        viewMedsB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object source = e.getSource();
                JPanel medPanelNew = new MedicationsPanel();
                panel.removeButtonsPane();
                frame.add(medPanelNew);
                frame.revalidate();
            }
        });
        add(viewMedsB, c);
        
        c.gridx = 4;
        JButton editPrescriptionB = new JButton("Edit Prescription");
        editPrescriptionB.setBackground(dBlue);
        editPrescriptionB.setForeground(Color.WHITE);
        editPrescriptionB.setBorderPainted(false);
        editPrescriptionB.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        editPrescriptionB.setFont(buttonFont);
        editPrescriptionB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object source = e.getSource();
            }
        });
        add(editPrescriptionB, c);
        
    }
}