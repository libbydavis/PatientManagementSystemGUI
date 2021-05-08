/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdc_part2;

import java.awt.Dimension;
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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class PrescriptionPanel extends JPanel {
    PrescriptionPanel panel = this;
    public static Connection conn;
    public static String url = "jdbc:derby://localhost:1527/MedicationDB; create = true";    
    public static String username = "admin1";
    public static String password = "admin123";
    DefaultTableModel model = new DefaultTableModel();
    JTable table = new JTable(model);
    JPanel jp = new JPanel();
    
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
                }
            }
        });
        add(backButton);
        
        
        model.addColumn("MEDNO");
        model.addColumn("MEDNAME");
        model.addColumn("SIDE_EFFECTS");
        model.addColumn("CONDITIONS");
 
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(1).setPreferredWidth(100);
        columnModel.getColumn(2).setPreferredWidth(905);
        columnModel.getColumn(3).setPreferredWidth(845);
 
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
        catch (SQLException e) 
        {
            e.printStackTrace();
        }

        JScrollPane jsp = new JScrollPane(table);
        jsp.setPreferredSize(new Dimension(1850, 600));
        this.add(jsp);
    }
}

