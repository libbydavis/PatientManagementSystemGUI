/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdc_part2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author libst
 */
public class AppointmentsHistoryController implements ActionListener{
    private AppointmentsHistoryPanel historyPanel;
    private AppointmentsController appointmentsMainController;
    private PatientManagementView frame;
    
    public AppointmentsHistoryController(AppointmentsController appointmentsMainController, PatientManagementView frame) {
        this.appointmentsMainController = appointmentsMainController;
        this.frame = frame;
    }
    
    public void addHistoryPane(AppointmentsHistoryPanel historyPanel) {
        this.historyPanel = historyPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == historyPanel.getSelectPatientHistory()) {
            int row = historyPanel.getSummaryTable().getSelectedRow();
            if (row != -1) {
                try {
                    String nhi = historyPanel.getSummaryTable().getModel().getValueAt(row, 0).toString();
                    Patient currentPatient = new Patient();
                    currentPatient.getPatientFromDatabase(nhi, "NHI");
                    historyPanel.remove(historyPanel.getSummaryTablePanel());
                    historyPanel.add(historyPanel.getPatientHistoryPanel(currentPatient));
                    frame.revalidate();
                } catch (SQLException ex) {
                    Logger.getLogger(AppointmentsHistoryController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else {
              Confirmation.createErrorMessage("Please select a patient row to view patient's appointments", "No row selected", JOptionPane.DEFAULT_OPTION); 
            }
        }
    }
    
}
