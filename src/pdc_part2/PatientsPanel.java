package pdc_part2;

import javax.swing.*;
import java.awt.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PatientsPanel extends JPanel {
    PatientsPanel panel = this;
    public PatientsPanel(PatientManagementView frame, double width, double height) {
        setLayout(new BorderLayout());
        JButton backButton = new JButton("Back");
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
        add(backButton, BorderLayout.WEST);
        JTextField searchBox = new JTextField("enter NHI, patient name or DOB...");
        add(searchBox, BorderLayout.CENTER);
        JButton searchButton = new JButton("search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object source = e.getSource();
                String testNHI = searchBox.getText();
                Connection conn = null;
                try {
                    conn = DriverManager.getConnection("jdbc:derby://localhost:1527/PatientDB; create=true", "admin1", "admin123");
                    Statement statement1 = conn.createStatement();
                    boolean found = statement1.execute("SELECT * FROM PATIENTS WHERE NHI=" + testNHI);
                    if (found) {
                        searchBox.setText("patient found");
                    }
                    else {
                        searchBox.setText("patient not found");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(PatientsPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });
        add(searchButton, BorderLayout.EAST);
    }
}
