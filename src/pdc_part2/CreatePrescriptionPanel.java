/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdc_part2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Raj
 */
public class CreatePrescriptionPanel extends JPanel
{
    Prescription objPresc;
    JPanel docPanel, nhiPanel, medNoPanel, repMedsPanel;
    JTextField enterDocName, enterNHI, enterMedNo, enterRepMeds;
    JButton confirmDocName, confirmNHI, confirmMedNo, confirmRepMeds, confirmPresc;
    // enter rep meds must be T/F
    JLabel timeDate, nhiPatName;
    
    public CreatePrescriptionPanel() 
    {
        //this.setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
        objPresc = new Prescription();
        // Doctor Panel
        docPanel = new JPanel();
        enterDocName = new JTextField("Enter Doctor Name");
        enterDocName.setOpaque(true);
        //set docName dimensions to be bigger.
        confirmDocName = new JButton("Confirm Doctor's Name");
        docPanel.add(enterDocName);
        docPanel.add(confirmDocName);
        this.add(docPanel); // I want each Panel to be displayed under each other, how would that be possible?
        // NHI Panel
        nhiPanel = new JPanel();
        enterNHI = new JTextField("e.g(tes123)");
        confirmNHI = new JButton("Confirm NHI entered");
        nhiPatName = new JLabel();
        confirmNHI.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                //TODO: Validate NHI, add it to that patients prescriptions.
                objPresc.setPatientName(TOOL_TIP_TEXT_KEY);   
            }
            
        });
        nhiPanel.add(enterNHI);
        this.add(nhiPatName);
        nhiPanel.add(confirmNHI);
        this.add(nhiPanel);
        // MedNo Panel
        medNoPanel = new JPanel();
        enterMedNo = new JTextField("Enter Number from 1-7");
        //Size of CreatePrescriptionPanel
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        this.setPreferredSize(new Dimension(screenSize.width/2, screenSize.height/2));
    }
    
}
