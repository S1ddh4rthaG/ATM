/*
,--------------------------------------------------------------------------------------------------.
|Report                                                                                            |
|--------------------------------------------------------------------------------------------------|
|+name: String                                                                                     |
|+accountNumber: int                                                                               |
|+transactionType: TransactionType                                                                 |
|+amount: double                                                                                   |
|+dateTime: String                                                                                 |
|+balance: double                                                                                  |
|+Report(name: String, accountNumber: int, tType: TransactionType, balance: double, amount: double)|
`--------------------------------------------------------------------------------------------------'

*/

import java.time.LocalDateTime; 
import java.time.format.DateTimeFormatter;

//Details of the transaction done are stored in a report.

public class Report {
    public final String name;
    public final int accountNumber;
    public final TransactionType transactionType;
    public final double amount;
    public final String dateTime;
    public final double balance;
    
    public Report(String name, int accountNumber, TransactionType tType,double balance, double amount){
        this.name = name;
        this.accountNumber = accountNumber;

        this.transactionType = tType;
        this.balance = balance;

        this.amount = amount;
        
        LocalDateTime ldt = LocalDateTime.now();
        this.dateTime = ldt.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }
}
