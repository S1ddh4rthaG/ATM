/*
,----------------------.
|NumberPad             |
|----------------------|
|-scan: Scanner        |
|+NumberPad()          |
|+getCode(): int       |
|+getAmount(): double  |
|+getRawInput(): String|
`----------------------'

*/

import java.util.Scanner;

//Mimics Key Pad of ATM
public class NumberPad {
    /*
        NOTE: Avoid using multiple Scanner instances. Closing one Scanner object closes every Scanner instance
    */
    
    private Scanner scan;

    public NumberPad(){
        scan = new Scanner(System.in);
    }

    //Gets Numeric Data
    public int getCode(){
        return Integer.parseInt(scan.next());
    }
    
    //Gets Amount
    public double getAmount(){
        return Math.abs(Double.parseDouble(scan.next()));
    }

    //gets raw data for bills
    public String getRawInput(){
        return scan.next();
    }
}
