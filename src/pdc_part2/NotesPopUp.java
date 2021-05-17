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
public class NotesPopUp extends JFrame {
    private static NotesPopUp NotesPopUpInstance;
        public static synchronized NotesPopUp getNotesPopUpInstance(Appointment current, JList notesList) {
            if (NotesPopUpInstance == null) {
                NotesPopUpInstance = new NotesPopUp(current, notesList);
            }
            return NotesPopUpInstance;
        }
        public static synchronized void noteClosePopUp() { 
        if (NotesPopUpInstance != null) {
            NotesPopUpInstance.setVisible(false);
            NotesPopUpInstance = null;
        }
    }
    private NotesPopUp(Appointment current, JList notesList) {
        super("Add Notes");
        setSize(500, 300);
        setLocation(200, 200);
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel notesLabel = new JLabel("Enter a new note");
        JTextField notesField = new JTextField();
        notesField.addFocusListener(new FocusListener(){
            @Override
            public void focusGained(FocusEvent e){
                notesField.setForeground(Color.BLACK);
                notesField.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });
        notesField.setSize(300, 100);
        JButton addNote = new JButton("Add");
        addNote.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean set = current.setNotes(notesField.getText(), notesField);
                if (set == true) {
                    notesField.setText("");
                    String[] notesArray = current.getNotes();
                    notesList.setListData(notesArray);
                }
            }
        });

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.2;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.insets = new Insets(0, 15, 0, 0);
        add(notesLabel, c);
        c.gridx = 1;
        c.weightx = 1;
        c.insets = new Insets(0, 0, 0, 15);
        add(notesField, c);
        c.gridx = 1;
        c.gridy = 2;
        c.weightx = 1;
        c.insets = new Insets(10, 0, 0, 15);
        c.fill = GridBagConstraints.PAGE_END;
        c.anchor = GridBagConstraints.EAST;
        add(addNote, c);
    }

}

