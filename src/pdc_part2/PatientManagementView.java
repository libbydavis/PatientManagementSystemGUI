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

public class PatientManagementView extends JFrame {
    double width;
    double height;

    public PatientManagementView(String title) {
        super(title);
        Dimension size = Toolkit. getDefaultToolkit().getScreenSize();
        width = size.getWidth();
        height = size.getHeight();
        this.setSize(size);

        MainMenuPanel mainMenu = new MainMenuPanel(width, height);
        this.add(mainMenu);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);


    }


    public static void main(String[] args) {
        PatientManagementView frame = new PatientManagementView("Patient Management System");
        frame.setVisible(true);
    }


}
