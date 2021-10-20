/*
,---------------------------------------------.
|RecepitPrinter                               |
|---------------------------------------------|
|-displayScreen: DisplayScreen                |
|+RecepitPrinter(displayScreen: DisplayScreen)|
|+printRecepit(recepit: Recepit): void        |
`---------------------------------------------'

*/

public class RecepitPrinter {
    /*
        We don't have an actual printer attached.
        We use displayScreen for showing recepit
    */

    private DisplayScreen displayScreen;

    public RecepitPrinter(DisplayScreen displayScreen){
        this.displayScreen = displayScreen;
    }
    
    //Prints receipt
    public void printRecepit(Recepit recepit){
        displayScreen.displayMessageTB("\n------------------------------ RECEIPT ------------------------------\n");
        displayScreen.displayMessageTB("------------ ATM DETAILS ------------");
        displayScreen.displayMessageTB("ATM Unique ID  :    " + recepit.uniqueID);
        displayScreen.displayMessageTB("ATM Location   :    " + recepit.location);
        displayScreen.displayMessageSL("\n");
        displayScreen.displayMessageTB("------------ CUSOTMER DETAILS ------------");
        displayScreen.displayMessageTB("Customer Name      : " + recepit.report.name);
        displayScreen.displayMessageTB("Account Number     : " + recepit.report.accountNumber);
        displayScreen.displayMessageSL("\n");
        displayScreen.displayMessageTB("----------- TRANSACTION DETAILS ---------");
        displayScreen.displayMessageTB("Transaction Kind     :   " + recepit.report.transactionType);
        displayScreen.displayMessageTB("Transaction DateTime :   " + recepit.report.dateTime);
        displayScreen.displayMessage("\n----------------------------------------------------------------------\n");

        switch(recepit.report.transactionType){
            case BALANCE_ENQUIRY:break;

            case WITHDRAWAL_REQUEST:
                displayScreen.displayMessageTB("Amount Withdrawal    :   " + recepit.report.amount);
            break;

            case DEPOSIT_REQUEST:
                displayScreen.displayMessageTB("Amount Desposited    :   " + recepit.report.amount);
            break;
        }

        displayScreen.displayMessageTB("Available Balance    :   Rs." + recepit.report.balance);
        displayScreen.displayMessage("----------------------------------------------------------------------");
    }
}
