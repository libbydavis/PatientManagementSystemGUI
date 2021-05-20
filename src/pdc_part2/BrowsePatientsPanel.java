/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdc_part2;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class BrowsePatientsPanel extends JPanel {
    BrowsePatientsPanel panel = this;
//    public static Connection conn;
//    public static String url = "jdbc:derby://localhost:1527/PatientDB; create = true";
//    public static String username = "admin1";
//    public static String password = "admin123";

    public BrowsePatientsPanel(PatientManagementView frame, double width, double height) throws IOException, SQLException {
        //setLayout(new BorderLayout());
        setSize((int) width, (int) height);
        JTextField searchBox = new JTextField("enter NHI, patient name or DOB...");
        //searchBox.setSize(100, 100);
        add(searchBox);
        JButton searchButton = new JButton("search");
        add(searchButton);

        Patient allPatients = new Patient();
        this.add(allPatients.displayAllPatients());
    }
}
