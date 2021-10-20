/*
MAIN DRIVER PROGRAM
*/

import java.util.Scanner;

public class DriverProgram {
    public static void main(String[] args) throws Exception{
        AccountDatabase adb = new AccountDatabase();
        Scanner scan = new Scanner(System.in);

        //TESTING PURPOSE ONLY
        String name, phoneNo;
        int accountNumber, PIN;
        double balance = 0;

        System.out.println("--- --- --- TESTING MODULE --- --- ---");
        System.out.println("Please register yourself.");
        System.out.print("Name           : ");
        name = scan.next();
        System.out.print("Account Number : ");
        accountNumber = scan.nextInt();
        if (String.valueOf(accountNumber).length() != 6) throw new Exception("Invalid Account Number(size != 6).");
        if (adb.getAccount(accountNumber) != null) throw new Exception("Account Already Exists.");
        System.out.print("PIN Code       : ");
        PIN = scan.nextInt();
        System.out.print("Phone Number   : ");
        phoneNo = scan.next();
        if (phoneNo.length() != 10) throw new Exception("Invalid Phone Number(size < 10).");
        System.out.print("Balance        : ");
        balance = scan.nextDouble();
        System.out.println("Enter 0 in Account Number to terminate the program\n\n");
        adb.addAccount(name, accountNumber, PIN, phoneNo, balance); //account added
        ATM myAtm = new ATM("PMCS40","IIT Tirupati", adb); //atm created
        myAtm.beginSession();
    }
}
