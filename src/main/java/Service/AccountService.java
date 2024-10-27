package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;
    
    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public Account registerUser(Account account){
        if(account.getUsername() == null || account.getPassword() == null){
            return null;
        }
        if(account.getUsername().length() == 0){
            return null;
        }
        if(account.getPassword().length() < 4){
            return null;
        }
        // return null if account already exists
        if(accountDAO.getAccountByUsername(account.getUsername()) != null){
            return null;
        }
        return accountDAO.insertAccount(account);
    }

    public Account loginAccount(Account account){
        if(account.getUsername() == null || account.getPassword() == null){
            return null;
        }
        return accountDAO.getAccount(account);
    }
}
