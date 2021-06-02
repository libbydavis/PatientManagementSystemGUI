package pdc_part2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JList;

/**
 *
 * @author libst
 */
public class MeasurementsPopUpController implements ActionListener{

    private Appointment current;
    private JList measurementsList;
    private MedicalPatient patient;
    private AppointmentsForm form;
    
    public MeasurementsPopUpController(Appointment current, JList measurementsList, MedicalPatient patient, AppointmentsForm form) {
        this.current = current;
        this.measurementsList = measurementsList;
        this.patient = patient;
        this.form = form;
    }
    
    public MeasurementsPopUpController() {
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == form.getMPopUp().addMeasurement) {
             boolean set = current.setMeasurement(form.getMPopUp().nameField.getText(), form.getMPopUp().valueField.getText(), form.getMPopUp().unitField.getText(), form.getMPopUp().unitField, form.getMPopUp().nameField, form.getMPopUp().valueField, patient);
            if (set == true) {
                    form.getMPopUp().nameField.setText("");
                    form.getMPopUp().valueField.setText("");
                    form.getMPopUp().unitField.setText("");
                    String[] measurementsArray = current.getMeasurementArrayToString();
                    measurementsList.setListData(measurementsArray);
                }
        }
    }
    
}
