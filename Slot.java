/*
,---------------------------------------------------------.
|Slot                                                     |
|---------------------------------------------------------|
|#displayScreen: DisplayScreen                            |
|+Slot(displayScreen: DisplayScreen)                      |
|+{abstract} process(acc: Account, amount: double): Report|
`---------------------------------------------------------'

*/

//Abstract Class to show the processes associated with depositSlot and withdrawalSlot
public abstract class Slot {
    /*
        We don't have an actual slot for depositing, withdrawing money. 
        We just show changes using displayScreen
    */

    protected DisplayScreen displayScreen;

    public Slot(DisplayScreen displayScreen){
        this.displayScreen = displayScreen;
    }

    public abstract Report process(Account acc, double amount);
}
