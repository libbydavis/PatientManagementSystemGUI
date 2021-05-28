/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdc_part2;

import java.util.StringTokenizer;

/**
 *
 * @author Raj
 */
public class AddPatientController 
{ 
    public void checkNullString(AddPatientsModel mnp)
    {
        int enteredNameLength = mnp.getEnterValues().getText().length();

        if (enteredNameLength == 0)

            mnp.getErrorMsg().setVisible(true);
        else
            mnp.getErrorMsg().setVisible(false);
    }
    
    public void checkAge(AddPatientsModel mnp)
    {
        try
        {
            Integer.parseInt(mnp.getEnterValues().getText());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public String[] setName(AddPatientsModel mnp)
    {
       String fullName = mnp.getEnterValues().getText();
       String[] fullNameDivided = fullName.split(" ");
       return fullNameDivided;
    }
    
    
}
