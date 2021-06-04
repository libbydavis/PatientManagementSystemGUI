/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdc_part2;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Random;
import javax.swing.JComboBox;

/**
 *
 * @author Raj
 */
public class AddPatientController {

    MedicalPatient newPat = new MedicalPatient();

    public AddPatientController() throws IOException, SQLException {

    }
    
    public boolean checkNullString(AddPatientsModel mnp) {
        int enteredNameLength = mnp.getEnterValues().getText().length();
        return ((enteredNameLength == 0) ? true : false);
    }
    
    /**
        * Checks whether the name entered in the JTextField has a space between it,
        * so it can see when a first name and last name is entered.
        * Otherwise it will just assign the patient's first name in the database.
        * @param mnp an AddPatientsModel
        * @author Raj 
        **/
    public void setName(AddPatientsModel mnp) {
        String fullName = mnp.getEnterValues().getText();
        String[] fullNameDivided = fullName.split(" ");

        if (fullNameDivided.length >= 2) {
            newPat.setfName(fullNameDivided[0]);
            newPat.setlName(fullNameDivided[1]);
        } else {
            newPat.setfName(fullNameDivided[0]);
        }
    }
    
    /**
        * Ensures that the number entered as the age is indeed a number
        * @param map an AddPatientsModel used to access the textfield that is used to input the age
        * @return returns a boolean of whether the user's input is a number or not.
        **/
    public boolean validateNum(AddPatientsModel map) {
        boolean correctAge;

        try {
            Integer.parseInt(map.getEnterValues().getText());
            map.getErrorMsg().setVisible(false);
            correctAge = true;
        } catch (Exception e) {
            map.getEnterValues().setText("");
            map.getErrorMsg().setVisible(true);
            correctAge = false;
        }

        return correctAge;
    }

    public void setAge(AddPatientsModel map) {
        int enteredAge = Integer.parseInt(map.getEnterValues().getText());
        newPat.setAge(enteredAge);
    }

    public void setStreet(AddPatientsModel msp) {
        newPat.setAddress(msp.getEnterValues().getText().toString());
    }
    
    /**
        * Generates a unique NHI for the patient that's inserted into the database
        * @return a string that serves as the primary key in the patient database 
        **/
    public String genNhi() {
        Random rand = new Random();
        String[] nhi = new String[6];

        for (int charIndex = 0; charIndex < nhi.length / 2; charIndex++) {
            Character randomChar = (char) ('a' + rand.nextInt(26));
            nhi[charIndex] = randomChar.toString();
        }

        for (int numIndex = 3; numIndex < nhi.length; numIndex++) {
            Integer randomInt = rand.nextInt(10);
            nhi[numIndex] = randomInt.toString();
        }

        String generatedNHI = "";

        for (int k = 0; k < nhi.length; k++) {
            generatedNHI += nhi[k];
        }

        return generatedNHI;
    }
    
    /**
     * Gets the medication from the combobox and sets it to the current medication for the patient
     * @param chosenMeds shows all the possible medication in a combobox
     * @param currMeds shows the current medications of the patient
     */
    public void setMedication(JComboBox chosenMeds, HashSet currMeds) {
        String chosenMedicine = chosenMeds.getSelectedItem().toString();
        if ((!chosenMedicine.equalsIgnoreCase("choose an option")) && (!(currMeds.contains(chosenMedicine)))) {
            currMeds.add(chosenMedicine);
            newPat.setCurrentMedications(currMeds);
        }
    }
}
