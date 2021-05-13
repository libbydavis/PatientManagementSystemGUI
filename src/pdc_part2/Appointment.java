/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdc_part2;

import javax.swing.*;
import java.awt.*;

public class Appointment {
    private String[] reasons;
    private Measurements[] measurements;
    private String[] notes;

    public Appointment() {
        reasons = new String[10];
        measurements = new Measurements[10];
        notes = new String[10];
    }

    public Object[] expandCapacity(Object[] list) {
        Object[] newList = new Object[list.length * 2];
        for (int i = 0; i < list.length; i++) {
            newList[i] = list[i];
        }

        return newList;
    }

    public boolean setReasons(String r, JTextField rField) {
        if (r.length() < 2) {
            rField.setForeground(Color.RED);
            rField.setText("Please enter a reason");
            return false;
        }
        if (reasons[reasons.length-1] != null) {
            reasons = (String[]) expandCapacity(reasons);
        }
        for (int i = 0; i < reasons.length; i++) {
            if (reasons[i] == null) {
                reasons[i] = r;
                return true;
            }
        }
        return true;
    }

    public boolean setNotes(String n, JTextField nField) {
        if (n.length() < 2) {
            nField.setForeground(Color.RED);
            nField.setText("Please enter a note");
            return false;
        }
        if (notes[notes.length-1] != null) {
            notes = (String[]) expandCapacity(notes);
        }
        for (int i = 0; i < notes.length; i++) {
            if (notes[i] == null) {
                notes[i] = n;
                return true;
            }
        }
        return true;
    }

    public boolean setMeasurement(String name, String value, String unit, JTextField uField, JTextField nField, JTextField vField) {
        Measurements currentMeasurement = new Measurements(name, value, unit, uField, nField, vField);
        if (currentMeasurement.measurement == null) {
            return false;
        }
        if (measurements[measurements.length-1] != null) {
            measurements = (Measurements[]) expandCapacity(measurements);
        }

        for (int i = 0; i < measurements.length; i++) {
            if (measurements[i] == null) {
                measurements[i] = currentMeasurement;
                return true;
            }
        }
        return true;
    }

    public String[] getMeasurementArrayToString() {
        String[] measurementsStrings = new String[measurements.length];
        for (int i = 0; i < measurements.length; i++) {
            if (measurements[i] != null) {
                String measurementString1 = measurements[i].name + ": " + measurements[i].measurement + measurements[i].units;
                measurementsStrings[i] = measurementString1;
            }
            else {
                return measurementsStrings;
            }
        }
        return measurementsStrings;
    }

    public String[] getReasons() {
        return reasons;
    }

    public Measurements[] getMeasurements() {
        return measurements;
    }

    public String[] getNotes() {
        return notes;
    }
    
    public String toString() {
        String total = "Appointment:\n";
        total += "Reasons:\n";
        for (String reason : reasons) {
            total += reason + "\n";
        }
        total += "Measurements:\n";
        for (Measurements measure : measurements) {
            total += measure.name + ": " + measure.measurement + measure.units + "\n";
        }
        total += "Notes:\n";
        for (String note : notes) {
            total += note + "\n";
        }
        return total;
    }
}


