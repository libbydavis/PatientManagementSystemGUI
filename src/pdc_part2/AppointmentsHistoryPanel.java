/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdc_part2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;



/**
 *
 * @author libst
 */
public class AppointmentsHistoryPanel extends JPanel{
    private AppointmentsHistoryController controller;
    private JButton selectPatientHistory;
    private JTable summaryTable;
    private Dimension d;
    private SummaryTablePanel summaryTablePanel;
    private static ArrayList<Object[]> summaryData;
    
    public AppointmentsHistoryPanel(int width, int height, AppointmentsHistoryController controller) throws SQLException {
        this.controller = controller;
        controller.addHistoryPane(this);

        //set layout
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        GridBagConstraints c = new GridBagConstraints();
        d = new Dimension(width, height/2);
        this.setPreferredSize(d);
        this.setMinimumSize(d);
        summaryTablePanel = new SummaryTablePanel();
        add(summaryTablePanel);
        
    }
    
    public SummaryTablePanel getSummaryTablePanel() {
        return summaryTablePanel;
    }
    
    public JButton getSelectPatientHistory() {
        return selectPatientHistory;
    }
    
    public JTable getSummaryTable() {
        return summaryTable;
    }
    
    public PatientAppointmentHistory getPatientHistoryPanel(MedicalPatient p) throws SQLException {
        return new PatientAppointmentHistory(p, d);
    }
    
    private class SummaryTablePanel extends JPanel{
        public SummaryTablePanel() throws SQLException {
            DefaultTableModel model = new DefaultTableModel();
            summaryTable = new JTable(model);

            model.addColumn("FIRSTNAME");
            model.addColumn("LASTNAME");
            model.addColumn("NHI");
            model.addColumn("NO_APPOINTMENTS");

            if (summaryData == null) {
                summaryData = Appointment.displayAppointmentHistorySummary();
            }
            for (int i = 0; i < summaryData.size(); i++) {
                model.addRow(summaryData.get(i));   
            }
                
            add(summaryTable);
            
            selectPatientHistory = new JButton("View Appointments");
            selectPatientHistory.addActionListener(controller);
            add(selectPatientHistory);
        }
    }

}