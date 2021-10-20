/*
,-------------------------------------------------------------------------------------------------.
|Account                                                                                          |
|-------------------------------------------------------------------------------------------------|
|-accountNumber: int                                                                              |
|-pinCode: int                                                                                    |
|-name: String                                                                                    |
|-phoneNumber: String                                                                             |
|-bankBalance: double                                                                             |
|-isBlocked: boolean                                                                              |
|+Account(name: String, accountNumber: int, pinCode: int, phoneNumber: String, balance: double)   |
|+getAccountNumber(): int                                                                         |
|+getName(): String                                                                               |
|+blockAccount(): void                                                                            |
|+blockStatus(): boolean                                                                          |
|+blockStatus(PIN: int, display: DisplayScreen, numberPad: NumberPad): boolean                    |
|+isAccountNumber(accountNumber: int): boolean                                                    |
|+getBalance(): double                                                                            |
|+depositAmount(amount: double): void                                                             |
|+canWithdrawAmount(amount: double): boolean                                                      |
|+withdrawAmount(amount: double): void                                                            |
|+tfauthenticatePINcode(PIN: int, display: DisplayScreen, numberPad: NumberPad): boolean          |
|+authenticatePINcode(PIN: int): boolean                                                          |
|+changePIN(oldPIN: int, newPIN: int, displayScreen: DisplayScreen, numberPad: NumberPad): boolean|
`-------------------------------------------------------------------------------------------------'

*/

public class Account {
    private final int accountNumber;
    private int pinCode; //PIN Code

    private final String name; //Customer's Name
    private String phoneNumber; //Phone Number
    
    private double bankBalance; // Balance in Bank 
    private boolean isBlocked; // Disables transition operations

    public Account(String name, int accountNumber, int pinCode, String phoneNumber, double balance){
        this.accountNumber = accountNumber;
        this.pinCode = pinCode;

        this.name = name;
        this.phoneNumber = phoneNumber; //To send OTPs

        this.bankBalance = (balance >= 0)?balance:0;
        this.isBlocked = false;
    }

    //-----------------------------

    public int getAccountNumber(){
        return this.accountNumber;
    }

    public String getName(){
        return this.name;
    }

    public void blockAccount(){
        this.isBlocked = true;
    }

    //Returns blocked status
    public boolean blockStatus(){
        return isBlocked;
    }

    //Unblocks if Blocked
    public boolean blockStatus(int PIN,DisplayScreen display, NumberPad numberPad){
        if (this.pinCode == PIN){
            if (SecurityModule.tfauthenticate(phoneNumber, display, numberPad)){
                isBlocked = false;
                return true;
            }
            return false;
        }else{
            return false;
        }
    }

    public boolean isAccountNumber(int accountNumber){
        return (accountNumber == this.accountNumber);
    }

    //-----------------------------

    public double getBalance(){
        return (!isBlocked)?bankBalance:0;
    }

    public void depositAmount(double amount){
        bankBalance += (!isBlocked)?amount:0;
    }

    public boolean canWithdrawAmount(double amount){
        return (amount <= bankBalance);
    }

    public void withdrawAmount(double amount){
        //NOTE: Check is performed before sending the amount to this method whether it's more than balance
        bankBalance -= (!isBlocked)?amount:0;
    }

    //------------------------------
    // Performs Security Operations
    public boolean tfauthenticatePINcode(int PIN, DisplayScreen display, NumberPad numberPad){
        if (authenticatePINcode(PIN) && !isBlocked)
            return (SecurityModule.tfauthenticate(phoneNumber, display, numberPad));
        else 
            return false;
    }

    public boolean authenticatePINcode(int PIN){
        return !isBlocked && (this.pinCode == PIN);
    }

    public boolean changePIN(int oldPIN, int newPIN, DisplayScreen displayScreen, NumberPad numberPad){
        if (tfauthenticatePINcode(oldPIN, displayScreen, numberPad)){

            this.pinCode = newPIN;
            return true;
        }
        return false;
    }
}
