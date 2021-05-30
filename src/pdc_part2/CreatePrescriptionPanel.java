/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdc_part2;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
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
    CreatePrescriptionPanel createPrescPanel = this;
    JPanel docPanel, nhiPanel, medNoPanel, repMedsPanel, timeDatePanel, savePanel, confirmedPrescPanel, dsgAmtPanel, dsgFreqPanel;
    JTextField enterDocName, enterNHI, enterMedNo, enterRepMeds, enterDosage, enterDosageFreq;// enter rep meds must be T/F
    JLabel timeDate, nhiPatName, docNamePrompt, patNHIPrompt, medNoPrompt, repMedsPrompt, dsgAmtPrompt, dsgFreqPrompt, docNameErrorMsg, medNoErrorMsg, nhiErrorMsg, dsgAmtErrorMsg, dsgFreqErrorMsg, repMedsErrorMsg;
    boolean validDocName, validNHI, validMedNo, validDsgAmt, validDsgFreq, validMedsRep;
    JButton savePresc;
    
    public CreatePrescriptionPanel(PatientManagementView frame, PrescriptionPanel prescPanel) 
    {
        // General Panel Setup
        Prescription objPresc = new Prescription();
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
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
        PrescriptionComponent makeDocPanel = new PrescriptionComponent();         
        docPanel = makeDocPanel.CreatePrescComponents("Doctor's Name: " ,"e.g John Smith", "Doctor's Name cannot be empty, please try again!");
        enterDocName = (JTextField) docPanel.getComponent(1);
        enterDocName.setPreferredSize(new Dimension(113, 20));
        docNameErrorMsg = (JLabel) docPanel.getComponent(2);
        enterDocName.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                 String docName = enterDocName.getText();
                if(docName.length() == 0)
                {
                    docNameErrorMsg.setVisible(true);
                    validDocName = false;
                }
                else
                {
                    docNameErrorMsg.setVisible(false);
                    validDocName = true;
                    isValidPrescription();
                }
                objPresc.setDoctorName((String)enterDocName.getText()); 
            }
        });
        this.add(docPanel);
        // NHI Panel
        PrescriptionComponent makeNHIPanel = new PrescriptionComponent();
        nhiPanel = makeNHIPanel.CreatePrescComponents("Enter Patient's NHI: ", "e.g(tes123)", "This NHI does not exist, please try again!");
        patNHIPrompt = new JLabel("Enter Patient's NHI: ");
        nhiPanel.getComponent(1);
        enterNHI = (JTextField) nhiPanel.getComponent(1);
        enterNHI.setPreferredSize(new Dimension(70, 20));
        nhiErrorMsg = (JLabel) nhiPanel.getComponent(2);
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
                        nhiErrorMsg.setVisible(false);
                        String patFName = rs.getString(1);
                        String patLName = rs.getString(2);
                        objPresc.setPatientName(patFName.concat(" " + patLName));
                        validNHI = true;
                        isValidPrescription();
                    }
                    else
                    {
                       enterNHI.setText("");
                       nhiErrorMsg.setVisible(true);
                       validNHI = false;
                    }
                } 
                catch (SQLException ex) 
                {
                    ex.printStackTrace();
                } 
            }
        });
        this.add(nhiPanel);
        // MedNo Panel
        PrescriptionComponent makeMedNoPanel = new PrescriptionComponent();
        medNoPanel = makeMedNoPanel.CreatePrescComponents("Medicine Number: ", "Enter Number from 1-7", "Incorrect input! Please Try Again");
        enterMedNo = (JTextField) medNoPanel.getComponent(1);
        enterMedNo.setPreferredSize(new Dimension(135, 20));
        medNoErrorMsg = (JLabel) medNoPanel.getComponent(2);
        medNoErrorMsg.setVisible(false);
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
                        validMedNo = false;
                    } 
                    else 
                    {
                        medNoErrorMsg.setVisible(false);

                        DatabaseConnection dbc = new DatabaseConnection();
                        PreparedStatement prepstmt = dbc.getConnectionMedication().prepareStatement("SELECT MEDNAME, SIDE_EFFECTS, CONDITIONS FROM MEDICATION WHERE MEDNO = " + medNo);
                        ResultSet rs = prepstmt.executeQuery();
                        
                        while(rs.next())
                        {
                            String medName = rs.getString(1);
                            String medSideEff = rs.getString(2);
                            String medConditions = rs.getString(3);
                            objPresc.setMeds(new Medication(medName, medSideEff, medConditions));
                        }
                        
                        validMedNo = true;
                        isValidPrescription();
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
        this.add(medNoPanel);
        // Dosage Amount Panel
        PrescriptionComponent makeDsgAmtPanel = new PrescriptionComponent();
        dsgAmtPanel = makeDsgAmtPanel.CreatePrescComponents("Enter Medication Dosage: ", "Enter mg/ml to 2 d.p", "Incorrect format for dosage amount, please try again!");
        enterDosage = (JTextField) dsgAmtPanel.getComponent(1);
        enterDosage.setPreferredSize(new Dimension(120, 20));
        dsgAmtErrorMsg = (JLabel) dsgAmtPanel.getComponent(2);
        dsgAmtErrorMsg.setVisible(false);
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
                        enterDosage.setText("");
                        dsgAmtErrorMsg.setVisible(true);
                        validDsgAmt = false;
                    }
                    else
                    {
                        dsgAmtErrorMsg.setVisible(false);
                        validDsgAmt = true;
                        isValidPrescription();
                    }
                    
                    objPresc.setDosage(new Dosage(dosageEntered));
                }
                catch(NumberFormatException nfe)
                {
                    enterDosage.setText("");
                    dsgAmtErrorMsg.setVisible(true);
                }
            }
        });
        this.add(dsgAmtPanel);
        // Dosage Frequency Panel
        PrescriptionComponent makeDsgFreqPanel = new PrescriptionComponent();
        dsgFreqPanel = makeDsgFreqPanel.CreatePrescComponents("Dosage Frequency: ", "Frequency of dosage e.g (3 times a week)", "This field cannot be empty, please try again!");
        enterDosageFreq = (JTextField) dsgFreqPanel.getComponent(1);
        enterDosageFreq.setPreferredSize(new Dimension(240, 20));
        dsgFreqErrorMsg = (JLabel) dsgFreqPanel.getComponent(2);
        dsgFreqErrorMsg.setVisible(false);
        enterDosageFreq.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                String dsgFreq = enterDosageFreq.getText();
                if(dsgFreq.length() == 0)
                {
                    dsgFreqErrorMsg.setVisible(true);
                    validDsgFreq = false;
                }
                else
                {
                    dsgFreqErrorMsg.setVisible(false);
                    validDsgFreq = true;
                    objPresc.setDosage(new Dosage(dsgFreq));
                    isValidPrescription();
                }
            }
        });
        this.add(dsgFreqPanel);
        // Repeat meds Panel T/F
        PrescriptionComponent makeRepMedsPanel = new PrescriptionComponent();
        repMedsPanel = makeRepMedsPanel.CreatePrescComponents("Repeat Prescription: ", "True/False OR T/F", "Not a boolean, please try again!");
        enterRepMeds = (JTextField) repMedsPanel.getComponent(1);
        enterRepMeds.setPreferredSize(new Dimension(110, 20));
        repMedsErrorMsg = (JLabel) repMedsPanel.getComponent(2);
        repMedsErrorMsg.setVisible(false);
        enterRepMeds.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                if(enterRepMeds.getText().length() == 0)
                {
                    enterRepMeds.setText("");
                    repMedsErrorMsg.setVisible(true);
                    validMedsRep = false;
                }
                else if(enterRepMeds.getText().equalsIgnoreCase("true"))
                {
                    repMedsErrorMsg.setVisible(false);
                    objPresc.setRepeat(true);
                    validMedsRep = true;
                    isValidPrescription();
                }
                else if(enterRepMeds.getText().equals("false"))
                {
                    repMedsErrorMsg.setVisible(false);
                    objPresc.setRepeat(false);
                    validMedsRep = true;
                    isValidPrescription();
                }
                else if(enterRepMeds.getText().toLowerCase().toCharArray()[0] == 't')
                {
                    repMedsErrorMsg.setVisible(false);
                    objPresc.setRepeat(true);
                    validMedsRep = true;
                    isValidPrescription();
                }
                else if(enterRepMeds.getText().toLowerCase().toCharArray()[0] == 'f')
                {
                    repMedsErrorMsg.setVisible(false);
                    objPresc.setRepeat(false);
                    validMedsRep = true;
                    isValidPrescription();
                }
                else
                {
                    enterRepMeds.setText("");
                    repMedsErrorMsg.setVisible(true);
                    validMedsRep = false;
                }
            }
        });
        this.add(repMedsPanel);
        //Confirm Prescription Panel
        savePanel = new JPanel();
        savePresc = new JButton("Save and Exit");
        JLabel saved = new JLabel("Saved Successfully!");
        saved.setVisible(false);
        savePresc.setEnabled(false);
        savePanel.add(saved);
        savePresc.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                try 
                {
                    String originalPresc = objPresc.toString();
                    String cleanPresc = "";
                    Scanner scan = new Scanner(originalPresc);
                    scan.useDelimiter("\\'\\'*");
                    while(scan.hasNext())
                    {
                        cleanPresc += scan.next();
                    }
                    
                    DatabaseConnection dbc = new DatabaseConnection();
                    String sqlQuery = "SELECT PRESCRIPTIONNO FROM PRESCRIPTIONS WHERE NHI = \'" + enterNHI.getText() + "\'";
                    
                    PreparedStatement prepstmt = dbc.getConnectionPatients().prepareStatement(sqlQuery);
                    ResultSet rs = prepstmt.executeQuery();
                    int rsPrescNo = 1;
                    
                    while(rs.next())
                    {
                        rsPrescNo = rs.getInt(1);
                        
                        if(rsPrescNo == 0)
                        {
                            rsPrescNo = 1;
                        }
                        rsPrescNo++;
                    }
                    
                    sqlQuery = "INSERT INTO ADMIN1.PRESCRIPTIONS (NHI, PRESCRIPTIONNO, PRESCRIPTION_DETAILS) VALUES (" + "\'" + enterNHI.getText() + "\'," + rsPrescNo + ",\'" + cleanPresc +"\')";
                    prepstmt = dbc.getConnectionPatients().prepareStatement(sqlQuery);
                    prepstmt.executeUpdate();
                    
                    saved.setVisible(true);
                    
                    JPanel confirmation = new JPanel();
                    confirmation.setMaximumSize(new Dimension(frame.getWidth(), 30));
                    confirmation.setBackground(Color.GREEN);
                    confirmation.add(new JLabel("Prescription Saved"));
                    frame.remove(createPrescPanel);
                    frame.remove(prescPanel);

                    try 
                    {
                        frame.add(confirmation);
                        frame.add(new MenuIconsPanel(frame));
                        Timer t = new Timer();
                        t.schedule(new TimerTask()
                        {
                            @Override
                            public void run() 
                            {
                               frame.remove(confirmation);
                               frame.revalidate();
                            }
                            
                        }, 3000);
                    } 
                    catch (IOException ex)
                    {
                        ex.printStackTrace();
                    }
                      
                } 
                catch (SQLException ex) 
                {
                    ex.printStackTrace();
                }
            }
        });
        savePanel.add(savePresc);
        this.add(savePanel);
        //Size of CreatePrescriptionPanel
        this.setPreferredSize(new Dimension(screenSize.width/2, screenSize.height/2));
    }
    
    public void isValidPrescription()
    {
        if(validDocName && validNHI && validMedNo && validDsgAmt && validDsgFreq && validMedsRep) 
        {
            savePresc.setEnabled(true);
        } 
    }
}
