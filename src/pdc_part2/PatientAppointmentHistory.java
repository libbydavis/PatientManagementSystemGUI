/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdc_part2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author libst
 * Panel that displays all of the appointment history of a patient in a JTable
 */
public class PatientAppointmentHistory extends JPanel{
    public PatientAppointmentHistory(MedicalPatient thisPatient, Dimension d) throws SQLException {
        //set layout
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);
        this.setPreferredSize(d);
        this.setMinimumSize(d);

       
        JLabel title = new JLabel("Appointment History For " + thisPatient.getfName() + " " + thisPatient.getlName());
        title.setFont((new Font("Arial", Font.BOLD, 24)));
        add(title);
        
        //set up table
        DefaultTableModel model = new DefaultTableModel();
        JTable patientSummaryTable = new JTable(model);

        model.addColumn("DATETIME");
        model.addColumn("REASONS");
        model.addColumn("MEASUREMENTS");
        model.addColumn("NOTES");

        ArrayList<Object[]> list = Appointment.displayAppointmentHistoryForPatient(thisPatient.getNHI());
        for (int i = 0; i < list.size(); i++) {
            model.addRow(list.get(i));   
        }
        
        add(patientSummaryTable);
    }
}
