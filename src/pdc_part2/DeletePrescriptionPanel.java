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
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class DeletePrescriptionPanel extends JPanel {

    JPanel editPanel, tablePanel, removePanel;
    JLabel prompt, errorMsg;
    JTextField enterNHI;
    //PrescriptionComponent objPC;
    JButton removeButton;
    DeletePrescriptionPanel store = this;
    private String nhi;
    /**
     * Deletes a patient from the patient table
     * @throws SQLException 
     */
    public DeletePrescriptionPanel() throws SQLException 
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
        JPanel editPanel = new JPanel();
        JLabel prompt = new JLabel("Select a Patient: ");
        JButton addPatientButton = new JButton("Add Patient");
        addPatientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getPatientPopUp getPatient = new getPatientPopUp(prompt, store);
                getPatient.setVisible(true);
                tablePanel.setVisible(true);
                Prescription currentPrescriptions = new Prescription();
                try {
                    ResultSet rs = currentPrescriptions.getPrescriptions(nhi);
                    while(rs.next())
                    {
                        prescTableModel.addRow(new Object[]{rs.getString(1), rs.getString(2)});
                    }
                    rs.close();
                    addPatientButton.setText("Change Patient");
                } catch (SQLException ex) {
                    Logger.getLogger(DeletePrescriptionPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });
        editPanel.add(prompt);
        editPanel.add(addPatientButton);

        this.add(editPanel);
        // Table Panel
        tablePanel = new JPanel();
        JScrollPane prescJsp = new JScrollPane(prescTable);
        prescJsp.setPreferredSize(new Dimension(800, 300));
        tablePanel.add(prescJsp);
        this.add(tablePanel);
        tablePanel.setVisible(false);
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
                    String strPrescNo = prescTableModel.getValueAt(prescTable.getSelectedRow(), 0).toString();
                    int intPrescNo = Integer.parseInt(strPrescNo);
                    System.out.println(strPrescNo);
                    System.out.println(intPrescNo);
                    prescTableModel.removeRow(prescTable.getSelectedRow());
                    
                    Prescription removedPrescription = new Prescription();
                    try {
                        removedPrescription.removePrescription(intPrescNo);
                    } catch (SQLException ex) {
                        Logger.getLogger(DeletePrescriptionPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            });
            removePanel.add(removeButton);
            this.add(removePanel);
            // Edit Prescription Panel Size
            this.setPreferredSize(new Dimension(screenSize.width / 2, screenSize.height / 2));
    }
    
    public void setNhi(String nhi) {
        this.nhi = nhi;
    }
}
