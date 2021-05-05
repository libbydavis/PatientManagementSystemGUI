/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdc_part2;

import java.awt.GridLayout;
import javax.swing.JPanel;

/**
 *
 * @author libst
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author libst
 */
public class AppointmentsForm extends JPanel{
    public AppointmentsForm(int width, int height) {
        //create appointment
        Appointment currentAppointment = new Appointment();

        //set layout
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        GridBagConstraints c = new GridBagConstraints();
        this.setPreferredSize(new Dimension(width, height - 200));

        //display
        Font labelFont = new Font("Arial", Font.BOLD, 24);

        //reasons
        JLabel reasons = new JLabel("Reasons", SwingConstants.CENTER);
        reasons.setBorder(new EmptyBorder(20, 0, 20, 0));
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.5;
        c.gridwidth = 2;
        reasons.setFont(labelFont);
        add(reasons, c);
        c.gridy = 1;
        //reasons list
        JList reasonsList = new JList();
        String[] reasonsStrings = currentAppointment.getReasons();
        reasonsList.setListData(reasonsStrings);
        add(reasonsList, c);
        //button and popup
        c.gridy = 2;
        JButton addReason = new JButton("Add Reason");
        addReason.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object source = e.getSource();
                ReasonPopUp popUp = new ReasonPopUp(currentAppointment, reasonsList);
                popUp.setVisible(true);
                revalidate();
            }
        });
        add(addReason, c);

        //measurements
        JLabel measurements = new JLabel("Measurements", SwingConstants.CENTER);
        c.gridx = 2;
        c.gridy = 0;
        measurements.setFont(labelFont);
        add(measurements, c);
        //measurements list
        c.gridy = 1;
        JList measurementsList = new JList();
        Measurements[] measurementsArray = currentAppointment.getMeasurements();
        measurementsList.setListData(measurementsArray);
        add(measurementsList, c);
        //button and popup
        c.gridy = 2;
        JButton addMeasurement = new JButton("Add Measurement");
        addMeasurement.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object source = e.getSource();
            }
        });
        add(addMeasurement, c);

        //notes
        JLabel notes = new JLabel("Notes", SwingConstants.CENTER);
        c.gridy = 0;
        c.gridx = 4;
        notes.setFont(labelFont);
        add(notes, c);
        //notes list
        c.gridy = 1;
        JList notesList = new JList();
        String[] notesArray = currentAppointment.getNotes();
        notesList.setListData(notesArray);
        add(notesList, c);
        //button and popup
        c.gridy = 2;
        JButton addNote = new JButton("Add Note");
        addNote.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object source = e.getSource();
                NotesPopUp popUp = new NotesPopUp(currentAppointment, notesList);
                popUp.setVisible(true);
                revalidate();
            }
        });
        add(addNote, c);


    }

    private class ReasonPopUp extends JFrame {
        public ReasonPopUp(Appointment current, JList reasonsList) {
            super("Add Reasons");
            setSize(500, 300);
            setLocation(200, 200);
            setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();

            JLabel reasonLabel = new JLabel("Enter a new reason");
            JTextField reasonField = new JTextField();
            reasonField.setSize(300, 100);
            JButton addReason = new JButton("Add");
            addReason.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    current.setReasons(reasonField.getText());
                    reasonField.setText("");
                    String[] reasonsStrings = current.getReasons();
                    reasonsList.setListData(reasonsStrings);
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

    private class NotesPopUp extends JFrame {
        public NotesPopUp(Appointment current, JList notesList) {
            super("Add Notes");
            setSize(500, 300);
            setLocation(200, 200);
            setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();

            JLabel notesLabel = new JLabel("Enter a new note");
            JTextField notesField = new JTextField();
            notesField.setSize(300, 100);
            JButton addNote = new JButton("Add");
            addNote.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    current.setNotes(notesField.getText());
                    notesField.setText("");
                    String[] notesArray = current.getNotes();
                    notesList.setListData(notesArray);
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


}
