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
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Raj
 */
class AddPatientsView extends JPanel {

    AddPatientsView addPatView = this;
    String newNhi;
    boolean validName, validAge, validPhoneNo, validStreet, validCond, validMeasure, validMeds;
    JButton addPatient;
    AddPatientController adp = new AddPatientController();

    public AddPatientsView(PatientManagementView frame, PatientsPanel patPanel, double width, double height) throws IOException, SQLException {
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
        addPatient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    adp.newPat.insertPatientToDatabase(adp.newPat);
                    JPanel confirmation = Confirmation.createConfirmation("Appointment Saved", frame);
                    frame.remove(addPatView);
                    frame.remove(patPanel);
                    frame.add(new MenuIconsPanel(frame));
                    frame.revalidate();
                    Timer t = new Timer();
                    t.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            Confirmation.removeConfirmation(frame, confirmation);
                        }
                    }, 3000);
                    /*
                    JPanel confirmation = Confirmation.createConfirmation("Patient Saved", frame);
                    frame.remove(addPatView);
                    frame.remove(patPanel);
                    //frame.add(new MenuIconsPanel(frame));
                    frame.revalidate();
                    Timer t = new Timer();
                    */
                    /*
                    t.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            frame.remove(confirmation);
                            frame.revalidate();
                        }
                    }, 3000);
                    */
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    Logger.getLogger(AddPatientsView.class.getName()).log(Level.SEVERE, null, ex);
                }
                    
            }
        });
                    
        saveAndExitPanel.add(addPatient);
        this.add(saveAndExitPanel);

        this.setPreferredSize(new Dimension(screenSize.width/2, screenSize.height/2));
    }

    /**
        *
        *
        * @return 
        **/
    public JPanel namePanel() {
        AddPatientsModel makeNamePanel = new AddPatientsModel("Enter patient's full name:", "e.g. John Smith", "Incorrect input, please try again!");
        makeNamePanel.clearTextField();
        makeNamePanel.getEnterValues().setPreferredSize(new Dimension(70, 20));
        makeNamePanel.getEnterValues().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean nullStr = adp.checkNullString(makeNamePanel);

                if (!nullStr) {
                    adp.setName(makeNamePanel);
                    validName = true;
                } else {
                    validName = false;
                }

                enableButnIfValidPatient();
            }
        });
        return makeNamePanel.combineComponents();
    }

    /**
        *
        *
        * @return 
        */
    public JPanel agePanel() {
        AddPatientsModel makeAgePanel = new AddPatientsModel("Enter patient's age:", "e.g. 12", "Incorrect input, please try again!");
        makeAgePanel.clearTextField();
        makeAgePanel.getEnterValues().setPreferredSize(new Dimension(50, 20));
        makeAgePanel.getEnterValues().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean correctAge = adp.validateNum(makeAgePanel);

                if (correctAge) {
                    adp.setAge(makeAgePanel);
                    validAge = true;
                } else {
                    validAge = false;
                }

                enableButnIfValidPatient();
            }
        });
        return makeAgePanel.combineComponents();
    }

    /**
        *
        * @return 
        **/
    public JPanel phoneNoPanel() {
        AddPatientsModel makePhoneNoPanel = new AddPatientsModel("Enter patient's phone number:", "e.g. 0212345678", "Incorrect input, please try again!");
        makePhoneNoPanel.clearTextField();
        makePhoneNoPanel.getEnterValues().setPreferredSize(new Dimension(100, 20));
        makePhoneNoPanel.getEnterValues().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean realNum = adp.validateNum(makePhoneNoPanel);

                if (realNum) {
                    Integer phoneNo = Integer.parseInt(makePhoneNoPanel.getEnterValues().getText()); //ensures what's entered is a number
                    adp.newPat.setPhoneNumber(phoneNo.toString());
                    validPhoneNo = true;
                } else {
                    validPhoneNo = false;
                }

                enableButnIfValidPatient();
            }
        });
        return makePhoneNoPanel.combineComponents();
    }

    /**
     *
     * @return 
        *
     */
    public JPanel streetPanel() {
        AddPatientsModel makeStreetPanel = new AddPatientsModel("Enter patient's street address:", "e.g. 123 Fake St", "Incorrect input, please try again!");
        makeStreetPanel.clearTextField();
        makeStreetPanel.getEnterValues().setPreferredSize(new Dimension(70, 20));
        makeStreetPanel.getEnterValues().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean nullStr = adp.checkNullString(makeStreetPanel);

                if (!nullStr) {
                    adp.setStreet(makeStreetPanel);
                    validStreet = true;
                } else {

                    validStreet = false;
                }

                enableButnIfValidPatient();
            }
        });
        return makeStreetPanel.combineComponents();
    }

    /**
     *
     * @return
     */
    public JPanel currentMedsPanel() {
        HashSet currentMeds = new HashSet();
        JPanel medPanel = new JPanel();
        JLabel promptMsg = new JLabel("Select your current medication: ");
        medPanel.add(promptMsg);
        JComboBox[] medBoxes = new JComboBox[4];

        for (int i = 0; i < medBoxes.length; i++) {
            medBoxes[i] = new JComboBox(Medication.medList());
        }

        for (JComboBox jcb : medBoxes) {
            jcb.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    adp.setMedication(jcb, currentMeds);
                }
            });
            medPanel.add(jcb);
        }
        return medPanel;
    }

    /**
     *
     * @return 
        *
     */
    public JPanel conditionsPanel() {
        JPanel objJPanel = new JPanel();
        AddPatientsModel makeStreetPanel = new AddPatientsModel("Enter your current conditions:", "e.g. High blood pressure", "Incorrect input, please try again!");
        makeStreetPanel.clearTextField();
        makeStreetPanel.getEnterValues().setPreferredSize(new Dimension(215, 20));
        makeStreetPanel.getEnterValues().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                makeStreetPanel.getErrorMsg().setVisible(true);
            }
        });
        return makeStreetPanel.combineComponents();
    }

    /**
     *
     * @return 
        *
     */
    public JPanel measurementsPanel() {

        AddPatientsModel makeStreetPanel = new AddPatientsModel("Enter patient's measurements:", "e.g. weight: 63 kgs", "Incorrect input, please try again!");
        makeStreetPanel.clearTextField();
        makeStreetPanel.getEnterValues().setPreferredSize(new Dimension(215, 20));
        makeStreetPanel.getEnterValues().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                makeStreetPanel.getErrorMsg().setVisible(true);
            }
        });
        return makeStreetPanel.combineComponents();
    }

    /**
     *
     * @return @throws SQLException 
        *
     */
    public JPanel nhiPanel() throws SQLException {
        newNhi = adp.genNhi();
        adp.newPat.setNHI(newNhi);
        JLabel genNhi = new JLabel("Auto-Generated NHI for this patient:  " + newNhi);
        JPanel nhiPanel = new JPanel();
        nhiPanel.add(genNhi);
        return nhiPanel;
    }

    /**
     *
     * @return 
        *
     */
    private boolean isValidPatient() {
        return validName && validAge && validPhoneNo && validStreet;
        //return validName && validAge && validPhoneNo && validStreet && validCond && validMeasure;
    }

    /**
     *
     */
    public void enableButnIfValidPatient() {
        if (isValidPatient() == true) {
            addPatient.setEnabled(true);
        }
    }
}
