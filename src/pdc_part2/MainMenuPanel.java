/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pdc_part2;

/**
 *
 * @author libst
 */
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

public class MainMenuPanel extends JPanel {
    int width;
    int height;
    private JLayeredPane layeredPane;

    public MainMenuPanel(double width, double height) {
        this.width = (int) width;
        this.height = (int) height;


        this.setLayout(new BorderLayout());
        Dimension d = new Dimension(this.width, this.height);
        this.setPreferredSize(d);
        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(d);

        GradientRect gr = new GradientRect(this.width, this.height);
        layeredPane.add(gr);

        JLabel title = new JLabel("Patient Management System");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Times New Roman", Font.BOLD, 40));
        title.setSize(500, 200);
        title.setLocation(20, 0);
        layeredPane.add(title, 2, 10);

        add(layeredPane, BorderLayout.CENTER);
    }

}

