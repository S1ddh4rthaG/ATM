/*
                     ,----.                     
                     |Slot|                     
                     |----|                     
                     `----'                     
                        |                       
                        |                       
,----------------------------------------------.
|WithdrawalSlot                                |
|----------------------------------------------|
|-mlt: int                                     |
|-AVAILABLE_CASH: double                       |
|+WithdrawalSlot(displayScreen: DisplayScreen) |
|+isEmpty(): boolean                           |
|+isValidDenomination(amount: double): boolean |
|+isValidAmount(amount: double): boolean       |
|-dispenseCash(amount: double): void           |
|+process(acc: Account, amount: double): Report|
`----------------------------------------------'

*/

public class WithdrawalSlot extends Slot{
    private final int mlt = 100; // ATM Can dispense any amount of Cash below AVAILABLE_CASH
    private double AVAILABLE_CASH = 10_00_000; //Initial Cash

    public WithdrawalSlot(DisplayScreen displayScreen) {
        super(displayScreen);
    }

    public boolean isEmpty(){
        return (AVAILABLE_CASH == 0);
    }
    public boolean isValidDenomination(double amount){
        return (amount%mlt == 0);
    }

    public boolean isValidAmount(double amount){
        return (AVAILABLE_CASH >= amount); 
    }

    //Dispenses Cash
    private void dispenseCash(double amount){
        AVAILABLE_CASH -= amount;// Amount dispensed.
        displayScreen.displayMessage("Amount Rs." + amount + " has been dispensed.");
        displayScreen.displayMessage("Please collect the cash.");
    }

    public Report process(Account acc, double amount) {
        dispenseCash(amount);
        acc.withdrawAmount(amount);
        return new Report(acc.getName(), acc.getAccountNumber(), TransactionType.WITHDRAWAL_REQUEST, acc.getBalance(),amount);
    }
    
}
