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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
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
    
    public AppointmentsController(PatientManagementView frame, AppointmentsPanel panel) {
        this.frame = frame;
        this.panel = panel;
        appointment1 = new Appointment();
    }
    
    public Appointment getAppointment() {
        return appointment1;
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
            try {
                    MenuIconsPanel menuIconsPanel = new MenuIconsPanel(frame);
                    frame.remove(panel);
                    frame.add(menuIconsPanel);
                    frame.revalidate();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
        }
        if (source == panel.getFinishAppointment()) {
            try {
                panel.patient1.saveAppointmentToDB(appointment1);
            } catch (SQLException ex) {
                Logger.getLogger(AppointmentsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if (source == form.getReasonButton()) {
            JFrame popUp = form.getReasonPopUp();
            popUp.setVisible(true);
            form.revalidate();
        }
        else if (source == form.getMeasureButton()) {
            JFrame popUp = form.getMeasurementsPopup();
            popUp.setVisible(true);
            form.revalidate();
        }
        else if (source == form.getNoteButton()) {
            JFrame popUp = form.getNotesPopup();
            popUp.setVisible(true);
            form.revalidate();
        }
    }
    
}
