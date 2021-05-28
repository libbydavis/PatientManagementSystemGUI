/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdc_part2;

import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Raj
 */
class AddPatientsView extends JPanel
{   
    public AddPatientsView(PatientManagementView frame, double width, double height) throws IOException, SQLException 
    {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        
        this.add(namePanel());
        this.add(nhiPanel());
        this.add(agePanel());
        this.add(phoneNoPanel());
        this.add(streetPanel());
        this.add(measurementsPanel());
        this.add(conditionsPanel());
        this.add(currentMedsPanel());
        this.add(saveAndExitPanel());
        
        this.setPreferredSize(new Dimension(screenSize.width/2, screenSize.height/2));
    }
    
    public JPanel namePanel()
    {
        AddPatientsModel makeNamePanel = new AddPatientsModel("Enter patient's first name:", "e.g. John Smith", "Incorrect input, please try again!");
        makeNamePanel.clearTextField();
        makeNamePanel.getEnterValues().setPreferredSize(new Dimension(70, 20));
        makeNamePanel.getEnterValues().addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                AddPatientController adp = new AddPatientController();
                adp.checkNullString(makeNamePanel);
                adp.setName(makeNamePanel);
            }
        });
        return makeNamePanel.combineComponents();
    }
    
    public JPanel agePanel()
    {
        AddPatientsModel makeAgePanel = new AddPatientsModel("Enter patient's age:", "e.g. 12", "Incorrect input, please try again!");
        makeAgePanel.clearTextField();
        makeAgePanel.getEnterValues().setPreferredSize(new Dimension(50, 20));
        makeAgePanel.getEnterValues().addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                makeAgePanel.getErrorMsg().setVisible(true);
            }
        });
        
        return makeAgePanel.combineComponents();
    }
    
    public JPanel phoneNoPanel()
    {
        AddPatientsModel makePhoneNoPanel = new AddPatientsModel("Enter your Phone Number:", "e.g. 0212345678", "Incorrect input, please try again!");
        makePhoneNoPanel.clearTextField();
        makePhoneNoPanel.getEnterValues().setPreferredSize(new Dimension(100, 20));
        makePhoneNoPanel.getEnterValues().addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                makePhoneNoPanel.getErrorMsg().setVisible(true);
            }
        });
        return makePhoneNoPanel.combineComponents();
    }
    
    public JPanel streetPanel()
    {
        AddPatientsModel makeStreetPanel = new AddPatientsModel("Enter your street address:", "e.g. 123 Fake St", "Incorrect input, please try again!");
        makeStreetPanel.clearTextField();
        makeStreetPanel.getEnterValues().setPreferredSize(new Dimension(70, 20));
        makeStreetPanel.getEnterValues().addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                makeStreetPanel.getErrorMsg().setVisible(true);
            }
        });
        return makeStreetPanel.combineComponents();
    }
    
    public JPanel currentMedsPanel()
    {
        AddPatientsModel makeStreetPanel = new AddPatientsModel("Enter your current Medication:", "A number between 1-7 (The MedNo#)", "Incorrect input, please try again!");
        makeStreetPanel.clearTextField();
        makeStreetPanel.getEnterValues().setPreferredSize(new Dimension(215, 20));
        makeStreetPanel.getEnterValues().addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                makeStreetPanel.getErrorMsg().setVisible(true);
            }
        });
        return makeStreetPanel.combineComponents();
    }
    
     public JPanel conditionsPanel()
    {
        JPanel objJPanel = new JPanel();
        AddPatientsModel makeStreetPanel = new AddPatientsModel("Enter your current conditions:", "e.g. High blood pressure", "Incorrect input, please try again!");
        makeStreetPanel.clearTextField();
        makeStreetPanel.getEnterValues().setPreferredSize(new Dimension(215, 20));
        makeStreetPanel.getEnterValues().addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                makeStreetPanel.getErrorMsg().setVisible(true);
            }
        });
        return makeStreetPanel.combineComponents();
    }
     
    public JPanel measurementsPanel()
    {

        AddPatientsModel makeStreetPanel = new AddPatientsModel("Enter your measurements:", "e.g. weight: 63 kgs", "Incorrect input, please try again!");
        makeStreetPanel.clearTextField();
        makeStreetPanel.getEnterValues().setPreferredSize(new Dimension(215, 20));
        makeStreetPanel.getEnterValues().addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                makeStreetPanel.getErrorMsg().setVisible(true);
            }
        });
        return makeStreetPanel.combineComponents();
    }
    
    public JPanel nhiPanel()
    {
        //NHI GENERATOR?
        AddPatientsModel makeStreetPanel = new AddPatientsModel("Enter patient's NHI:", "e.g. weight: 63 kgs", "Incorrect input, please try again!");
        makeStreetPanel.clearTextField();
        makeStreetPanel.getEnterValues().setPreferredSize(new Dimension(215, 20));
        makeStreetPanel.getEnterValues().addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                makeStreetPanel.getErrorMsg().setVisible(true);
            }
        });
        return makeStreetPanel.combineComponents();
    }
    
    public JPanel saveAndExitPanel()
    {
        JPanel objPanel = new JPanel();
        JButton addPatient = new JButton("Add Patient and Exit");
        addPatient.setEnabled(false);
        objPanel.add(addPatient);
        
        return objPanel;
    }
}
