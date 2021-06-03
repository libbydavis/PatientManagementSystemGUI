package pdc_part2;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author libst
 */
public class PatientInfoPanel extends JPanel{
    
    public PatientInfoPanel(MedicalPatient patient) {
        JLabel nhi = new JLabel("NHI: " + patient.getNHI());
        JLabel age = new JLabel("Age: " + patient.getAge());
        JLabel measurements = new JLabel("Measurements: " + patient.getMeasurementsString());
        
        add(nhi);
        add(age);
        add(measurements);
    }
}