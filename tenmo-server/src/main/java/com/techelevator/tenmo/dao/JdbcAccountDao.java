package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Balance;
import org.jboss.logging.BasicLogger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.security.Principal;

@Component
public class JdbcAccountDao implements AccountDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

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
    public Balance getBalance(String username){

        String sql = "SELECT balance FROM account JOIN tenmo_user ON account.user_id = tenmo_user.user_id WHERE username = ?";
        SqlRowSet results= jdbcTemplate.queryForRowSet(sql, username);
        Balance balance = new Balance();

        if (results.next()) {
           String accountBal= results.getString("balance");
           balance.setBalance(new BigDecimal(accountBal));
        }
        return balance;
    }


    //used for defining rows in the account table.
//    private Account mapRowToAccount(SqlRowSet result) {
//        Account account = new Account();
//
//        account.setAccountId(result.getInt("account_id"));
//        account.setUserId(result.getInt("user_id"));
//        account.setBalance(result.getBigDecimal("balance"));
//        return account;
//    }

}
