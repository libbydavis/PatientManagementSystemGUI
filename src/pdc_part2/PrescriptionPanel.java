/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdc_part2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
    PrescriptionButtonsPanel buttonsPane;
    GridBagConstraints c;
    Dimension preferredD;
    
    public PrescriptionPanel(PatientManagementView frame, int width, int height) throws IOException {
        setLayout(new GridBagLayout());
        setBackground(new Color(18, 29, 94));
        c = new GridBagConstraints();
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        preferredD = new Dimension(screenSize.width/2, screenSize.height/2);
        BufferedImage backImage = ImageIO.read(new File("src\\Images\\backButtonArrow.png"));
        JButton backButton = new JButton(new ImageIcon(backImage));
        backButton.setBorder(new EmptyBorder(30, 30, 10, 10));
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

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        add(backButton, c);

        c.anchor = GridBagConstraints.CENTER;
        JLabel pTitle = new JLabel("Prescription");
        pTitle.setFont(new Font("Arial", Font.BOLD, 40));
        pTitle.setForeground(Color.WHITE);
        add(pTitle, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        buttonsPane = new PrescriptionButtonsPanel(frame, preferredD, this);
        add(buttonsPane, c);
    }
    
    public void removeButtonsPane() {
        remove(buttonsPane);
    }
    
    public void addPrescPane(JPanel pane) {
        add(pane, c);
    }

}
