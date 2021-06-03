package pdc_part2;

import javax.swing.*;
import java.awt.*;

/**
* @author -LibbyDavis
* Header panel for home page
*/

public class MainMenuPanel extends JPanel {
    int width;
    int height;
    private JLayeredPane layeredPane;

    public MainMenuPanel(double width, double height) {
        this.width = (int) width;
        this.height = (int) height;


        this.setLayout(new BorderLayout());
        Dimension d = new Dimension(this.width, 200);
        setMaximumSize(d);
        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(d);

        GradientRect gr = new GradientRect(this.width, this.height);
        layeredPane.add(gr);

        JLabel title = new JLabel("Patient Management System");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 45));
        title.setSize(650, 200);
        title.setLocation(20, 0);
        layeredPane.add(title, 2, 10);

        add(layeredPane, BorderLayout.CENTER);
    }

}


