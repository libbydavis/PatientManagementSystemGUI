/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdc_part2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Raj
 */
public class AddPatientController 
{ 
    Patient newPat = new Patient();

    public AddPatientController() throws IOException, SQLException 
    {
        
    }

    public boolean checkNullString(AddPatientsModel mnp)
    {
        int enteredNameLength = mnp.getEnterValues().getText().length();

        if (enteredNameLength == 0)

            mnp.getErrorMsg().setVisible(true);
        else
            mnp.getErrorMsg().setVisible(false);
        
        return ((enteredNameLength == 0) ? true : false);
    }
    
    public void setName(AddPatientsModel mnp)
    {
       String fullName = mnp.getEnterValues().getText();
       String[] fullNameDivided = fullName.split(" ");
       
       if(fullNameDivided.length >= 2)
       {           
            newPat.setfName(fullNameDivided[0]);
            newPat.setlName(fullNameDivided[1]);
       }
    }
    
    public boolean validateAge(AddPatientsModel map)
    {
        boolean correctAge;
        
        try
        {
            Integer.parseInt(map.getEnterValues().getText());
            map.getErrorMsg().setVisible(false);
            correctAge = true;
        }
        catch(Exception e)
        {
            map.getEnterValues().setText("");
            map.getErrorMsg().setVisible(true);
            correctAge = false;
        }
        
        return correctAge;
    }
    
    public void setAge(AddPatientsModel map)
    {
        int enteredAge = Integer.parseInt(map.getEnterValues().getText());
        newPat.setAge(enteredAge);
    }
    
    public void setStreet(AddPatientsModel msp)
    {
        newPat.setAddress(msp.getEnterMsg().getText());
    }
    
    public String genNhi()
    {
        Random rand = new Random();
        String[] nhi = new String[6];

        for (int charIndex = 0; charIndex < nhi.length / 2; charIndex++) 
        {
            Character randomChar = (char) ('a' + rand.nextInt(26));
            nhi[charIndex] = randomChar.toString();
        }

        for (int numIndex = 3; numIndex < nhi.length; numIndex++) 
        {
            Integer randomInt = rand.nextInt(10);
            nhi[numIndex] = randomInt.toString();
        }

        String generatedNHI = "";

        for (int k = 0; k < nhi.length; k++) 
        {
            generatedNHI += nhi[k];
        }
        
        //test to see if a patient can be put into the patientDB with a duplicate nhi
        generatedNHI = "jnv758";
        
        return generatedNHI;
    }
    
    //Don't know if i need this method to check whether NHI is unique.
    
//    public String newNhi() throws SQLException
//    {
//        String newNhi = genNhi();
//        DatabaseConnection dbc = new DatabaseConnection();
//        
//        String sqlQuery = "SELECT * FROM PATIENTS WHERE NHI = \'" + newNhi +"\'";
//        PreparedStatement prepstmt = dbc.getConnectionPatients().prepareStatement(sqlQuery);
//        ResultSet rs = prepstmt.executeQuery();
//        boolean resultSet = rs.next();
//        
//        while(rs.next())
//        {
//            newNhi = genNhi();
//        }
//        
//        return newNhi;
//    }
}
