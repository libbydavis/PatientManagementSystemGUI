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
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;



/**
 *
 * @author libst
 */
public class AppointmentsHistoryPanel extends JPanel{
    private AppointmentsController controller;
    
    public AppointmentsHistoryPanel(int width, int height, AppointmentsController controller) throws SQLException {
        this.controller = controller;
        controller.addHistoryPane(this);

        //set layout
        //setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        GridBagConstraints c = new GridBagConstraints();
        this.setPreferredSize(new Dimension(width, height/2));
        this.setMinimumSize(new Dimension(width, height/2));
        
        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);
        
        model.addColumn("FIRSTNAME");
        model.addColumn("LASTNAME");
        model.addColumn("NHI");
        model.addColumn("NO_APPOINTMENTS");
        
        ArrayList<Object[]> list = Appointment.displayAppointmentHistorySummary();
        for (int i = 0; i < list.size(); i++) {
            model.addRow(list.get(i));   
        }
        
        add(table);
    }

}