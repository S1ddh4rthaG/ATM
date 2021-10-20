/*
,------------------------------------------------.
|DisplayScreen                                   |
|------------------------------------------------|
|+displayMessage(message: String): void          |
|+displayMessageSL(message: String): void        |
|+displayOTP(otp: int, phoneNumber: String): void|
|+displayMessageTB(message: String): void        |
`------------------------------------------------'

*/

//Mimics Display Screen of ATM
public class DisplayScreen {
    //displays message in new line
    public void displayMessage(String message){
        System.out.println(message);
    }

    //displays message in same line
    public void displayMessageSL(String message){
        System.out.print(message);
    }

    //prints OTP
    public void displayOTP(int otp, String phoneNumber){
        System.out.println("--- EMULATOR FOR MOBILE ---");
        System.out.println("OTP: " + otp + " sent to " + phoneNumber);
        System.out.println("---------------------------");
    }

    //prints tab + message
    public void displayMessageTB(String message){
        System.out.println("\t" + message);
    }
}
