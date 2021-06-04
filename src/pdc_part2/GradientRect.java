package pdc_part2;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

/**
 * @author libst
 * Paints a gradient rectangle for the header on the home page
 */

public class GradientRect extends JComponent{
    int width;
    int height;

    public GradientRect(int width, int height) {
        this.width = width;
        this.height = height;

        this.setSize(width, height);
        this.setLocation(0, 0);
        setOpaque(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        Color darkBlue = new Color(18, 29, 94);
        Color purple = new Color(181, 118, 255);
        Point2D start = new Point2D.Float(0, 0);
        Point2D end = new Point2D.Float(width, 0);
        float[] dist = {0.0f, 1.0f};
        Color[] colors = {darkBlue, purple};

        LinearGradientPaint gp = new LinearGradientPaint(start, end, dist, colors);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setPaint(gp);
        g.fillRect(0, 0, width, 200);
    }
}

