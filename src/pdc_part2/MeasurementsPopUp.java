/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdc_part2;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;

/**
 *
 * @author libst
 */
public class MeasurementsPopUp extends JFrame {
    private static MeasurementsPopUp MeasurementsPopUpInstance;
        public static synchronized MeasurementsPopUp getMeasurementsPopUpInstance(Appointment current, JList measurementsList, Patient patient) {
            if (MeasurementsPopUpInstance == null) {
                MeasurementsPopUpInstance = new MeasurementsPopUp(current, measurementsList, patient);
            }
            return MeasurementsPopUpInstance;
        }
        public static synchronized void measurementClosePopUp() { 
        if (MeasurementsPopUpInstance != null) {
            MeasurementsPopUpInstance.setVisible(false);
            MeasurementsPopUpInstance = null;
        }
    }
    private MeasurementsPopUp(Appointment current, JList measurementsList, Patient patient) {
        super("Add Measurement");
        setSize(500, 300);
        setLocation(200, 200);
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel measurementTitle = new JLabel("Enter a new measurement");
        JLabel nameLabel = new JLabel("name/type:");
        JTextField nameField = new JTextField();
        nameField.addFocusListener(new FocusListener(){
            @Override
            public void focusGained(FocusEvent e){
                nameField.setForeground(Color.BLACK);
                nameField.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });
        JLabel valueLabel = new JLabel("value:");
        JTextField valueField = new JTextField();
        valueField.addFocusListener(new FocusListener(){
            @Override
            public void focusGained(FocusEvent e){
                valueField.setForeground(Color.BLACK);
                valueField.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });
        JLabel unitLabel = new JLabel("units:");
        JTextField unitField = new JTextField();
        unitField.addFocusListener(new FocusListener(){
            @Override
            public void focusGained(FocusEvent e){
                unitField.setForeground(Color.BLACK);
                unitField.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });
        JButton addMeasurement = new JButton("Add");
        addMeasurement.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean set = current.setMeasurement(nameField.getText(), valueField.getText(), unitField.getText(), unitField, nameField, valueField, patient);
                if (set == true) {
                    nameField.setText("");
                    valueField.setText("");
                    unitField.setText("");
                    String[] measurementsArray = current.getMeasurementArrayToString();
                    measurementsList.setListData(measurementsArray);
                }
            }
        });

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.2;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.insets = new Insets(0, 15, 0, 0);
        add(measurementTitle, c);

        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 1;
        add(nameLabel, c);
        c.gridx = 1;
        c.insets = new Insets(0, 0, 0, 10);
        add(nameField, c);

        c.gridy = 2;
        c.gridx = 0;
        c.insets = new Insets(0, 15, 0, 0);
        add(valueLabel, c);
        c.gridx = 1;
        c.insets = new Insets(0, 0, 0, 10);
        add(valueField, c);

        c.gridx = 0;
        c.gridy = 3;
        c.insets = new Insets(0, 15, 0, 0);
        add(unitLabel, c);
        c.gridx = 1;
        c.insets = new Insets(0, 0, 0, 10);
        add(unitField, c);

        c.gridx = 1;
        c.gridy = 4;
        c.weightx = 1;
        c.insets = new Insets(10, 0, 0, 15);
        c.fill = GridBagConstraints.PAGE_END;
        c.anchor = GridBagConstraints.EAST;
        add(addMeasurement, c);
    }

}
