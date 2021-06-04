/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdc_part2;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Components that are used to make some of the panels in the CreatePrescription classes
 * @author Raj
 */
public class PrescriptionComponent
{
    public PrescriptionComponent() 
    {
        
    }
    
    public JPanel CreatePrescComponents(String promptMsg, String txtField, String errorMsg)
    {
        JPanel objPanel = new JPanel();
        JLabel prompt = new JLabel(promptMsg);
        JTextField enter = new JTextField(txtField);
        JLabel error = new JLabel(errorMsg);
        error.setVisible(false);
        
        enter.addFocusListener(new FocusListener()
        {
            @Override
            public void focusGained(FocusEvent e) 
            {
                JTextField source = (JTextField) e.getComponent();
                source.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) 
            {
                
            }       
        });
        
        objPanel.add(prompt);
        objPanel.add(enter);
        objPanel.add(error);
        
        return objPanel;
    }    
}
