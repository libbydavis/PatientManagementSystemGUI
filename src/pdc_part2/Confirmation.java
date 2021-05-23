/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdc_part2;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author libst
 */
public class Confirmation {
    public static void createConfirmation(String confirmationMessage, PatientManagementView frame) {
        JPanel confirmation = new JPanel();
        confirmation.setMaximumSize(new Dimension(frame.getWidth(), 30));
        confirmation.setBackground(Color.GREEN);
        confirmation.add(new JLabel(confirmationMessage));
        frame.add(confirmation);
    }
    
    public static int createErrorMessage(String errorMessage, String errorType, int type) {
        int error = JOptionPane.showConfirmDialog(null, errorMessage, errorType,
                type, JOptionPane.ERROR_MESSAGE);
        System.out.println(error);
        return error;
    }
}
