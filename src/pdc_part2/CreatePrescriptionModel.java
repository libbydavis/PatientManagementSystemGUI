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
public class CreatePrescriptionModel {

    private JPanel prescPanelPart;
    private JLabel enterLabel, errorLabel, corrLabel;
    private JTextField enterField;
    
    public CreatePrescriptionModel(String enterMsg, String enterValuesTxt, String errorMsg, String corrMsg) {
        this.prescPanelPart = new JPanel();
        this.enterLabel = new JLabel(enterMsg);
        this.enterField = new JTextField(enterValuesTxt);
        this.errorLabel = new JLabel(errorMsg);
        this.corrLabel = new JLabel(corrMsg);
    }
    
    public CreatePrescriptionModel(String enterMsg, String errorMsg, String corrMsg) {
        this.prescPanelPart = new JPanel();
        this.enterLabel = new JLabel(enterMsg);
        this.errorLabel = new JLabel(errorMsg);
        this.corrLabel = new JLabel(corrMsg);

    }

    public JPanel getPrescPanelPart() {
        return prescPanelPart;
    }

    public JLabel getEnterLabel() {
        return enterLabel;
    }

    public JLabel getErrorLabel() {
        return errorLabel;
    }

    public JLabel getCorrLabel() {
        return corrLabel;
    }

    public JTextField getEnterField() {
        return enterField;
    }
    
    public JPanel combineComponents() {
        JPanel objPanel = getPrescPanelPart();
        objPanel.add(getEnterLabel());
        objPanel.add(getEnterField());
        objPanel.add(getErrorLabel());
        objPanel.add(getCorrLabel());
        getCorrLabel().setVisible(false);
        getCorrLabel().setForeground(new Color(0, 153, 0));
        getErrorLabel().setVisible(false);
        getErrorLabel().setForeground(Color.RED);
        return objPanel;
    }
    
    public void clearTextField() {
        JTextField objJtf = (JTextField) combineComponents().getComponent(1);
        objJtf.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                JTextField source = (JTextField) e.getComponent();
                source.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });
    }
    
    public boolean checkNullString(CreatePrescriptionModel mnp) {
        int enteredNameLength = mnp.getEnterField().getText().length();
        return ((enteredNameLength == 0) ? true : false);
    }
    
}
