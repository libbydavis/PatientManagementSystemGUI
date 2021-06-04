package pdc_part2;
/**
 * 
 * @author Raj
 */
public class Dosage 
{  
    private double amount;
    private String howOften;

    public Dosage() {
    }
    
    public Dosage(double amount) {
        this.amount = amount;
    }

    public Dosage(String howOften) {
        this.howOften = howOften;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setHowOften(String howOften) {
        this.howOften = howOften;
    }
    
    public String toString() 
    {
        return "Dosage amount: " + amount + ", Dosage Frequency: " + howOften;
    }
}
