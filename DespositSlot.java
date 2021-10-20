/*
                     ,----.                     
                     |Slot|                     
                     |----|                     
                     `----'                     
                        |                       
                        |                       
,----------------------------------------------.
|DespositSlot                                  |
|----------------------------------------------|
|-validBills: int[]                            |
|+DespositSlot(displayScreen: DisplayScreen)   |
|+isValidDenomination(bill: String): boolean   |
|+process(acc: Account, amount: double): Report|
`----------------------------------------------'

*/

//Mimics Deposit Slot of real ATM

public class DespositSlot extends Slot{
    private int[] validBills = {1,2,5,10,20,50,100,200,500,2000};

    public DespositSlot(DisplayScreen displayScreen) {
        super(displayScreen);
    }

    //Checks whether bill is valid denomination or not
    public boolean isValidDenomination(String bill){
        int billValue = Integer.parseInt(bill);
        for (int i: validBills){
            if (i == billValue) return true;
        }

        return false;
    }

    //Processes data
    public Report process(Account acc, double amount) {
        acc.depositAmount(amount);
        displayScreen.displayMessage("Amount Rs." + amount + " has been deposited.");
        return new Report(acc.getName(), acc.getAccountNumber(), TransactionType.DEPOSIT_REQUEST, acc.getBalance(),amount);
    }
    
}
