/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdc_part2;

import javax.swing.*;
import java.awt.*;

public class Measurements {
    String name;
    Double measurement;
    String units;

    public Measurements() {
        name = "";
        measurement = 0.0;
        units = "";
    }

    public Measurements(String name, String value, String units, JTextField uField, JTextField nField, JTextField vField) {
        try {
            this.measurement = Double.parseDouble(value);
            this.units = units;
            this.name = name;
        }
        catch (Exception e) {
            vField.setForeground(Color.RED);
            vField.setText("Please enter a number");
        }
        if (this.units == null) {
            uField.setForeground(Color.RED);
            uField.setText("Please enter the units here");
        }
        if (this.name == null) {
            nField.setForeground(Color.RED);
            nField.setText("Please enter a name");
        }
    }
    
    public String toString() {
        return name + ": " + measurement + units;
    }

}

