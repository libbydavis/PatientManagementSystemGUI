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
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    JPanel docPanel, nhiPanel, medNoPanel, repMedsPanel, timeDatePanel, savePanel, confirmedPrescPanel, dosageAmountPanel, dosageFreqPanel;
    JTextField enterDocName, enterNHI, enterMedNo, enterRepMeds, enterDosage, enterDosageFreq;// enter rep meds must be T/F
    JLabel timeDate, nhiPatName, docNamePrompt, patNHIPrompt, medNoPrompt, repMedsPrompt, dosageAmountPrompt, dosageFreqPrompt, medNoErrorMsg, nhiErrorMsg;
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
        enterDocName = new JTextField("Enter Doctor Name");
        enterDocName.setPreferredSize(new Dimension(113, 20));
        enterDocName.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
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
                source.removeFocusListener(this);
            }

            @Override
            public void focusLost(FocusEvent e) 
            {  
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        docPanel.add(docNamePrompt);
        docPanel.add(enterDocName);
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
                    String enteredNHI = enterNHI.getText().toLowerCase();
                    DatabaseConnection dbc = new DatabaseConnection();
                    PreparedStatement prepstmt = dbc.getConnectionPatients().prepareStatement("SELECT FIRSTNAME, LASTNAME FROM PATIENTS WHERE NHI = \'" + enteredNHI + "\'");
                    ResultSet rs = prepstmt.executeQuery();
                    
                    while (rs.next()) 
                    {
                        nhiErrorMsg.setVisible(false);
                        String patFName = rs.getString(1);
                        String patLName = rs.getString(2);
                        objPresc.setPatientName(patFName.concat(" " + patLName));
                    }
                    
                    if(!rs.next())
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
                source.removeFocusListener(this);
            }

            @Override
            public void focusLost(FocusEvent e) 
            {
                JTextField source = (JTextField) e.getComponent();
                source.setText("");
                source.removeFocusListener(this);
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
                source.removeFocusListener(this);
            }

            @Override
            public void focusLost(FocusEvent e) 
            {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            
        });
        medNoPanel.add(medNoPrompt);
        medNoPanel.add(enterMedNo);
        medNoPanel.add(medNoErrorMsg);
        this.add(medNoPanel);
        // Dosage Amount Panel
        dosageAmountPanel = new JPanel();
        dosageAmountPrompt = new JLabel("Enter Medication Dosage: ");
        enterDosage = new JTextField("Enter mg/ml to 2 d.p");
        enterDosage.setPreferredSize(new Dimension(100, 20));
        enterDosage.addFocusListener(new FocusListener()
        {
            @Override
            public void focusGained(FocusEvent e) 
            {
                JTextField source = (JTextField) e.getComponent();
                source.setText("");
                source.removeFocusListener(this);
            }

            @Override
            public void focusLost(FocusEvent e) 
            {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            
        });
        dosageAmountPanel.add(dosageAmountPrompt);
        dosageAmountPanel.add(enterDosage);
        this.add(dosageAmountPanel);
        // Dosage Frequency Panel
        dosageFreqPanel = new JPanel();
        dosageFreqPrompt = new JLabel("Dosage Frequency: ");
        enterDosageFreq = new JTextField("Frequency of dosage e.g (3 times a week)");
        enterDosageFreq.setPreferredSize(new Dimension(240, 20));
        enterDosageFreq.addFocusListener(new FocusListener()
        {
            @Override
            public void focusGained(FocusEvent e) 
            {
                JTextField source = (JTextField) e.getComponent();
                source.setText("");
                source.removeFocusListener(this);
            }

            @Override
            public void focusLost(FocusEvent e) 
            {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        dosageFreqPanel.add(dosageFreqPrompt);
        dosageFreqPanel.add(enterDosageFreq);
        this.add(dosageFreqPanel);
        // Repeat meds Panel T/F
        repMedsPanel = new JPanel();
        repMedsPrompt = new JLabel("Repeat Prescription: ");
        enterRepMeds = new JTextField("Enter True/False");
        enterRepMeds.setPreferredSize(new Dimension(100, 20));
        enterRepMeds.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                // TODO: Error Checking
                objPresc.setRepeat(Boolean.parseBoolean(enterRepMeds.getText()));
            }
            
        }); 
        enterRepMeds.addFocusListener(new FocusListener()
        {
            @Override
            public void focusGained(FocusEvent e) 
            {
                JTextField source = (JTextField) e.getComponent();
                source.setText("");
                source.removeFocusListener(this);
            }

            @Override
            public void focusLost(FocusEvent e) 
            {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            
        });
        repMedsPanel.add(repMedsPrompt);
        repMedsPanel.add(enterRepMeds);
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
                objPresc.setDateTime(sdf.format(currentTimeDate));
                saved.setVisible(true);
            }
        });
        savePanel.add(savePresc);
        this.add(savePanel);
        //Size of CreatePrescriptionPanel
        this.setPreferredSize(new Dimension(screenSize.width/2, screenSize.height/2));
    }
    
}
