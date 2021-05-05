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

public class PatientsPanel extends JPanel {
    PatientsPanel panel = this;
    public PatientsPanel(PatientManagementView frame, double width, double height) throws IOException {
        setLayout(new BorderLayout());
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
        searchBox.setSize(100, 100);
        add(searchBox, BorderLayout.CENTER);
        JButton searchButton = new JButton("search");
        add(searchButton, BorderLayout.EAST);
    }
}

