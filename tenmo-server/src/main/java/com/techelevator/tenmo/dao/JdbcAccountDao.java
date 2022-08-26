package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Balance;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.security.auth.login.AccountNotFoundException;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Component
public class JdbcAccountDao implements AccountDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    public static List<Account> accounts = new ArrayList<>();

    @Override
    public Balance getBalanceByUserId(int id) {
        String sql = "SELECT balance FROM account WHERE user_id = ?;";
        Balance balance = null;

        try {
            balance = jdbcTemplate.queryForObject(sql, Balance.class, id);
        } catch (DataAccessException e) {
            System.out.println("Error getting data from database");
        }
        return balance;
    }

    @Override
    public Balance getBalance(String username) {
        String sql = "SELECT balance FROM account JOIN tenmo_user ON account.user_id = tenmo_user.user_id WHERE username = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);
        Balance balance = new Balance();

        if (results.next()) {
            String accountBal = results.getString("balance");
            balance.setBalance(new BigDecimal(accountBal));
        }
        return balance;
    }
    @Override
    public void updateAccountBalance(Account account){
        String sql= "UPDATE account SET balance= ? WHERE user_id=?";
        jdbcTemplate.update(sql, account.getBalance().getBalance(), account.getUserId());
    }
    @Override
    public void addToBalance(BigDecimal amount, int userId){
        Account account= findAccountById(userId);
        BigDecimal newBal= account.getBalance().getBalance().add(amount);
        String sql= "UPDATE account SET balance=? WHERE user_id=?";
        jdbcTemplate.update(sql, newBal, userId);
    }

    @Override
    public void subtractFromBalance(BigDecimal amount, int userId){
        Account account= findAccountById(userId);
        BigDecimal newBal= account.getBalance().getBalance().subtract(amount);
        String sql= "UPDATE account SET balance=? WHERE user_id=?";
        jdbcTemplate.update(sql, newBal, userId);

    }
    @Override
    public Account findAccountById(int id) {
        Account account = null;
        String sql = "SELECT account_id, user_id, balance FROM account WHERE user_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        if (results.next()) {
            account = mapRowToAccount(results);
        }
        return account;
    }

    @Override
    public Account getAccountByAccountId(int accountId){
        Account account= null;
        String sql= "SELECT account_id, user_id, balance FROM account WHERE account_id=?";
        SqlRowSet results= jdbcTemplate.queryForRowSet(sql, accountId);
        if(results.next()){
            account=mapRowToAccount(results);
        }
        return account;

    }

    @Override
    public Account getAccountByUserId(Long userId){
        Account account= null;
        String sql= "SELECT account_id, user_id, balance FROM account WHERE user_id=?";
        SqlRowSet results= jdbcTemplate.queryForRowSet(sql, userId);
        if(results.next()){
            account=mapRowToAccount(results);
        }
        return account;

    }
    //TODO: DO NOT DELETE WE NEED IT FOR LATER

    private Account mapRowToAccount(SqlRowSet result) {
        Account account = new Account();

        account.setAccountId(result.getInt("account_id"));
        account.setUserId(result.getInt("user_id"));

        String accountBal= result.getString("balance");

        Balance bal= new Balance();
        bal.setBalance(new BigDecimal(accountBal));

        account.setBalance(bal);

        return account;
    }



}
