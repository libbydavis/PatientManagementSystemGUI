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
    
    /**
     * @author -LibbyDavis
     * @param confirmationMessage
     * @param frame
     * @returns JPanel
     * Adds a green confirmation popup to the main window
     * Then returns the confirmation panel so the calling method can remove it later on
     */
    public static JPanel createConfirmation(String confirmationMessage, PatientManagementView frame) {
        JPanel confirmation = new JPanel();
        confirmation.setMaximumSize(new Dimension(frame.getWidth(), 30));
        confirmation.setBackground(Color.GREEN);
        confirmation.add(new JLabel(confirmationMessage));
        frame.add(confirmation);
        return confirmation;
    }
    
    /**
     * @author -LibbyDavis
     * @param errorMessage
     * @param errorType
     * @param type
     * @return int
     * Creates and displays a JOptionPane with error message
     * You can specify the error message, error title and JOptionPane type (e.g. JOptionPane.OK_OPTION)
     */
    public static int createErrorMessage(String errorMessage, String errorType, int type) {
        int error = JOptionPane.showConfirmDialog(null, errorMessage, errorType,
                type, JOptionPane.ERROR_MESSAGE);
        System.out.println(error);
        return error;
    }
    
    public static void removeConfirmation(PatientManagementView frame, JPanel confirmation) {
        frame.remove(confirmation);
        frame.revalidate();
    }
}
