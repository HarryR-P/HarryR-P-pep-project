package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;
    
    public AccountService(){
        accountDAO = new AccountDAO();
    }

    /**
     * Register new user if username is not blank and password has four or more characters
     * Will not register user if username already exists.
     * @param account without account ID
     * @return Account added with account ID or null if invalid
     */
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
        if(accountDAO.getAccountByUsername(account.getUsername()) != null){
            return null;
        }
        return accountDAO.insertAccount(account);
    }

    /**
     * Checks if user with given usernam and password exists
     * @param account with username and password
     * @return account with account ID or null if the user does not exist.
     */
    public Account loginAccount(Account account){
        if(account.getUsername() == null || account.getPassword() == null){
            return null;
        }
        return accountDAO.getAccount(account);
    }
}
