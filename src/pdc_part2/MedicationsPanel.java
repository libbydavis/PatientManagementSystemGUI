/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdc_part2;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author libst
 */
public class MedicationsPanel extends JPanel
{
    DefaultTableModel model;
    JTable table;
    JPanel jp;
    
    public MedicationsPanel(Dimension d) {
        model = new DefaultTableModel();
        table = new JTable(model);
        jp = new JPanel();
        
        model.addColumn("MEDNO");
        model.addColumn("MEDNAME");
        model.addColumn("SIDE_EFFECTS");
        model.addColumn("CONDITIONS");
 
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(1).setPreferredWidth(100);
        columnModel.getColumn(2).setPreferredWidth(400);
        columnModel.getColumn(3).setPreferredWidth(650);
 
        try 
        {
            DatabaseConnection medDBC = new DatabaseConnection();
            PreparedStatement prepstmt = medDBC.getConnectionMedication().prepareStatement("SELECT * FROM MEDICATION");
            ResultSet rs = prepstmt.executeQuery();

            while (rs.next()) 
            {
                model.addRow(new Object[]{rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)});      
            }
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
        }

        JScrollPane jsp = new JScrollPane(table);
        // Set preferred size so the width of jsp adds up to the width of all the column widths - it is necessary to be able to display columns correctly.
        jsp.setPreferredSize(new Dimension(1500, 135));
        this.add(jsp);
        this.setPreferredSize(d);
        this.setMinimumSize(d);     
    }
    
}
