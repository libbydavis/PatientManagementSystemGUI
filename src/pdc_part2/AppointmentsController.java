/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdc_part2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
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
    
    public AppointmentsController(PatientManagementView frame, AppointmentsPanel panel) {
        this.frame = frame;
        this.panel = panel;
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
