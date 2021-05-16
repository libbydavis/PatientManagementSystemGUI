/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdc_part2;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

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
    private JList reasonsList;
    private JList measurementsList;
    private JList notesList;
    
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
            if (source == panel.getFinishAppointment()) {
            try {
                panel.patient1.saveAppointmentToDB(appointment1);
                createConfirmation();
                Timer t = new Timer();
                t.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        removeConfirmation();
                    }
                }, 3000);
            } catch (SQLException ex) {
                Logger.getLogger(AppointmentsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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
       
        else if (source == form.getReasonButton()) {
            JFrame popUp = form.getReasonPopUp();
            popUp.setVisible(true);
            form.revalidate();
        }
        else if (source == form.getDeleteReasonButton()) {
            int index = reasonsList.getSelectedIndex();
            if (index > -1) {
                appointment1.deleteReason(index);
                reasonsList.setListData(appointment1.getReasons());
            }
        }
        else if (source == form.getMeasureButton()) {
            JFrame popUp = form.getMeasurementsPopup();
            popUp.setVisible(true);
            form.revalidate();
        }
        else if (source == form.getDeleteMeasurementButton()) {
            int index = measurementsList.getSelectedIndex();
            if (index > -1) {
                appointment1.deleteMeasurement(index);
                measurementsList.setListData(appointment1.getMeasurements());
            }
        }
        else if (source == form.getNoteButton()) {
            JFrame popUp = form.getNotesPopup();
            popUp.setVisible(true);
            form.revalidate();
        }
        else if (source == form.getDeleteNoteButton()) {
            int index = notesList.getSelectedIndex();
            if (index > -1) {
                appointment1.deleteNote(index);
                notesList.setListData(appointment1.getNotes());
            }
        }
    }
    
    public void createConfirmation() {
        confirmation = new JPanel();
        confirmation.setMaximumSize(new Dimension(frame.getWidth(), 30));
        confirmation.setBackground(Color.GREEN);
        confirmation.add(new JLabel("Appointment Saved"));
        frame.add(confirmation);
    }
    
    public void removeConfirmation() {
        frame.remove(confirmation);
        frame.revalidate();
    }
}
