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
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
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
public class CreatePrescriptionPanel extends JPanel
{
    CreatePrescriptionPanel createPrescPanel = this;
    JPanel docPanel, nhiPanel, medNoPanel, repMedsPanel, timeDatePanel, savePanel, confirmedPrescPanel, dsgAmtPanel, dsgFreqPanel;
    JTextField enterDocName, enterNHI, enterMedNo, enterRepMeds, enterDosage, enterDosageFreq;// enter rep meds must be T/F
    JLabel timeDate, nhiPatName, docNamePrompt, patNHIPrompt, medNoPrompt, repMedsPrompt, dsgAmtPrompt, dsgFreqPrompt, docNameErrorMsg, medNoErrorMsg, nhiErrorMsg, dsgAmtErrorMsg, dsgFreqErrorMsg, repMedsErrorMsg;
    boolean validDocName, validNHI, validMedNo, validDsgAmt, validDsgFreq, validMedsRep;
    JButton savePresc;
    JComboBox allNhi;
    Prescription objPresc = new Prescription();
    
    public CreatePrescriptionPanel(PatientManagementView frame, PrescriptionPanel prescPanel) throws SQLException 
    {
        // General Panel Setup
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        this.add(timeDatePanel());
        this.add(docPanel());
        this.add(nhiPanel());
        this.add(medNamePanel());
        this.add(dsgAmtPanel());
        this.add(dsgFreqPanel());
        this.add(repMedsPanel());
        //Confirm Prescription Panel
        savePanel = new JPanel();
        savePresc = new JButton("Save and Exit");
        JLabel saved = new JLabel("Saved Successfully!");
        saved.setVisible(false);
        savePresc.setEnabled(false);
        savePanel.add(saved);
        savePresc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String originalPresc = objPresc.toString();
                    String cleanPresc = "";
                    Scanner scan = new Scanner(originalPresc);
                    scan.useDelimiter("\\'\\'*");
                    while (scan.hasNext()) {
                        cleanPresc += scan.next();
                    }

                    DatabaseConnection dbc = new DatabaseConnection();
                    String sqlQuery = "SELECT PRESCRIPTIONNO FROM PRESCRIPTIONS WHERE NHI = \'" + allNhi.getSelectedItem() + "\'";

                    PreparedStatement prepstmt = dbc.getConnectionPatients().prepareStatement(sqlQuery);
                    ResultSet rs = prepstmt.executeQuery();
                    int rsPrescNo = 1;

                    while (rs.next()) {
                        rsPrescNo = rs.getInt(1);

                        if (rsPrescNo == 0) {
                            rsPrescNo = 1;
                        }
                        rsPrescNo++;
                    }

                    sqlQuery = "INSERT INTO ADMIN1.PRESCRIPTIONS (NHI, PRESCRIPTIONNO, PRESCRIPTION_DETAILS) VALUES (" + "\'" + allNhi.getSelectedItem() + "\'," + rsPrescNo + ",\'" + cleanPresc + "\')";
                    prepstmt = dbc.getConnectionPatients().prepareStatement(sqlQuery);
                    prepstmt.executeUpdate();

                    saved.setVisible(true);

                    JPanel confirmation = Confirmation.createConfirmation("Prescription Saved", frame);
                    
                    frame.remove(createPrescPanel);
                    frame.remove(prescPanel);

                    try {
                        frame.add(new MenuIconsPanel(frame));
                        Timer t = new Timer();
                        t.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                frame.remove(confirmation);
                                frame.revalidate();
                            }

                        }, 3000);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        savePanel.add(savePresc);
        this.add(savePanel);
        this.setPreferredSize(new Dimension(screenSize.width/2, screenSize.height/2));
    }
    
    private JPanel timeDatePanel() {
        timeDatePanel = new JPanel();
        Date currentTimeDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy h:mm aa");
        objPresc.setDateTime(sdf.format(currentTimeDate));
        timeDate = new JLabel("Prescription made at: " + sdf.format(currentTimeDate));
        timeDatePanel.add(timeDate);
        return timeDatePanel;
    }
    
    private JPanel docPanel() {
        docPanel = new JPanel();
        JLabel docNamePrompt = new JLabel("Enter Doctor's Name: ");
        JTextField enterDocName = new JTextField("e.g John Smith");
        enterDocName.setPreferredSize(new Dimension(113, 20));
        JLabel docNameErrorMsg = new JLabel("Doctor's Name cannot be empty, please try again!");
        docNameErrorMsg.setForeground(Color.red);
        docNameErrorMsg.setVisible(false);
        JLabel docNameCorrMsg = new JLabel("Name entered successfully");
        docNameCorrMsg.setForeground(Color.GREEN);
        docNameCorrMsg.setVisible(false);
        enterDocName.addFocusListener(new FocusListener()
        {
  
            public void focusGained(FocusEvent e) {
                enterDocName.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                String docName = enterDocName.getText();
                if (docName.length() == 0) {
                    docNameErrorMsg.setVisible(true);
                    docNameCorrMsg.setVisible(false);
                    validDocName = false;
                } else {
                    docNameErrorMsg.setVisible(false);
                    docNameCorrMsg.setVisible(true);
                    validDocName = true;
                    objPresc.setDoctorName(enterDocName.getText().toString());
                }
                isValidPrescription();
            }
        
        });
        docPanel.add(docNamePrompt);
        docPanel.add(enterDocName);
        docPanel.add(docNameCorrMsg);
        docPanel.add(docNameErrorMsg);
        return docPanel;
    }
    
    private JPanel nhiPanel() throws SQLException {
        allNhi = new JComboBox(MedicalPatient.paitentNHIList().toArray());
        nhiPanel = new JPanel();
        patNHIPrompt = new JLabel("Enter Patient's NHI: ");
        nhiPanel.add(patNHIPrompt);
           
            allNhi.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (allNhi.getSelectedIndex() == 0) 
                    {
                        nhiErrorMsg.setVisible(true);
                        validNHI = false;
                    } else 
                    {
                        try {
                            DatabaseConnection dbc = new DatabaseConnection();
                            PreparedStatement prepstmt2 = dbc.getConnectionPatients().prepareStatement("SELECT FIRSTNAME, LASTNAME FROM PATIENTS WHERE NHI = \'" + allNhi.getSelectedItem() + "\'");
                            ResultSet rs2 = prepstmt2.executeQuery();
                            
                            while(rs2.next())
                            {
                                String patFName = rs2.getString("FIRSTNAME");
                                String patLName = rs2.getString("LASTNAME");
                                objPresc.setPatientName(patFName.concat(" " +patLName));
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(CreatePrescriptionPanel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        validNHI = true;
                    }
                    isValidPrescription();
                }

            });

        nhiPanel.add(allNhi);
        return nhiPanel;
    }

    private JPanel medNamePanel() {
        JComboBox medList = new JComboBox(Medication.medList());
        medNoPanel = new JPanel();
        medNoPrompt = new JLabel("Medicine Name: ");
        medNoPanel.add(medNoPrompt);
        medList.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                try 
                {
                    DatabaseConnection dbc = new DatabaseConnection();
                    PreparedStatement prepstmt = dbc.getConnectionMedication().prepareStatement("SELECT MEDNAME, SIDE_EFFECTS, CONDITIONS FROM MEDICATION WHERE MEDNAME = \'" + medList.getSelectedItem().toString() + "\'");
                    ResultSet rs = prepstmt.executeQuery();
                    
                    while (rs.next()) {
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
                catch (SQLException ex) 
                {
                    Logger.getLogger(CreatePrescriptionPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        });
        medNoPanel.add(medList);
        return medNoPanel;
    }
    
    private JPanel dsgAmtPanel() {
        PrescriptionComponent makeDsgAmtPanel = new PrescriptionComponent();
        dsgAmtPanel = makeDsgAmtPanel.CreatePrescComponents("Enter Medication Dosage: ", "Enter mg/ml to 2 d.p", "Incorrect format for dosage amount, please try again!");
        enterDosage = (JTextField) dsgAmtPanel.getComponent(1);
        enterDosage.setPreferredSize(new Dimension(120, 20));
        dsgAmtErrorMsg = (JLabel) dsgAmtPanel.getComponent(2);
        dsgAmtErrorMsg.setVisible(false);
        enterDosage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double dosageEntered = Double.parseDouble(enterDosage.getText());

                    if (dosageEntered <= 0.0) {
                        enterDosage.setText("");
                        dsgAmtErrorMsg.setVisible(true);
                        validDsgAmt = false;
                    } else {
                        dsgAmtErrorMsg.setVisible(false);
                        validDsgAmt = true;
                        isValidPrescription();
                    }

                    objPresc.setDosage(new Dosage(dosageEntered));
                } catch (NumberFormatException nfe) {
                    enterDosage.setText("");
                    dsgAmtErrorMsg.setVisible(true);
                }
            }
        });
        return dsgAmtPanel;
    }
    
    public JPanel dsgFreqPanel()
    {
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
        return dsgFreqPanel;
    }
    
    private JPanel repMedsPanel() {
        PrescriptionComponent makeRepMedsPanel = new PrescriptionComponent();
        repMedsPanel = makeRepMedsPanel.CreatePrescComponents("Repeat Prescription: ", "True/False OR T/F", "Not a boolean, please try again!");
        enterRepMeds = (JTextField) repMedsPanel.getComponent(1);
        enterRepMeds.setPreferredSize(new Dimension(110, 20));
        repMedsErrorMsg = (JLabel) repMedsPanel.getComponent(2);
        repMedsErrorMsg.setVisible(false);
        enterRepMeds.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (enterRepMeds.getText().length() == 0) {
                    enterRepMeds.setText("");
                    repMedsErrorMsg.setVisible(true);
                    validMedsRep = false;
                } else if (enterRepMeds.getText().equalsIgnoreCase("true")) {
                    repMedsErrorMsg.setVisible(false);
                    objPresc.setRepeat(true);
                    validMedsRep = true;
                    isValidPrescription();
                } else if (enterRepMeds.getText().equals("false")) {
                    repMedsErrorMsg.setVisible(false);
                    objPresc.setRepeat(false);
                    validMedsRep = true;
                    isValidPrescription();
                } else if (enterRepMeds.getText().toLowerCase().toCharArray()[0] == 't') {
                    repMedsErrorMsg.setVisible(false);
                    objPresc.setRepeat(true);
                    validMedsRep = true;
                    isValidPrescription();
                } else if (enterRepMeds.getText().toLowerCase().toCharArray()[0] == 'f') {
                    repMedsErrorMsg.setVisible(false);
                    objPresc.setRepeat(false);
                    validMedsRep = true;
                    isValidPrescription();
                } else {
                    enterRepMeds.setText("");
                    repMedsErrorMsg.setVisible(true);
                    validMedsRep = false;
                }
            }
        });
        return repMedsPanel;
    }
    
    private void isValidPrescription() {
        if (validDocName && validNHI && validMedNo && validDsgAmt && validDsgFreq && validMedsRep) {
            savePresc.setEnabled(true);
        }
    }
}
