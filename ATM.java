/*
,--------------------------------------------------------------.
|ATM                                                           |
|--------------------------------------------------------------|
|-location: String                                             |
|-uniqueID: String                                             |
|-database: AccountDatabase                                    |
|-numberPad: NumberPad                                         |
|-displayScreen: DisplayScreen                                 |
|-despositSlot: DespositSlot                                   |
|-withdrawalSlot: WithdrawalSlot                               |
|-recepitPrinter: RecepitPrinter                               |
|-currAccount: Account                                         |
|-isOperational: boolean                                       |
|-maxLimit: int                                                |
|+ATM(uniqueID: String, location: String, adb: AccountDatabase)|
|-reset(): void                                                |
|+beginSession(): void                                         |
|-displayMenu(): int                                           |
|-displayWithdrawalMenu(): int                                 |
|-completeTransaction(): void                                  |
`--------------------------------------------------------------'
*/

public class ATM {
    //Functionalities For ATM
    private final String location;
    private final String uniqueID;
    private final AccountDatabase database;

    //Individual components of ATM
    private NumberPad numberPad;
    private DisplayScreen displayScreen;
    private DespositSlot despositSlot;
    private WithdrawalSlot withdrawalSlot;
    private RecepitPrinter recepitPrinter;

    //Session
    private Account currAccount;
    private boolean isOperational;
    private final int maxLimit = 3;

    public ATM(String uniqueID, String location, AccountDatabase adb) {
        this.location = location;
        this.uniqueID = uniqueID;

        numberPad = new NumberPad();
        displayScreen = new DisplayScreen();
        despositSlot = new DespositSlot(displayScreen);
        withdrawalSlot = new WithdrawalSlot(displayScreen);
        recepitPrinter = new RecepitPrinter(displayScreen);
        database = adb;

        currAccount = null;
        isOperational = true;
    }

    //Overrides ATM State
    private void reset() {
        currAccount = null;
    }

    //Begins the session
    public void beginSession() throws Exception {
        //Checks Whether the Cash tray is Empty or not
        if (withdrawalSlot.isEmpty()) {
            displayScreen.displayMessage("ATM IS CURRENTLY OUT OF CASH");
            isOperational = false;
        }

        //Checks for Operational Status
        if (!isOperational) {
            displayScreen.displayMessage("ATM is in inoperable condition.");
            displayScreen.displayMessage("Sorry for the inconvience.");
            displayScreen.displayMessage("Please visit at an another time.");
            System.exit(0);
        }

        displayScreen.displayMessage("--- --- --- --- --- WELCOME --- --- --- --- ---");

        Account acc = null;
        int PIN = 0, i = 0;

        //Gets Account Number
        do {
            displayScreen.displayMessageSL("Enter your Account Number: ");
            int aN = numberPad.getCode();
            if (aN == 0) System.exit(0);
            String tmpAcc = String.valueOf(aN);
            if (tmpAcc.length() != 6) throw new Exception("Invalid Account Number(size != 6).");
            acc = database.getAccount(aN);
            
            if (acc != null)
            if (acc.blockStatus()) {
                displayScreen.displayMessage("Your have tried to access a blocked account.");
                displayScreen.displayMessageSL("Enter PIN and authenticate: ");
                PIN = numberPad.getCode();

                if (acc.blockStatus(PIN, displayScreen, numberPad)){
                    currAccount = acc;
                    break;
                }else{
                    displayScreen.displayMessage("Authentication Failed.");
                    this.reset();
                    this.beginSession();
                }
            }
        } while (acc == null); //Get Valid account number

        

        if (currAccount == null){
            while (i < maxLimit) {
                displayScreen.displayMessageSL("Enter your PIN Code: ");
                PIN = numberPad.getCode();
    
                if (!acc.authenticatePINcode(PIN)) {
                    i++;
                    displayScreen.displayMessage("Attempt: " + i + "/" + maxLimit);
                } else {
                    break;
                }
    
                if (i == maxLimit) {
                    displayScreen.displayMessage("You have reached the max limit for PIN Code.");
                    displayScreen.displayMessage("Your Account has been blocked.");
                    displayScreen.displayMessage("This Session will terminate now.");
                    acc.blockAccount();
    
                    this.reset();
                    this.beginSession();
                }
            }
    
            i = 0;
    
            while (i < maxLimit) {
                displayScreen.displayMessage("Enter OTP: ");
                if (!acc.tfauthenticatePINcode(PIN, displayScreen, numberPad)) {
                    i++;
                    displayScreen.displayMessage("Attempt: " + i + "/" + maxLimit);
                } else {
                    currAccount = acc;
                    break;
                }
    
                if (i == maxLimit) {
                    displayScreen.displayMessage("You have reached the max limit for OTP.");
                    displayScreen.displayMessage("Your Account has been blocked.");
                    displayScreen.displayMessage("This Session will terminate now.");
                    acc.blockAccount();
    
                    this.reset();
                    this.beginSession();
                }
            }
        }

        //Waiting for Garbage Collection
        PIN = 0;
        acc = null;
        i = 0;

        this.completeTransaction();
    }

    private int displayMenu() throws Exception {
        int i = -1;

        // Main Display Menu
        do {
            displayScreen.displayMessage("--- --- --- MAIN MENU --- --- ---");
            displayScreen.displayMessage("1: View Balance             ");
            displayScreen.displayMessage("2: Withdraw Amount          ");
            displayScreen.displayMessage("3: Deposit Amount           ");
            displayScreen.displayMessage("4: Update Basic Profile(PIN)");

            displayScreen.displayMessage("\n0: End Session      ");
            displayScreen.displayMessageSL("SELECT AN OPTION: ");
            i = numberPad.getCode();
        } while (i < 0 || i > 4);

        if (i == 0) {
            this.reset();
            this.beginSession();
            return 0;
        }

        return i;
    }

    private int displayWithdrawalMenu() throws Exception {
        int i = -1;

        //Withdrawal Menu
        do {
            displayScreen.displayMessage("--- --- --- SELECT AMOUNT --- --- ---\n");
            displayScreen.displayMessage("0: End Session \n");
            displayScreen.displayMessage("1: 100");
            displayScreen.displayMessage("2: 200");
            displayScreen.displayMessage("3: 500");
            displayScreen.displayMessage("4: 2000");
            displayScreen.displayMessage("5: Enter Amount");
            displayScreen.displayMessageSL("SELECT AN OPTION: ");
            i = numberPad.getCode();
        } while (i < 0 && i > 5);

        if (i == 0) {
            this.reset();
            this.beginSession();
            return 0;
        }

        return i;
    }

    private void completeTransaction() throws Exception {
        Report report = null;

        int op = this.displayMenu();

        switch (op) {
            case 1:
                // Display Balance Details
                displayScreen.displayMessage("\n --- --- --- Balance Details --- --- --- ");
                displayScreen.displayMessage("CUSTOMER NAME       :  " + currAccount.getName());
                displayScreen.displayMessage("ACCOUNT NUMBER      :  " + currAccount.getAccountNumber());
                displayScreen.displayMessage("\nAVAILABLE BALANCE :  " + currAccount.getBalance());

                report = new Report(currAccount.getName(), currAccount.getAccountNumber(), TransactionType.BALANCE_ENQUIRY, currAccount.getBalance(),0);
                break;

            case 2:
                // Withdraw Cash
                int sel = this.displayWithdrawalMenu();
                double amount = 0;

                switch (sel) {
                    case 1:
                        amount = 100;
                        break;

                    case 2:
                        amount = 200;
                        break;

                    case 3:
                        amount = 500;
                        break;

                    case 4:
                        amount = 2000;
                        break;

                    case 5:
                        displayScreen.displayMessageSL("Enter your Withdrawal Amount:");
                        amount = numberPad.getAmount();
                        break;
                    }
                    
                    if (withdrawalSlot.isEmpty()) {
                        displayScreen.displayMessage("FATAL ERROR: CASH TRAY IS EMPTY");
                        displayScreen.displayMessage("THIS SESSION WILL TERMINATE");
                        this.reset();
                        this.beginSession();
                    }

                    boolean flg = false;

                    do {
                        //Check for validity
                        flg = !(withdrawalSlot.isValidAmount(amount) && withdrawalSlot.isValidDenomination(amount));

                        if (currAccount.canWithdrawAmount(amount)){
                            if (!withdrawalSlot.isValidAmount(amount)) {
                                displayScreen.displayMessage("Please enter smaller denominations.");
                                displayScreen.displayMessageSL("Enter your Withdrawal Amount:");
                                amount = numberPad.getAmount();
                                continue;
                            }

                            if (!withdrawalSlot.isValidDenomination(amount)) {
                                displayScreen.displayMessage("Please enter a multiple of 100");
                                displayScreen.displayMessageSL("Enter your Withdrawal Amount:");
                                amount = numberPad.getAmount();
                                continue;
                            }
                        }
                        else{
                            displayScreen.displayMessage("Your Balance is insufficient.");
                            displayScreen.displayMessageSL("Enter your Withdrawal Amount:");
                            amount = numberPad.getAmount();
                            continue;
                        }
                    } while (flg || !currAccount.canWithdrawAmount(amount));

                report = withdrawalSlot.process(currAccount, amount);
                break;

            case 3:
                amount = 0;
                //Desposit Cash Bill by Bill
                displayScreen.displayMessage("--- --- --- DEPOSIT AMOUNT --- --- ---\n");
                displayScreen.displayMessage("      *** DEPOSIT BOX ACTIVE ***        ");

                displayScreen.displayMessage("NOTE: You can't deposit more than 50,000 at once.");
                displayScreen.displayMessage("NOTE: Amount only till 50,000 will be processed at once.");
                displayScreen.displayMessage("Press 0 to terminate this session.\n");
                displayScreen.displayMessage("Put Cash In Deposit Box Bill by Bill.");
                displayScreen.displayMessage("Wait for the Amount to show up.");
                displayScreen.displayMessage("Enter X after completion.");

                String rawData = new String();
                //Get Bill One by one and check for validity
                do {
                    //Mimics port activity of depositSlot(Scanning part)
                    if (rawData.equalsIgnoreCase("0")){
                        this.completeTransaction();
                    }

                    rawData = numberPad.getRawInput();
                    if (rawData.equalsIgnoreCase("X")){
                        displayScreen.displayMessage("Final Amount: " + amount);
                        break;
                    }
                    if (despositSlot.isValidDenomination(rawData)){
                        amount += Integer.parseInt(rawData);
                        displayScreen.displayMessage("Current Amount: " + amount);
                    }else{
                        displayScreen.displayMessage("Invalid Bill");
                    }
                } while (!rawData.equalsIgnoreCase("X") && amount <= 50_000);

                report = despositSlot.process(currAccount, amount);
                break;

            case 4:
                /*
                    Update Profile for security reasons only PIN can be changed.
                */

                displayScreen.displayMessage("--- --- --- UPDATE PROFILE --- --- ---");
                displayScreen.displayMessage("ONLY PIN CAN BE CHANGED.");
                
                int oldPIN, newPIN;
                displayScreen.displayMessageSL("Enter Old PIN: ");
                oldPIN = numberPad.getCode();
                displayScreen.displayMessageSL("Enter New PIN: ");
                newPIN = numberPad.getCode();

                if (currAccount.changePIN(oldPIN, newPIN, displayScreen, numberPad)){
                    displayScreen.displayMessage("Process Successful");
                    displayScreen.displayMessage("Use NEW PIN next time.");
                }else{
                    displayScreen.displayMessage("Process Failed.");
                    displayScreen.displayMessage("Try Again Next Time");
                }
                displayScreen.displayMessage("This Session Will Now Terminate.");

                this.reset();
                this.beginSession();
            break;

            case 0:
                this.reset();
                this.beginSession();
            break;
        }

        //Generate recepit and send to Printer
        Recepit recepit = new Recepit(uniqueID, location, report);
        recepitPrinter.printRecepit(recepit);

        this.completeTransaction();
    }
}
