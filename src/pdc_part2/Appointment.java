/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdc_part2;

public class Appointment {
    private String[] reasons;
    private Measurements[] measurements;
    private String[] notes;

    public Appointment() {
        reasons = new String[10];
        measurements = new Measurements[10];
        notes = new String[10];
    }

    public void expandCapacity(String[] list) {
        String[] newList = new String[list.length * 2];
        for (int i = 0; i < list.length; i++) {
            newList[i] = list[i];
        }
        reasons = newList;
    }

    public void setReasons(String r) {
        if (reasons[reasons.length-1] != null) {
            expandCapacity(reasons);
        }
        for (int i = 0; i < reasons.length; i++) {
            if (reasons[i] == null) {
                reasons[i] = r;
                return;
            }
        }
    }

    public void setNotes(String n) {
        if (notes[notes.length-1] != null) {
            expandCapacity(notes);
        }
        for (int i = 0; i < notes.length; i++) {
            if (notes[i] == null) {
                notes[i] = n;
                return;
            }
        }
    }

    public String[] getReasons() {
        return reasons;
    }

    public Measurements[] getMeasurements() {
        return measurements;
    }

    public String[] getNotes() {
        return notes;
    }
}

