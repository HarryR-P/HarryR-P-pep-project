package DAO;

import java.sql.*;
import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {

    public Account getAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            ResultSet results = preparedStatement.executeQuery();
            if(results.next()){
                return new Account(
                    results.getInt("account_id"),
                    results.getString("username"),
                    results.getString("password")
                    );
            }
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return null;
    }

    public Account getAccountByUsername(String username){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,username);

            ResultSet results = preparedStatement.executeQuery();
            if(results.next()){
                return new Account(
                    results.getInt("account_id"),
                    results.getString("username"),
                    results.getString("password")
                    );
            }
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return null;
    }

    public Account getAccountByID(int accountID){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account WHERE account_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,accountID);

            ResultSet results = preparedStatement.executeQuery();
            if(results.next()){
                return new Account(
                    results.getInt("account_id"),
                    results.getString("username"),
                    results.getString("password")
                    );
            }
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return null;
    }

    public Account insertAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO account (username, password) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2,account.getPassword());
            preparedStatement.executeUpdate();
            ResultSet genResultSet = preparedStatement.getGeneratedKeys();
            if(genResultSet.next()){
                int accountID = (int) genResultSet.getLong(1);
                return new Account(accountID, account.getUsername(), account.getPassword());
            }
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        return null;
    }
}
