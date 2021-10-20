import java.util.ArrayList;

/*
,--------------------------------------------------------------------------------------------------.
|AccountDatabase                                                                                   |
|--------------------------------------------------------------------------------------------------|
|-accountList: ArrayList<Account>                                                                  |
|+AccountDatabase()                                                                                |
|+getAccount(accountNumber: int): Account                                                          |
|+addAccount(name: String, accNumber: int, pin: int, phoneNumber: String, balance: double): boolean|
`--------------------------------------------------------------------------------------------------'

*/
public class AccountDatabase {
    private ArrayList<Account> accountList;

    public AccountDatabase(){
        accountList = new ArrayList<>();

        accountList.add(new Account("AAA", 120123, 12345, "123456789", 10000));
        accountList.add(new Account("BBB", 120124, 67890, "987654321", 20000));
        accountList.add(new Account("CCC", 120125, 13579, "102013029", 30000));
    }

    //Get account from database
    public Account getAccount(int accountNumber){
        Account acc = null;

        for (int i = 0;i < accountList.size();i++)
        {
            acc = accountList.get(i);
            if (acc.isAccountNumber(accountNumber)) return acc;
        }

        return null;
    }

    //ADD Account to database
    public boolean addAccount(String name, int accNumber, int pin, String phoneNumber, double balance){
        if(getAccount(accNumber) == null){
            accountList.add(new Account(name, accNumber, pin, phoneNumber, balance));
            return true;
        }
        return false;
    }
}
