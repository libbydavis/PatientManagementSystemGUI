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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
/**
 *
 * @author Raj
 */
public class CreatePrescriptionView extends JPanel
{
    CreatePrescriptionView createPrescPanel = this;
    JPanel savePanel;
    boolean validDocName, validNHI, validMedNo, validDsgAmt, validDsgFreq, validMedsRep;
    JButton savePresc;
    JComboBox allNhi;
    Dosage dsg;
    CreatePrescriptionController cpc = new CreatePrescriptionController();
    
    public CreatePrescriptionView(PatientManagementView frame, PrescriptionPanel prescPanel)
    {
        // General Panel Setup
        dsg = new Dosage();
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        this.add(timeDatePanel());
        this.add(docPanel());
        try {
            this.add(nhiPanel());
        } catch (SQLException ex) {
            Logger.getLogger(CreatePrescriptionView.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.add(medNamePanel());
        this.add(dsgAmtPanel());
        this.add(dsgFreqPanel());
        this.add(repMedsPanel());
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
                    cpc.objPresc.insertPrescToDatabase(cpc.objPresc, allNhi.getSelectedItem().toString());
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
         this.addMouseListener(new MouseListener()
        {
            @Override
            public void mouseClicked(MouseEvent e) {
                requestFocusInWindow();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        
        });
        this.setPreferredSize(new Dimension(screenSize.width/2, screenSize.height/2));
    }
    
    private JPanel timeDatePanel() {
        JPanel timeDatePanel = new JPanel();
        JLabel timeDate = new JLabel("Prescription made at: " + cpc.makeTimeDate());
        timeDatePanel.add(timeDate);
        return timeDatePanel;
    }
    
    private JPanel docPanel() {
        CreatePrescriptionModel makeDocPanel = new CreatePrescriptionModel("Enter Doctor's Name: ", "e.g John Smith", 
                "Doctor's Name cannot be empty, please try again!", "Name entered successfully");
        makeDocPanel.clearTextField();
        makeDocPanel.getEnterField().setPreferredSize(new Dimension(113, 20));
        makeDocPanel.getEnterField().addFocusListener(new FocusListener()
        {
            public void focusGained(FocusEvent e) {
                makeDocPanel.getEnterField().setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                String docName = makeDocPanel.getEnterField().getText();
                if (docName.length() == 0) {
                    makeDocPanel.getErrorLabel().setVisible(true);
                    makeDocPanel.getCorrLabel().setVisible(false);
                    validDocName = false;
                } else {
                    makeDocPanel.getErrorLabel().setVisible(false);
                    makeDocPanel.getCorrLabel().setVisible(true);
                    validDocName = true;
                    cpc.objPresc.setDoctorName(docName);
                }
                enableBtnIfValidPresc();
            }
        });
        return makeDocPanel.combineComponents();
    }
    
    private JPanel nhiPanel() throws SQLException {
        allNhi = new JComboBox(MedicalPatient.paitentNHIList().toArray());
        JPanel nhiPanel = new JPanel();
        JLabel patNHIPrompt = new JLabel("Enter Patient's NHI: ");
        nhiPanel.add(patNHIPrompt);

        allNhi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (allNhi.getSelectedIndex() == 0) {
                    validNHI = false;
                } else {
                    validNHI = true;
                    cpc.setPatName(allNhi.getSelectedItem().toString());
                }
                enableBtnIfValidPresc();
            }
        });

        nhiPanel.add(allNhi);
        return nhiPanel;
    }

    private JPanel medNamePanel() {
        JComboBox medList = new JComboBox(Medication.medList());
        JPanel medNoPanel = new JPanel();
        JLabel medNoPrompt = new JLabel("Medicine Name: ");
        medNoPanel.add(medNoPrompt);
        medList.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                cpc.setPatMeds(medList.getSelectedItem().toString());
                validMedNo = true;
                enableBtnIfValidPresc();
            }
            
        });
        medNoPanel.add(medList);
        return medNoPanel;
    }
    
    private JPanel dsgAmtPanel() {
        CreatePrescriptionModel makeDsgAmtPanel = new CreatePrescriptionModel("Enter Medication Dosage: ", "Enter mg/ml to 2 d.p", 
                "Incorrect format for dosage amount, please try again!", "Dosage Amount entered successfully");
        makeDsgAmtPanel.clearTextField();
        makeDsgAmtPanel.getEnterField().setPreferredSize(new Dimension(120, 20));
        makeDsgAmtPanel.getEnterField().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                makeDsgAmtPanel.getEnterField().setText("");
            }
            @Override
            public void focusLost(FocusEvent e) {
                try {
                    double dosageEntered = Double.parseDouble(makeDsgAmtPanel.getEnterField().getText());
                    if (dosageEntered <= 0.0) {
                        makeDsgAmtPanel.getEnterField().setText("");
                        makeDsgAmtPanel.getErrorLabel().setVisible(true);
                        makeDsgAmtPanel.getCorrLabel().setVisible(false);
                        validDsgAmt = false;
                    } else {
                        makeDsgAmtPanel.getErrorLabel().setVisible(false);
                        makeDsgAmtPanel.getCorrLabel().setVisible(true);
                        dsg.setAmount(dosageEntered);
                        cpc.objPresc.setDosage(dsg);
                        validDsgAmt = true;
                    }
                    enableBtnIfValidPresc();
                } catch (NumberFormatException nfe) {
                    makeDsgAmtPanel.getEnterField().setText("");
                    makeDsgAmtPanel.getErrorLabel().setVisible(true);
                    makeDsgAmtPanel.getCorrLabel().setVisible(false);
                    validDsgAmt = false;
                }
            }
        });
        return makeDsgAmtPanel.combineComponents();
    }
    
    public JPanel dsgFreqPanel()
    {
        CreatePrescriptionModel makeDsgFreqPanel = new CreatePrescriptionModel("Enter Dosage Frequency: ", "Frequency of dosage e.g (3 times a week)", 
                "This field cannot be empty, please try again!", "Dosage Frequency entered successfully!");
        makeDsgFreqPanel.getEnterField().setPreferredSize(new Dimension(240, 20));
        makeDsgFreqPanel.getEnterField().addFocusListener(new FocusListener()
        {
            @Override
            public void focusGained(FocusEvent e) {
                makeDsgFreqPanel.getEnterField().setText("");
            }
            @Override
            public void focusLost(FocusEvent e) {
                String dsgFreq = makeDsgFreqPanel.getEnterField().getText();
                if (dsgFreq.length() == 0) {
                    makeDsgFreqPanel.getCorrLabel().setVisible(false);
                    makeDsgFreqPanel.getErrorLabel().setVisible(true);
                    validDsgFreq = false;
                } else {
                    makeDsgFreqPanel.getCorrLabel().setVisible(true);
                    makeDsgFreqPanel.getErrorLabel().setVisible(false);
                    validDsgFreq = true;
                    dsg.setHowOften(dsgFreq);
                    cpc.objPresc.setDosage(dsg);
                }
                enableBtnIfValidPresc();
            }       
        });
        return makeDsgFreqPanel.combineComponents();
    }
    
    private JPanel repMedsPanel() {
        CreatePrescriptionModel makeRepMedsPanel = new CreatePrescriptionModel("Enter Prescription Repetition: ", "Enter a boolean T/F", 
                "Not a boolean, please try again!", "Prescription repeat entered successfully!");
        makeRepMedsPanel.clearTextField();
        makeRepMedsPanel.getEnterField().setPreferredSize(new Dimension(110, 20));
        makeRepMedsPanel.getEnterField().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                makeRepMedsPanel.getEnterField().setText("");
            }
            @Override
            public void focusLost(FocusEvent e) {
                boolean corrInput = cpc.isBoolean(makeRepMedsPanel.getEnterField().getText().toString());
                if (corrInput) {
                    makeRepMedsPanel.getCorrLabel().setVisible(true);
                    makeRepMedsPanel.getErrorLabel().setVisible(false);
                    validMedsRep = true;
                    cpc.objPresc.setRepeat(corrInput);
                } else {
                    makeRepMedsPanel.getCorrLabel().setVisible(false);
                    makeRepMedsPanel.getErrorLabel().setVisible(true);
                    validMedsRep = false;
                }
                enableBtnIfValidPresc();
            }
        });
        return makeRepMedsPanel.combineComponents();
    }
    
    private void enableBtnIfValidPresc() {
        if (validDocName && validNHI && validMedNo && validDsgAmt && validDsgFreq && validMedsRep) {
            savePresc.setEnabled(true);
        }
    }
}
