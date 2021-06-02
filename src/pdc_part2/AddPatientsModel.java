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
 *
 * @author Raj
 */
public class AddPatientsModel 
{
    private JPanel panel;
    private JLabel enterMsg, errorMsg;
    private JTextField enterValues; 

    public AddPatientsModel(String enterMsg, String enterValuesTxt, String errorMsg) 
    {
        this.panel = new JPanel();
        this.enterMsg = new JLabel(enterMsg);
        this.enterValues = new JTextField(enterValuesTxt);
        this.errorMsg = new JLabel(errorMsg);
    }
    
    public JPanel getPanel() 
    {
        return panel;
    }

    public JLabel getEnterMsg() 
    {
        return enterMsg;
    }

    public JLabel getErrorMsg() 
    {
        return errorMsg;
    }

    public JTextField getEnterValues() 
    {
        return enterValues;
    }
    
    public JPanel combineComponents()
    {
        JPanel objPanel = getPanel();
        objPanel.add(getEnterMsg());
        objPanel.add(getEnterValues());
        getErrorMsg().setVisible(false);
        objPanel.add(getErrorMsg());
        return objPanel;
    }
    
    public void clearTextField()
    {
        JTextField objJtf = (JTextField) combineComponents().getComponent(1);
        objJtf.addFocusListener(new FocusListener()
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
    }
}
