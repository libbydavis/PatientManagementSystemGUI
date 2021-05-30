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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Raj
 */
class AddPatientsView extends JPanel
{   
    boolean validName, validAge, validPhoneNo, validStreet, validCond, validMeasure, validMeds;
    JButton addPatient;
    AddPatientController adp = new AddPatientController();
    
    public AddPatientsView(PatientManagementView frame, double width, double height) throws IOException, SQLException 
    {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        
        this.add(nhiPanel());
        this.add(namePanel());
        this.add(agePanel());
        this.add(phoneNoPanel());
        this.add(streetPanel());
        this.add(measurementsPanel());
        this.add(conditionsPanel());
        this.add(currentMedsPanel());
        // Save and Exit Panel
        JPanel saveAndExitPanel = new JPanel();
        addPatient = new JButton("Add Patient and Exit");
        addPatient.setEnabled(false);
        saveAndExitPanel.add(addPatient);
        this.add(saveAndExitPanel);
        
        this.setPreferredSize(new Dimension(screenSize.width/2, screenSize.height/2));
    }
    
    public JPanel namePanel()
    {
        AddPatientsModel makeNamePanel = new AddPatientsModel("Enter patient's full name:", "e.g. John Smith", "Incorrect input, please try again!");
        makeNamePanel.clearTextField();
        makeNamePanel.getEnterValues().setPreferredSize(new Dimension(70, 20));
        makeNamePanel.getEnterValues().addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                boolean nullStr = adp.checkNullString(makeNamePanel);
                
                if(!nullStr)
                {
                    adp.setName(makeNamePanel);
                    validName = true;
                }
                else
                {
                    validName = false;
                }
                
                enableButnIfValidPatient();
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
                boolean correctAge = adp.validateAge(makeAgePanel);
                
                if(correctAge)
                {
                    adp.setAge(makeAgePanel);
                    validAge = true;
                }
                else
                {
                    validAge = false;
                }
                
                enableButnIfValidPatient();
            }
        });
        return makeAgePanel.combineComponents();
    }
    
    public JPanel phoneNoPanel()
    {
        AddPatientsModel makePhoneNoPanel = new AddPatientsModel("Enter patient's phone number:", "e.g. 0212345678", "Incorrect input, please try again!");
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
        AddPatientsModel makeStreetPanel = new AddPatientsModel("Enter patient's street address:", "e.g. 123 Fake St", "Incorrect input, please try again!");
        makeStreetPanel.clearTextField();
        makeStreetPanel.getEnterValues().setPreferredSize(new Dimension(70, 20));
        makeStreetPanel.getEnterValues().addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                boolean nullStr = adp.checkNullString(makeStreetPanel);
                
                if (!nullStr) 
                {
                    adp.setName(makeStreetPanel);
                    adp.setStreet(makeStreetPanel);
                    validStreet = true;
                }
                else 
                {
                   
                    validStreet = false;
                }
                
                enableButnIfValidPatient();
            }
        });
        return makeStreetPanel.combineComponents();
    }
    
    public JPanel currentMedsPanel()
    {
        ArrayList<String> currentMeds = new ArrayList<String>();
        JPanel medPanel = new JPanel();
        JLabel promptMsg = new JLabel("Select your current medication: ");
        medPanel.add(promptMsg);
        JComboBox firstMed = new JComboBox();
        firstMed.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                String chosenMed = firstMed.getSelectedItem().toString();
                if((!chosenMed.equalsIgnoreCase("choose an option")) && (!(currentMeds.contains(chosenMed))))
                {                  
                    currentMeds.add(chosenMed);
                    System.out.println(currentMeds.get(0));
                    firstMed.setEnabled(false);
                }
            }
        });
        JComboBox secondMed = new JComboBox();
        secondMed.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                String chosenMed = secondMed.getSelectedItem().toString();
                if((!chosenMed.equalsIgnoreCase("choose an option")) && (!(currentMeds.contains(chosenMed)))) 
                {
                    currentMeds.add(chosenMed);
                    System.out.println(currentMeds.get(0));
                    secondMed.setEnabled(false);
                }
            }
        });
        JComboBox thirdMed = new JComboBox();
        thirdMed.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                String chosenMed = thirdMed.getSelectedItem().toString();
                if((!chosenMed.equalsIgnoreCase("choose an option")) && (!(currentMeds.contains(chosenMed)))) 
                {
                    currentMeds.add(chosenMed);
                    System.out.println(currentMeds.get(0));
                    thirdMed.setEnabled(false);
                }
            }
        });
        JComboBox fourthMed = new JComboBox();
        fourthMed.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                String chosenMed = fourthMed.getSelectedItem().toString();
                if((!chosenMed.equalsIgnoreCase("choose an option")) && (!(currentMeds.contains(chosenMed)))) 
                {
                    currentMeds.add(chosenMed);
                    System.out.println(chosenMed);
                    fourthMed.setEnabled(false);
                }
            }
        });
        secondMed.setSelectedItem(5);
        firstMed.addItem("choose an option");
        secondMed.addItem("choose an option");
        thirdMed.addItem("choose an option");
        fourthMed.addItem("choose an option");
        
        try 
        {
            DatabaseConnection dbc = new DatabaseConnection();
            String sqlQuery = "SELECT MEDNAME FROM MEDICATION";
            PreparedStatement prepstmt = dbc.getConnectionMedication().prepareStatement(sqlQuery);
            ResultSet rs = prepstmt.executeQuery();
            
            while(rs.next())
            {
                firstMed.addItem(rs.getString("MEDNAME").toString());
                secondMed.addItem(rs.getString("MEDNAME").toString());
                thirdMed.addItem(rs.getString("MEDNAME").toString());
                fourthMed.addItem(rs.getString("MEDNAME").toString());
            }
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(AddPatientsView.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
          secondMed.setSelectedIndex(0);
          medPanel.add(firstMed);
          medPanel.add(secondMed);
          medPanel.add(thirdMed);
          medPanel.add(fourthMed);
          
          return medPanel;
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

        AddPatientsModel makeStreetPanel = new AddPatientsModel("Enter patient's measurements:", "e.g. weight: 63 kgs", "Incorrect input, please try again!");
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
    
    public JPanel nhiPanel() throws SQLException
    {
        String newNhi = adp.genNhi();
        JLabel genNhi = new JLabel("Auto-Generated NHI for this patient:  "+newNhi);
        JPanel nhiPanel = new JPanel();
        nhiPanel.add(genNhi);
        return nhiPanel;
    }
    
    public boolean isValidPatient()
    {
        return  validName && validAge && validStreet;
        //return validName && validAge && validPhoneNo && validStreet && validCond && validMeasure && validMeds;
    }
    
    public void enableButnIfValidPatient()
    {
        if (isValidPatient() == true) 
        {
            addPatient.setEnabled(true);
        }
    }
}
