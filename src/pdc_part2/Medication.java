package pdc_part2;

import pdc_part2.Dosage;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;

/**
 * 
 * @author Raj
 */
public class Medication 
{
    private String name;
    private Dosage dosage;
    private HashSet<String> sideEffects;
    private HashSet<String> conditions;

    public Medication(String name, Dosage dosage, HashSet<String> sideEffects, HashSet<String> conditions) 
    {
        this.name = name;
        this.dosage = dosage;
        this.sideEffects = sideEffects;
        this.conditions = conditions;
    }

    public String getName() 
    {
        return name;
    }

    public Dosage getDosage() 
    {
        return dosage;
    }

    public void setDosage(Dosage dosage) 
    {
        this.dosage = dosage;
    }

    public HashSet<String> getSideEffects() 
    {
        return sideEffects;
    }

    public HashSet<String> getConditions() 
    {
        return conditions;
    }
    /**
     * This searches the MedicationList.txt file by the name of the medication as opposed to
     * searching by it's key. (MedicationList.txt is stored as a HashMap)
     * @param medName The name of the medication the doctor wants to remove.
     * @return The medication object that the doctor wants to remove.
     * @throws FileNotFoundException 
     * @author Raj
     */
    public static Medication searchMedsByName(String medName) throws FileNotFoundException 
    {
        // Converts the method parameter to a char array and makes it all lowercase.
        char[] uInputCharArray = medName.toLowerCase().toCharArray();
        // Changes the first letter of the char array to a capital letter and stores it back into the
        // method parameter. This is so it matches how the medication name is formatted in MedicationList.txt
        uInputCharArray[0] = Character.toUpperCase(uInputCharArray[0]);
        Medication removedMeds = null;
        medName = String.valueOf(uInputCharArray);
        // Searches through the MedicationList.txt by comparing the name of the method parameter to the names
        // of medication within MedicationList.txt to remove the selected medication.
        for (int i = 1; i < Medication.fileToHashMap().size() + 1; i++) 
        {
            String medNameInDB = Medication.fileToHashMap().get(String.valueOf(i)).getName();
            if (medNameInDB.equals(medName)) 
            {
                removedMeds = Medication.fileToHashMap().get(String.valueOf(i));    
            }
        }
        return removedMeds;
    }
 
    /**
     * This method searches MedicationList.txt by the key value the medication is stored at
     * @param medIndex The key of the medication the doctor wants
     * @return The medication object the doctor wants
     * @throws FileNotFoundException
     * @author Raj
     */
    public static Medication getMeds(String medIndex) throws FileNotFoundException 
    {
       return Medication.fileToHashMap().get(medIndex);
    }
    
    /**
     * This method ensures that the key of the medication the doctor wants does 
     * exist within MedicationList.txt
     * @param medChoice The key the doctor wants that needs validating
     * @throws FileNotFoundException 
     * @author Raj
     */
    public static String validateMeds(String medChoice) throws FileNotFoundException
    {
        Scanner scanner = new Scanner(System.in);
        while(!Medication.fileToHashMap().containsKey(medChoice))
        {
            System.out.println("This does not exist in the list of medication, please try again");
            medChoice = scanner.nextLine();
        }
        return medChoice;
    }
    
    /**
     * This method converts the MedicationList.txt into a HashMap, as it was initially stored
     * into the txt file as a HashMap. This allows the data within MedicationList.txt to be manipulated.
     * @return
     * @throws FileNotFoundException 
     * @author Raj
     */
    public static HashMap<String, Medication> fileToHashMap() throws FileNotFoundException 
    {
        return null;
    }

    /**
     * This method scans for the key of the medication that the doctor wants to see more details of.
     * @throws FileNotFoundException 
     * @author Raj
     */
    public static void printMedInfo() throws FileNotFoundException 
    {
        
    }
    
    /**
     * This prints the name and key of all the medication that exists within MedicationList.txt
     * @throws FileNotFoundException 
     * @author Raj
     */
    public static void printMedList() throws FileNotFoundException 
    {
        
    }
    
    /**
     * compares the name of each medication to ensure they're not the same medication.
     * @param o The medication that will be compared
     * @return a Boolean letting you know if the medications are the same or not.
     * @author Raj
     */
    public boolean equals(Object o) 
    {
        if(o != null && o instanceof Medication)
        {
            String meds = ((Medication)o).getName();
            
            if(meds != null && meds.equals(this.name))
            {
                return true;
            }     
        }
        return false;
    }
    
    /**
     * Randomly generated hashcode for Medication.
     * @return Randomly generated number
     * @author Raj
     */ 
    public int hashCode()
    {
        int hashCode = 1;
        hashCode = 180 * hashCode + 5;
        return hashCode;
    }
    
    public String toString() 
    {
        return "Medication Name: " + name + "\n" + dosage + "\nSide Effects: " + sideEffects + "\nConditions: " + conditions;
    }
}
