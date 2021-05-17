/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdc_part2;


import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    JPanel docPanel, nhiPanel, medNoPanel, repMedsPanel, timeDatePanel, savePanel, confirmedPrescPanel, dsgAmtPanel, dsgFreqPanel;
    JTextField enterDocName, enterNHI, enterMedNo, enterRepMeds, enterDosage, enterDosageFreq;// enter rep meds must be T/F
    JLabel timeDate, nhiPatName, docNamePrompt, patNHIPrompt, medNoPrompt, repMedsPrompt, dsgAmtPrompt, dsgFreqPrompt, medNoErrorMsg, nhiErrorMsg, dsgAmtErrorMsg, dsgFreqErrorMsg, repMedsErrorMsg, docNameErrorMsg;
    Prescription objPresc;
    Dimension screenSize;
    JButton savePresc;
    Toolkit kit;
    /**
     * For the focusGained method on each focusListener, could make a method.
     * Need to make an error JLabel for every time incorrect input is entered.
     * Make a method for each panel within the 'CreatePrescriptionComponents' class.
     */
    public CreatePrescriptionPanel() 
    {
        // General Panel Setup
        objPresc = new Prescription();
        kit = Toolkit.getDefaultToolkit();
        screenSize = kit.getScreenSize();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        // Date and Time Panel
        timeDatePanel = new JPanel();
        Date currentTimeDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy h:mm aa");
        objPresc.setDateTime(sdf.format(currentTimeDate));
        timeDate = new JLabel("Prescription made at: " + sdf.format(currentTimeDate));
        timeDatePanel.add(timeDate);
        this.add(timeDatePanel);
        // Doctor Panel
        docPanel = new JPanel();
        docNamePrompt = new JLabel("Doctor's Name: ");
        enterDocName = new JTextField("e.g John Smith");
        enterDocName.setPreferredSize(new Dimension(113, 20));
        docNameErrorMsg = new JLabel("Doctor's Name cannot be empty, please try again!");
        docNameErrorMsg.setVisible(false);
        enterDocName.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                String docName = enterDocName.getText();
                if(docName.length() == 0)
                {
                    docNameErrorMsg.setVisible(true);
                }
                else
                {
                    docNameErrorMsg.setVisible(false);
                }
                objPresc.setDoctorName((String)enterDocName.getText()); //To change body of generated methods, choose Tools | Templates.
            }
        });
        enterDocName.addFocusListener(new FocusListener()
        {
            @Override
            public void focusGained(FocusEvent e) 
            {
                JTextField source = (JTextField) e.getComponent();
                source.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) 
            {  
                //enterDocName.setText("e.g John Smith");
            }
        });
        docPanel.add(docNamePrompt);
        docPanel.add(enterDocName);
        docPanel.add(docNameErrorMsg);
        this.add(docPanel); 
        // NHI Panel
        nhiPanel = new JPanel();
        patNHIPrompt = new JLabel("Enter Patient's NHI: ");
        enterNHI = new JTextField("e.g(tes123)");
        nhiErrorMsg = new JLabel("This NHI does not exist, please try again!");
        nhiErrorMsg.setVisible(false);
        enterNHI.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) 
            {
                try 
                {
                    
                    DatabaseConnection dbc = new DatabaseConnection();
                    PreparedStatement prepstmt = dbc.getConnectionPatients().prepareStatement("SELECT FIRSTNAME, LASTNAME FROM PATIENTS WHERE NHI = \'" + enterNHI.getText().toLowerCase() + "\'");
                    ResultSet rs = prepstmt.executeQuery();
                    
                    if(rs.next())
                    {
                        System.out.println("true");
                        //enterNHI.setText("");
                        nhiErrorMsg.setVisible(false);
                        String patFName = rs.getString(1);
                        String patLName = rs.getString(2);
                        objPresc.setPatientName(patFName.concat(" " + patLName));
                    }
                    else
                    {
                       enterNHI.setText("");
                       nhiErrorMsg.setVisible(true);
                    }
                } 
                catch (SQLException ex) 
                {
                    ex.printStackTrace();
                } 
            }
        });
        enterNHI.setPreferredSize(new Dimension(70, 20));
        enterNHI.addFocusListener(new FocusListener()
        {
            @Override
            public void focusGained(FocusEvent e) 
            {
                JTextField source = (JTextField) e.getComponent();
                source.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) 
            {
                //enterNHI.setText("e.g(tes123)");
            }
            
        });
        nhiPanel.add(patNHIPrompt);
        nhiPanel.add(enterNHI);
        nhiPanel.add(nhiErrorMsg);
        this.add(nhiPanel);
        // MedNo Panel
        medNoPanel = new JPanel();
        medNoPrompt = new JLabel("Medicine Number: ");
        enterMedNo = new JTextField("Enter Number from 1-7");
        medNoErrorMsg = new JLabel("Incorrect input! Please Try Again");
        medNoErrorMsg.setVisible(false);
        enterMedNo.setPreferredSize(new Dimension(135, 20));
        enterMedNo.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                try 
                {
                    int medNo = Integer.parseInt(enterMedNo.getText());

                    if (medNo < 1 || medNo > 7) 
                    {
                        enterMedNo.setText("");   
                        medNoErrorMsg.setVisible(true);
                    } 
                    else 
                    {
                        medNoErrorMsg.setVisible(false);
                        enterMedNo.setText("");

                        DatabaseConnection dbc = new DatabaseConnection();
                        PreparedStatement prepstmt = dbc.getConnectionMedication().prepareStatement("SELECT MEDNAME, SIDE_EFFECTS, CONDITIONS FROM MEDICATION WHERE MEDNO = " + medNo);
                        ResultSet rs = prepstmt.executeQuery();
                        
                        while(rs.next())
                        {
                            String medName = rs.getString(1);
                            String medSideEff = rs.getString(2);
                            String medConditions = rs.getString(3);
                            System.out.println(medName);
                            objPresc.setMeds(new Medication(medName, medSideEff, medConditions));
                        } 
                        prepstmt.close();
                        rs.close();
                    }
                } 
                catch (NumberFormatException er) 
                {
                    medNoErrorMsg.setVisible(true);
                    enterMedNo.setText("");
                } 
                catch (SQLException ex) 
                {
                   ex.printStackTrace();
                }
            }    
        });
        enterMedNo.addFocusListener(new FocusListener()
        {
            @Override
            public void focusGained(FocusEvent e) 
            {
                JTextField source = (JTextField) e.getComponent();
                source.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) 
            {
              //enterMedNo.setText("Enter Number from 1-7");
            }
            
        });
        medNoPanel.add(medNoPrompt);
        medNoPanel.add(enterMedNo);
        medNoPanel.add(medNoErrorMsg);
        this.add(medNoPanel);
        // Dosage Amount Panel
        dsgAmtPanel = new JPanel();
        dsgAmtPrompt = new JLabel("Enter Medication Dosage: ");
        enterDosage = new JTextField("Enter mg/ml to 2 d.p");
        dsgAmtErrorMsg = new JLabel("Incorrect format for dosage amount, please try again!");
        dsgAmtErrorMsg.setVisible(false);
        enterDosage.setPreferredSize(new Dimension(120, 20));
        enterDosage.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                try
                {
                    double dosageEntered = Double.parseDouble(enterDosage.getText());
                    
                    if(dosageEntered <= 0.0)
                    {
                        dsgAmtErrorMsg.setVisible(true);
                    }
                    else
                    {
                        dsgAmtErrorMsg.setVisible(false);
                    }
                    
                    objPresc.setDosage(new Dosage(dosageEntered));
                }
                catch(NumberFormatException nfe)
                {
                    dsgAmtErrorMsg.setVisible(true);
                    enterDosage.setText("");
                }
            }
        });
        enterDosage.addFocusListener(new FocusListener()
        {
            @Override
            public void focusGained(FocusEvent e) 
            {
                JTextField source = (JTextField) e.getComponent();
                source.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) 
            {
                //enterDosage.setText("Enter mg/ml to 2 d.p");
            }
            
        });
        dsgAmtPanel.add(dsgAmtPrompt);
        dsgAmtPanel.add(enterDosage);
        dsgAmtPanel.add(dsgAmtErrorMsg);
        this.add(dsgAmtPanel);
        // Dosage Frequency Panel
        dsgFreqPanel = new JPanel();
        dsgFreqPrompt = new JLabel("Dosage Frequency: ");
        enterDosageFreq = new JTextField("Frequency of dosage e.g (3 times a week)");
        dsgFreqErrorMsg = new JLabel("This field cannot be empty, please try again!");
        dsgFreqErrorMsg.setVisible(false);
        enterDosageFreq.setPreferredSize(new Dimension(240, 20));
        enterDosageFreq.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                String dsgFreq = enterDosageFreq.getText();
                if(dsgFreq.length() == 0)
                {
                    dsgFreqErrorMsg.setVisible(true);
                }
                else
                {
                    dsgFreqErrorMsg.setVisible(false);
                }
            }
        });
        enterDosageFreq.addFocusListener(new FocusListener()
        {
            @Override
            public void focusGained(FocusEvent e) 
            {
                JTextField source = (JTextField) e.getComponent();
                source.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) 
            {
                //enterDosageFreq.setText("Frequency of dosage e.g (3 times a week)");
            }
        });
        dsgFreqPanel.add(dsgFreqPrompt);
        dsgFreqPanel.add(enterDosageFreq);
        dsgFreqPanel.add(dsgFreqErrorMsg);
        this.add(dsgFreqPanel);
        // Repeat meds Panel T/F
        repMedsPanel = new JPanel();
        repMedsPrompt = new JLabel("Repeat Prescription: ");
        enterRepMeds = new JTextField("True/False OR T/F");
        repMedsErrorMsg = new JLabel("Not a boolean, please try again!");
        repMedsErrorMsg.setVisible(false);
        enterRepMeds.setPreferredSize(new Dimension(110, 20));
        enterRepMeds.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                if(enterRepMeds.getText().equalsIgnoreCase("true"))
                {
                    repMedsErrorMsg.setVisible(false);
                    objPresc.setRepeat(true);
                }
                else if(enterRepMeds.getText().equals("false"))
                {
                    repMedsErrorMsg.setVisible(false);
                    objPresc.setRepeat(false);
                }
                else if(enterRepMeds.getText().toLowerCase().toCharArray()[0] == 't')
                {
                    repMedsErrorMsg.setVisible(false);
                    objPresc.setRepeat(true);
                }
                else if(enterRepMeds.getText().toLowerCase().toCharArray()[0] == 'f')
                {
                    repMedsErrorMsg.setVisible(false);
                    objPresc.setRepeat(false);
                }
                else
                {
                    enterRepMeds.setText("");
                    repMedsErrorMsg.setVisible(true);
                }    
            }
        });
        enterRepMeds.addFocusListener(new FocusListener()
        {
            @Override
            public void focusGained(FocusEvent e) 
            {
                JTextField source = (JTextField) e.getComponent();
                source.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) 
            {
                //enterRepMeds.setText("True/False OR T/F");
            }    
        });
        repMedsPanel.add(repMedsPrompt);
        repMedsPanel.add(enterRepMeds);
        repMedsPanel.add(repMedsErrorMsg);
        this.add(repMedsPanel);
        //Confirm Prescription Panel
        savePanel = new JPanel();
        savePresc = new JButton("Save");
        JLabel saved = new JLabel("Saved Successfully!");
        saved.setVisible(false);
        savePanel.add(saved);
        savePresc.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                // Make a Prescription table - primary key is 
                // Put objPresc into the Prescription Table
                saved.setVisible(true);
            }
        });
        savePanel.add(savePresc);
        this.add(savePanel);
        //Size of CreatePrescriptionPanel
        this.setPreferredSize(new Dimension(screenSize.width/2, screenSize.height/2));
    }
    
}
