package pdc_part2;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
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

public class PatientsPanel extends JPanel {

    PatientsPanel panel = this;
    public static Connection conn;
    public static String url = "jdbc:derby://localhost:1527/PatientDB; create = true";
    public static String username = "admin1";
    public static String password = "admin123";
    DefaultTableModel model = new DefaultTableModel();
    JTable table = new JTable(model);
    JPanel jp = new JPanel();

    public PatientsPanel(PatientManagementView frame, double width, double height) throws IOException {
        //setLayout(new BorderLayout());
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
        add(backButton, BorderLayout.WEST);
        JTextField searchBox = new JTextField("enter NHI, patient name or DOB...");
        //searchBox.setSize(100, 100);
        add(searchBox, BorderLayout.CENTER);
        JButton searchButton = new JButton("search");
        add(searchButton, BorderLayout.EAST);
        
        model.addColumn("NHI");
        model.addColumn("FIRSTNAME");
        model.addColumn("LASTNAME");
        model.addColumn("AGE");
        model.addColumn("PHONENO");
        model.addColumn("STREET");
        model.addColumn("APPOINTMENT_HISTORY");
        model.addColumn("CONDITIONS");
        model.addColumn("CURRENTMEDS");
        model.addColumn("MEASUREMENTS");
        model.addColumn("PRESCRIPTIONS");
        
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(1).setPreferredWidth(80);
        columnModel.getColumn(2).setPreferredWidth(80);
        columnModel.getColumn(3).setPreferredWidth(30);
        columnModel.getColumn(4).setPreferredWidth(75);
        columnModel.getColumn(5).setPreferredWidth(120);
        columnModel.getColumn(6).setPreferredWidth(350);
        columnModel.getColumn(7).setPreferredWidth(300);
        columnModel.getColumn(8).setPreferredWidth(150);
        columnModel.getColumn(9).setPreferredWidth(300);
        columnModel.getColumn(10).setPreferredWidth(300);

        try 
        {
            conn = DriverManager.getConnection(url, username, password);
            PreparedStatement prepstmt = conn.prepareStatement("SELECT * FROM PATIENTS");
            ResultSet rs = prepstmt.executeQuery();

            while (rs.next()) 
            {
                model.addRow(new Object[]{rs.getString(3), rs.getString(1), rs.getString(2), rs.getInt(11), rs.getInt(4),
                    rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10)});
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
