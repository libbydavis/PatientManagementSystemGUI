package pdc_part2;
/**
 * 
 * @author Raj
 */
public class Dosage 
{  
    double amount;
    String howOften;

    public Dosage(double amount, String howOften) 
    {
        this.amount = amount;
        this.howOften = howOften;
    }
        
    public Dosage() 
    {
        this.amount = 0.0;
        this.howOften = "UNDEFINED";
    }
    
    public String toString() 
    {
        return "Dosage amount: " + amount + ", Frequency: " + howOften;
    }
}
