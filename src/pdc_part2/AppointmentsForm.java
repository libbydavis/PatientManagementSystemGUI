/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdc_part2;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author libst
 */
public class AppointmentsForm extends JPanel{
    private AppointmentsController controller;
    private JButton addReason;
    private Appointment currentAppointment;
    private JList reasonsList;
    private JList measurementsList;
    private JButton addMeasurement;
    private JList notesList;
    private JButton addNote;
    
    public AppointmentsForm(int width, int height, AppointmentsController controller) {
        this.controller = controller;
        controller.addPanel2(this);
        
        //create appointment
        currentAppointment = new Appointment();

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
        reasonsList = new JList();
        String[] reasonsStrings = currentAppointment.getReasons();
        reasonsList.setListData(reasonsStrings);
        add(reasonsList, c);
        //button and popup
        c.gridy = 2;
        addReason = new JButton("Add Reason");
        addReason.addActionListener(controller);
        add(addReason, c);

        //measurements
        JLabel measurements = new JLabel("Measurements", SwingConstants.CENTER);
        c.gridx = 2;
        c.gridy = 0;
        measurements.setFont(labelFont);
        add(measurements, c);
        //measurements list
        c.gridy = 1;
        measurementsList = new JList();
        Measurements[] measurementsArray = currentAppointment.getMeasurements();
        measurementsList.setListData(measurementsArray);
        add(measurementsList, c);
        //button and popup
        c.gridy = 2;
        addMeasurement = new JButton("Add Measurement");
        addMeasurement.addActionListener(controller);
        add(addMeasurement, c);

        //notes
        JLabel notes = new JLabel("Notes", SwingConstants.CENTER);
        c.gridy = 0;
        c.gridx = 4;
        notes.setFont(labelFont);
        add(notes, c);
        //notes list
        c.gridy = 1;
        notesList = new JList();
        String[] notesArray = currentAppointment.getNotes();
        notesList.setListData(notesArray);
        add(notesList, c);
        //button and popup
        c.gridy = 2;
        addNote = new JButton("Add Note");
        addNote.addActionListener(controller);
        add(addNote, c);


    }
    
    public JButton getReasonButton() {
        return addReason;
    }
    
    public JButton getMeasureButton() {
        return addMeasurement;
    }
    
    public JButton getNoteButton() {
        return addNote;
    }
    
    public ReasonPopUp getReasonPopUp() {
        return new ReasonPopUp(currentAppointment, reasonsList);
    }
    
    public MeasurementsPopUp getMeasurementsPopup() {
        return new MeasurementsPopUp(currentAppointment, measurementsList);
    }
    
    public NotesPopUp getNotesPopup() {
        return new NotesPopUp(currentAppointment, notesList);
    }

    public class ReasonPopUp extends JFrame {
        public ReasonPopUp(Appointment current, JList reasonsList) {
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

    private class MeasurementsPopUp extends JFrame {
        public MeasurementsPopUp(Appointment current, JList measurementsList) {
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
            nameField.addFocusListener(new FocusListener(){
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
            nameField.addFocusListener(new FocusListener(){
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
                    boolean set = current.setMeasurement(nameField.getText(), valueField.getText(), unitField.getText(), unitField, nameField, valueField);
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

    private class NotesPopUp extends JFrame {
        public NotesPopUp(Appointment current, JList notesList) {
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


}
