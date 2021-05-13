/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdc_part2;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class PrescriptionPanel extends JPanel {

    PrescriptionPanel panel = this;
    public static Connection conn;
    public static String url = "jdbc:derby://localhost:1527/MedicationDB; create = true";
    public static String username = "admin1";
    public static String password = "admin123";
    DefaultTableModel model;
    JTable table;
    JPanel jp;
    JToggleButton viewMeds;
    JScrollPane jsp;
    JButton genPresc;

    public PrescriptionPanel(PatientManagementView frame) throws IOException {
        BufferedImage backImage = ImageIO.read(new File("src\\Images\\backButtonArrow.png"));
        JButton backButton = new JButton(new ImageIcon(backImage));
        backButton.setBorder(new EmptyBorder(10, 10, 10, 10));
        backButton.setContentAreaFilled(false);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object source = e.getSource();
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
        });
        add(backButton);
        // Button that allows you to view Medication
        viewMeds = new JToggleButton("View Medication");
        model = new DefaultTableModel();
        table = new JTable(model);
        jsp = new JScrollPane(table);
        jp = new JPanel();
        jp.add(jsp);
        // Column names
        TableColumnModel columnModel = table.getColumnModel();
        model.addColumn("MEDNO");
        model.addColumn("MEDNAME");
        model.addColumn("SIDE_EFFECTS");
        model.addColumn("CONDITIONS");
        // Setting column widths
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(1).setPreferredWidth(100);
        columnModel.getColumn(2).setPreferredWidth(450);
        columnModel.getColumn(3).setPreferredWidth(645);
        
        jp.setVisible(false);
        viewMeds.setLocation(100, 50);
        viewMeds.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                if (viewMeds.isSelected()) 
                {
                    jp.setVisible(true);
                    try 
                    {
                        conn = DriverManager.getConnection(url, username, password);
                        PreparedStatement prepstmt = conn.prepareStatement("SELECT * FROM MEDICATION");
                        ResultSet rs = prepstmt.executeQuery();

                        while (rs.next()) 
                        {
                            model.addRow(new Object[]{rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)});
                        }
                    } 
                    catch (SQLException ex) 
                    {
                        ex.printStackTrace();
                    }                
                } 
                else
                {
                    try 
                    {
                        conn = DriverManager.getConnection(url, username, password);
                        PreparedStatement prepstmt = conn.prepareStatement("SELECT * FROM MEDICATION");
                        ResultSet rs = prepstmt.executeQuery();

                        while (rs.next()) 
                        {
                            model.removeRow(0);
                        }
                    } 
                    catch (SQLException ex) 
                    {
                        ex.printStackTrace();
                    }
                    jp.setVisible(false);
                }
            }
        });
        this.add(viewMeds);
        
        genPresc = new JButton("Generate Prescription");
        this.add(genPresc);
        //Variables needed to display Medication Table.
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        jsp.setPreferredSize(new Dimension(1250, 135));
        this.add(jp);
        this.setSize((screenSize.width / 2), (screenSize.height / 2));
    }
}
