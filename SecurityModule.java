/*
,------------------------------------------------------------------------------------------------------.
|SecurityModule                                                                                        |
|------------------------------------------------------------------------------------------------------|
|-{static} otpSize: int                                                                                |
|-{static} genOTP(): int                                                                               |
|+{static} tfauthenticate(phoneNumber: String, display: DisplayScreen, numbericPad: NumberPad): boolean|
`------------------------------------------------------------------------------------------------------'

*/

import java.util.Random;

//For Security related operations like OTPs, two factor autentication
public class SecurityModule {
    private final static int otpSize = 5;
    
    //generates OTP
    private static int genOTP(){
        Random r = new Random(System.currentTimeMillis());
        int pow10 = (int)Math.pow(10, otpSize);
        return (pow10 + Math.abs(r.nextInt(pow10)))%pow10;
    }

    //for two factor authentication
    public static boolean tfauthenticate(String phoneNumber, DisplayScreen display, NumberPad numbericPad) {
        int OTP = genOTP();
        display.displayOTP(OTP, phoneNumber);

        int userOTP = numbericPad.getCode();
        return (OTP == userOTP);
    }
}
