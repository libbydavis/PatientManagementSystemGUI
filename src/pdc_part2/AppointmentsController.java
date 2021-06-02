/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdc_part2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author libst
 */
public class AppointmentsController implements ActionListener{
    private PatientManagementView frame;
    private AppointmentsPanel panel;
    private AppointmentsForm form;
    private Appointment appointment1;
    private JPanel confirmation;
    private int error;
    private JList reasonsList;
    private JList measurementsList;
    private JList notesList;
    private AppointmentsController store = this;
    private JPanel appointmentsForm;
    private Timer t;
    
    public AppointmentsController(PatientManagementView frame, AppointmentsPanel panel) {
        this.frame = frame;
        this.panel = panel;
        appointment1 = new Appointment();
    }
    
    public Appointment getAppointment() {
        return appointment1;
    }
    
    public void setLists(JList reasons, JList measurements, JList notes) {
        this.reasonsList = reasons;
        this.measurementsList = measurements;
        this.notesList = notes;
    }
    
    public void setNHI(String NHI) {
        appointment1.NHI = NHI;
    }

    public void addPanel2(AppointmentsForm form) {
        this.form = form;
    }
    
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        
        if (source == panel.getBackButton() || source == panel.getFinishAppointment()) {
            closePopUps();
            int confirmationLeave = 0;
            
            if (source == panel.getBackButton() && appointmentsForm != null) {
                confirmationLeave = Confirmation.createErrorMessage("Are you sure you want to leave your unsaved changes?", "Leave Unsaved Appointment", JOptionPane.YES_NO_OPTION);
            }
            if (source == panel.getFinishAppointment()) {
            try {
                panel.patient1.saveAppointmentToDB(appointment1);
                confirmation = Confirmation.createConfirmation("Appointment Saved", frame);
                t = new Timer();
                t.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Confirmation.removeConfirmation(frame, confirmation);
                    }
                }, 3000);
            } catch (SQLException ex) {
                Logger.getLogger(AppointmentsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            if (confirmationLeave != JOptionPane.NO_OPTION) {
                try {
                        MenuIconsPanel menuIconsPanel = new MenuIconsPanel(frame);
                        frame.remove(panel);
                        frame.add(menuIconsPanel);
                        frame.revalidate();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        
        else if (source == panel.getCreateAppointmentB()) {
            
            appointmentsForm = new AppointmentsForm(panel.getWidth(), panel.getHeight(), store);
            panel.getPatient(panel.getTitleAP());
            if (!panel.getTitleAP().getText().equals("Appointment ")) {
                panel.removeButtons();
                //patient details
                
                PatientInfoPanel infoPanel = new PatientInfoPanel(panel.patient1);
                panel.addComponentAppointments(infoPanel);
                //appointment form
                panel.setAppointmentsFormContraints();
                panel.addComponentAppointments(appointmentsForm);
                panel.setConstraintsSaveButton();
                panel.addComponentAppointments(panel.getFinishAppointment());
                frame.revalidate();
            }
        }
        
        else if (source == panel.getViewHistoryB()) {
            JPanel historyPanel;
            try {
                historyPanel = new AppointmentsHistoryPanel(panel.getWidth(), panel.getHeight(), new AppointmentsHistoryController(store, frame));
                panel.setTitleAP("Appointment History Summary");
                panel.removeButtons();
                panel.addComponentAppointments(historyPanel);
                frame.revalidate();
                } catch (SQLException ex) {
                    Logger.getLogger(AppointmentsController.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
       
        else if (source == form.getReasonButton()) {
            JFrame popUp = form.getReasonPopUp();
            popUp.setVisible(true);
            MeasurementsPopUp.measurementClosePopUp();
            NotesPopUp.noteClosePopUp();
            form.revalidate();
        }
        else if (source == form.getDeleteReasonButton()) {
            int index = reasonsList.getSelectedIndex();
            if (index > -1) {
                appointment1.deleteReason(index);
                reasonsList.setListData(appointment1.getReasons());
            }
            else {
                Confirmation.createErrorMessage("Select a reason from the list then press the remove button to delete.",
                        "No Reason Selected", JOptionPane.DEFAULT_OPTION);
            }
        }
        else if (source == form.getMeasureButton()) {
            JFrame popUp = form.getMeasurementsPopup(panel.patient1);
            popUp.setVisible(true);
            ReasonPopUp.reasonClosePopUp();
            NotesPopUp.noteClosePopUp();
            form.revalidate();
        }
        else if (source == form.getDeleteMeasurementButton()) {
            int index = measurementsList.getSelectedIndex();
            if (index > -1) {
                appointment1.deleteMeasurement(index);
                measurementsList.setListData(appointment1.getMeasurementArrayToString());
            }
            else {
                Confirmation.createErrorMessage("Select a measurement from the list then press the remove button to delete.",
                        "No Measurement Selected", JOptionPane.DEFAULT_OPTION);
            }
        }
        else if (source == form.getNoteButton()) {
            JFrame popUp = form.getNotesPopup();
            popUp.setVisible(true);
            ReasonPopUp.reasonClosePopUp();
            MeasurementsPopUp.measurementClosePopUp();
            form.revalidate();
        }
        else if (source == form.getDeleteNoteButton()) {
            int index = notesList.getSelectedIndex();
            if (index > -1) {
                appointment1.deleteNote(index);
                notesList.setListData(appointment1.getNotes());
            }
            else {
                Confirmation.createErrorMessage("Select a note from the list then press the remove button to delete.",
                        "No Note Selected", JOptionPane.DEFAULT_OPTION);
            }
        }
        
        
    }
    
    
    public void closePopUps() {
        ReasonPopUp.reasonClosePopUp();
        MeasurementsPopUp.measurementClosePopUp();
        NotesPopUp.noteClosePopUp();
    }
}
