/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdc_part2;

import java.awt.Color;
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
    private JLabel enterMsg, errorMsg, corrMsg;
    private JTextField enterValues; 

    public AddPatientsModel(String enterMsg, String enterValuesTxt, String errorMsg, String corrMsg) 
    {
        this.panel = new JPanel();
        this.enterMsg = new JLabel(enterMsg);
        this.enterValues = new JTextField(enterValuesTxt);
        this.errorMsg = new JLabel(errorMsg);
        this.corrMsg = new JLabel(corrMsg);
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

    public JLabel getCorrMsg() 
    {
        return corrMsg;
    }
    
    public JPanel combineComponents()
    {
        JPanel objPanel = getPanel();
        objPanel.add(getEnterMsg());
        objPanel.add(getEnterValues());
        objPanel.add(getErrorMsg());
        objPanel.add(getCorrMsg());
        getCorrMsg().setVisible(false);
        getCorrMsg().setForeground(new Color(0, 153, 0));
        getErrorMsg().setVisible(false);
        getErrorMsg().setForeground(Color.RED);
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
