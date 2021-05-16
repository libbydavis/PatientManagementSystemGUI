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
public class ReasonPopUp extends JFrame {
    private static ReasonPopUp ReasonPopUpInstance;
    public static synchronized ReasonPopUp getReasonPopUpInstance(Appointment current, JList reasonsList) {
        if (ReasonPopUpInstance == null) {
            ReasonPopUpInstance = new ReasonPopUp(current, reasonsList);
        }
        return ReasonPopUpInstance;
    }
    private ReasonPopUp(Appointment current, JList reasonsList) {
        super("Add Reasons");
        setSize(500, 300);
        setLocation(200, 200);
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel reasonLabel = new JLabel("Enter a new reason");
        JTextField reasonField = new JTextField();
        reasonField.addFocusListener(new FocusListener(){
            @Override
            public void focusGained(FocusEvent e){
                reasonField.setForeground(Color.BLACK);
                reasonField.setText("");
            }
            @Override
            public void focusLost(FocusEvent e) {

            }
        });
        reasonField.setSize(300, 100);
        JButton addReason = new JButton("Add");
        addReason.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean set = current.setReasons(reasonField.getText(), reasonField);
                if (set == true) {
                    reasonField.setText("");
                    String[] reasonsStrings = current.getReasons();
                    reasonsList.setListData(reasonsStrings);
                }
            }
        });

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.2;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.insets = new Insets(0, 15, 0, 0);
        add(reasonLabel, c);
        c.gridx = 1;
        c.weightx = 1;
        c.insets = new Insets(0, 0, 0, 15);
        add(reasonField, c);
        c.gridx = 1;
        c.gridy = 2;
        c.weightx = 1;
        c.insets = new Insets(10, 0, 0, 15);
        c.fill = GridBagConstraints.PAGE_END;
        c.anchor = GridBagConstraints.EAST;
        add(addReason, c);
    }

}
