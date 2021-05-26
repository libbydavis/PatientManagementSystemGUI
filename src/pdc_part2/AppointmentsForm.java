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
    private JButton deleteReason;
    private JButton deleteMeasurement;
    private JButton deleteNote;
    
    public AppointmentsForm(int width, int height, AppointmentsController controller) {
        this.controller = controller;
        controller.addPanel2(this);
        
        //create appointment
        currentAppointment = controller.getAppointment();

        //set layout
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        GridBagConstraints c = new GridBagConstraints();
        this.setPreferredSize(new Dimension(width, height/2 + 20));
        this.setMinimumSize(new Dimension(width, height/2 +20));

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
        //buttons and popup
        //add button
        c.gridy = 2;
        addReason = new JButton("Add Reason");
        addReason.addActionListener(controller);
        add(addReason, c);
        //remove button
        c.gridy = 3;
        deleteReason = new JButton("Remove Reason");
        deleteReason.setToolTipText("Select reason from list to delete");
        deleteReason.addActionListener(controller);
        c.insets = new Insets(10,0,0,0);
        add(deleteReason, c);
       

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
        //remove button
        c.gridy = 3;
        deleteMeasurement = new JButton("Remove Measurement");
        deleteMeasurement.setToolTipText("Select measurement from list to delete");
        deleteMeasurement.addActionListener(controller);
        c.insets = new Insets(10,0,0,0);
        add(deleteMeasurement, c);

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
        //remove button
        c.gridy = 3;
        deleteNote = new JButton("Remove Note");
        deleteNote.addActionListener(controller);
        deleteNote.setToolTipText("Select note from list to delete");
        c.insets = new Insets(10,0,0,0);
        add(deleteNote, c);

        controller.setLists(reasonsList, measurementsList, notesList);
    }
    
    //getters
    
    public JButton getReasonButton() {
        return addReason;
    }
    
    public JButton getMeasureButton() {
        return addMeasurement;
    }
    
    public JButton getNoteButton() {
        return addNote;
    }
    
    public JButton getDeleteReasonButton() {
        return deleteReason;
    }
    
    public JButton getDeleteMeasurementButton() {
        return deleteMeasurement;
    }
    
    public JButton getDeleteNoteButton() {
        return deleteNote;
    }
    
    public ReasonPopUp getReasonPopUp() {
        return ReasonPopUp.getReasonPopUpInstance(currentAppointment, reasonsList);
    }
    
    public MeasurementsPopUp getMeasurementsPopup(Patient patient) {
        return MeasurementsPopUp.getMeasurementsPopUpInstance(currentAppointment, measurementsList, patient);
    }
    
    public NotesPopUp getNotesPopup() {
        return NotesPopUp.getNotesPopUpInstance(currentAppointment, notesList);
    }


}
