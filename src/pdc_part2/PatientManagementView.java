package pdc_part2;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class PatientManagementView extends JFrame {
    double width;
    double height;

    public PatientManagementView(String title) throws IOException {
        super(title);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        Dimension size = Toolkit. getDefaultToolkit().getScreenSize();
        width = size.getWidth();
        height = (size.getHeight() - 20);
        this.setSize(size);

        MainMenuPanel mainMenu = new MainMenuPanel(width, height);
        mainMenu.setAlignmentY(SwingConstants.TOP);
        this.add(mainMenu);
        MenuIconsPanel menuIcons = new MenuIconsPanel(this);
        this.add(menuIcons);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

    }


    public static void main(String[] args) throws IOException {
        PatientManagementView frame = new PatientManagementView("Patient Management System");
        frame.setVisible(true);
    }


}

