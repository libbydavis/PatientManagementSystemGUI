/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdc_part2;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Raj
 */
public class EditPrescriptionPanel extends JPanel 
{
    JPanel editPanel, tablePanel, removePanel;
    JLabel prompt, errorMsg;
    JTextField enterNHI;
    PrescriptionComponent objPC;
    JButton removeButton;

    public EditPrescriptionPanel() 
    {
        // General Setup
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        // Setting table up
        DefaultTableModel prescTableModel = new DefaultTableModel();
        prescTableModel.addColumn("PRESCRIPTIONNO");
        prescTableModel.addColumn("PRESCRIPTION_DETAILS");
        JTable prescTable = new JTable(prescTableModel);
        prescTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        // Edit Panel
        objPC = new PrescriptionComponent();
        editPanel = objPC.CreatePrescComponents("Enter NHI of the patient's prescription you want to delete: ", "e.g(tes123)", "This NHI does not have a prescripton, please try again!");
        enterNHI = (JTextField) editPanel.getComponent(1);
        enterNHI.setPreferredSize(new Dimension(70, 20));
        errorMsg = (JLabel) editPanel.getComponent(2);
        enterNHI.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                try 
                {
                    DatabaseConnection dbc = new DatabaseConnection();
                    Statement stmt = dbc.getConnectionPatients().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    ResultSet rs = stmt.executeQuery("SELECT PRESCRIPTIONNO, PRESCRIPTION_DETAILS FROM PRESCRIPTIONS WHERE NHI = \'" + enterNHI.getText().toLowerCase() + "\'");
                    
                    while(rs.next()) 
                    {
                        prescTableModel.addRow(new Object[]{rs.getString(1), rs.getString(2)});
                        errorMsg.setVisible(false);
                    } 
                    
                    rs.beforeFirst();
                    
                    if(!rs.next()) 
                    {
                        enterNHI.setText("");
                        errorMsg.setVisible(true);
                    }  
                } 
                catch (SQLException ex) 
                {
                    ex.printStackTrace();
                }
            }    
        });
        this.add(editPanel);
        // Table Panel
        tablePanel = new JPanel();
        JScrollPane prescJsp = new JScrollPane(prescTable);
        prescJsp.setPreferredSize(new Dimension(800, 300));
        this.add(tablePanel);
        tablePanel.add(prescJsp);
        //Remove Panel
        removePanel = new JPanel();
        removeButton = new JButton("Remove");
        removeButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                if(prescTable.getSelectedRow() != -1)
                {
                    prescTableModel.removeRow(prescTable.getSelectedRow());
                    String selected = (String) prescTableModel.getValueAt(prescTable.getSelectedRow(), 1);
                    System.out.println(selected);
                }
            }     
        });
        removePanel.add(removeButton);
        this.add(removePanel);
        // Edit Prescription Panel Size
        this.setPreferredSize(new Dimension(screenSize.width/2, screenSize.height/2));
    }
}
