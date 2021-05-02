package pdc_part2;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class PatientManagementView extends JFrame {
    double width;
    double height;

    public PatientManagementView(String title) throws IOException {
        super(title);
        setLayout(new GridLayout(4, 0));
        Dimension size = Toolkit. getDefaultToolkit().getScreenSize();
        width = size.getWidth();
        height = size.getHeight();
        this.setSize(size);

        MainMenuPanel mainMenu = new MainMenuPanel(width, height);
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
