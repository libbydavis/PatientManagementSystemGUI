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
    
    /**
        * This constructor is used to display all the components required to make an Add Patient Panel
        * @param frame The add patient panel is added to the frame
        * @param patPanel The panel is added to the patient panel
        * @param width gets the width of the frame
        * @param height gets the height of the frame
        * @throws IOException
        * @throws SQLException 
        **/
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
                    adp.newPat.updateMeasurements();
                    JPanel confirmation = Confirmation.createConfirmation("Patient Saved", frame, Color.GREEN);
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
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    Logger.getLogger(AddPatientsView.class.getName()).log(Level.SEVERE, null, ex);
                }                 
            }
        });
                    
        saveAndExitPanel.add(addPatient);
        this.add(saveAndExitPanel);
        this.addMouseListener(new MouseListener()
        {
            @Override
            public void mouseClicked(MouseEvent e) {
               requestFocusInWindow();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                
            }

            @Override
            public void mouseEntered(MouseEvent e) {
               
            }

            @Override
            public void mouseExited(MouseEvent e) {
               
            }     
        });
        this.setPreferredSize(new Dimension(screenSize.width/2, screenSize.height/2));
    }

    public JPanel namePanel() {
        AddPatientsModel makeNamePanel = new AddPatientsModel("Enter patient's full name:", "e.g. John Smith", "Incorrect input, please try again!", "Name entered successfully!");
        makeNamePanel.clearTextField();
        makeNamePanel.getEnterValues().setPreferredSize(new Dimension(70, 20));
        makeNamePanel.getEnterValues().addFocusListener(new FocusListener()
        {
            @Override
            public void focusGained(FocusEvent e) {
                
            }

            @Override
            public void focusLost(FocusEvent e) {
                   boolean nullStr = adp.checkNullString(makeNamePanel);

                if (!nullStr) {
                    makeNamePanel.getCorrMsg().setVisible(true);
                    makeNamePanel.getErrorMsg().setVisible(false);
                    validName = true;
                    adp.setName(makeNamePanel);
                } else {
                    makeNamePanel.getCorrMsg().setVisible(false);
                    makeNamePanel.getErrorMsg().setVisible(true);
                    validName = false;
                }
                enableButnIfValidPatient();
            }     
        });
        return makeNamePanel.combineComponents();
    }

    public JPanel agePanel() {
        AddPatientsModel makeAgePanel = new AddPatientsModel("Enter patient's age:", "e.g. 12", "Incorrect input, please try again!", "Age entered successfully!");
        makeAgePanel.clearTextField();
        makeAgePanel.getEnterValues().setPreferredSize(new Dimension(50, 20));
        makeAgePanel.getEnterValues().addFocusListener(new FocusListener()
        {
            @Override
            public void focusGained(FocusEvent e) {
                makeAgePanel.getEnterValues().setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                boolean correctAge = adp.validateNum(makeAgePanel);

                if (correctAge) {
                    makeAgePanel.getCorrMsg().setVisible(true);
                    makeAgePanel.getErrorMsg().setVisible(false);
                    validAge = true;
                    adp.setAge(makeAgePanel);
                } else {
                    makeAgePanel.getCorrMsg().setVisible(false);
                    makeAgePanel.getErrorMsg().setVisible(true);
                    validAge = false;
                }
                enableButnIfValidPatient();
            } 
        });
        return makeAgePanel.combineComponents();
    }

    public JPanel phoneNoPanel() {
        AddPatientsModel makePhoneNoPanel = new AddPatientsModel("Enter patient's phone number:", "e.g. 0212345678", "Incorrect input, please try again!", "Phone Number entered successfully!");
        makePhoneNoPanel.clearTextField();
        makePhoneNoPanel.getEnterValues().setPreferredSize(new Dimension(100, 20));
        makePhoneNoPanel.getEnterValues().addFocusListener(new FocusListener()
        {
            @Override
            public void focusGained(FocusEvent e) {
                makePhoneNoPanel.getEnterValues().setText("");
            }

            @Override
            public void focusLost(FocusEvent e) { 
                boolean realNum = adp.validateNum(makePhoneNoPanel);
                
                if (realNum) {
                    Integer phoneNo = Integer.parseInt(makePhoneNoPanel.getEnterValues().getText()); 
                    makePhoneNoPanel.getCorrMsg().setVisible(true);
                    makePhoneNoPanel.getErrorMsg().setVisible(false);
                    validPhoneNo = true;
                    adp.newPat.setPhoneNumber(phoneNo.toString());
                } else {
                    makePhoneNoPanel.getCorrMsg().setVisible(false);
                    makePhoneNoPanel.getErrorMsg().setVisible(true);
                    validPhoneNo = false;
                }
                enableButnIfValidPatient();
            }
            
        });
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

    public JPanel streetPanel() {
        AddPatientsModel makeStreetPanel = new AddPatientsModel("Enter patient's street address:", "e.g. 123 Fake St", "Incorrect input, please try again!", "Street entered successfully!");
        makeStreetPanel.clearTextField();
        makeStreetPanel.getEnterValues().setPreferredSize(new Dimension(70, 20));
        makeStreetPanel.getEnterValues().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                makeStreetPanel.getEnterValues().setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                boolean nullStr = adp.checkNullString(makeStreetPanel);

                if (!nullStr) {
                    adp.setStreet(makeStreetPanel);
                    makeStreetPanel.getCorrMsg().setVisible(true);
                    makeStreetPanel.getErrorMsg().setVisible(false);
                    validStreet = true;
                } else {

                    makeStreetPanel.getCorrMsg().setVisible(false);
                    makeStreetPanel.getErrorMsg().setVisible(true);
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

    public JPanel measurementsPanel() {
        
        JPanel measurementsPanel = new JPanel();
        JButton addMeas = new JButton("Add Measurement");
        addMeas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MeasurementsPopUpController controller = new MeasurementsPopUpController(adp.newPat);
                MeasurementsPopUp popUp = MeasurementsPopUp.getMeasurementsPopUpInstance(controller);
                controller.setUpController(popUp);
                popUp.setVisible(true);
            }
        });
        
        
        measurementsPanel.add(addMeas);
        return measurementsPanel;
    }

    public JPanel nhiPanel() throws SQLException {
        newNhi = adp.genNhi();
        adp.newPat.setNHI(newNhi);
        JLabel genNhi = new JLabel("Auto-Generated NHI for this patient:  " + newNhi);
        JPanel nhiPanel = new JPanel();
        nhiPanel.add(genNhi);
        return nhiPanel;
    }

    private boolean isValidPatient() {
        return validName && validAge && validPhoneNo && validStreet;
    }

    public void enableButnIfValidPatient() {
        if (isValidPatient() == true) {
            addPatient.setEnabled(true);
        }
    }
}
