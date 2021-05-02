package pdc_part2;

import javax.swing.*;
import java.awt.*;

public class PatientsPanel extends JPanel {
    public PatientsPanel(double width, double height) {
        setLayout(new BorderLayout());

        JTextField searchBox = new JTextField("enter NHI, patient name or DOB...");
        add(searchBox, BorderLayout.CENTER);
        JButton searchButton = new JButton("search");
        add(searchButton, BorderLayout.EAST);
    }
}
