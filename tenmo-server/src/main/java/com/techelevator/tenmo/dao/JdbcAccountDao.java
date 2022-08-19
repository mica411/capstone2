package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.jboss.logging.BasicLogger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.math.BigDecimal;

public class JdbcAccountDao implements AccountDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public BigDecimal getBalanceByUserId(int id) {
        String sql = "SELECT balance FROM account WHERE user_id = ?;";
        BigDecimal balance = null;

        try {
            balance = jdbcTemplate.queryForObject(sql, BigDecimal.class, id);
        } catch (DataAccessException e) {
            System.out.println("Error getting data from database");
        }
        return balance;
    }


    //used for defining rows in the account table.
    private Account mapRowToAccount(SqlRowSet result) {
        Account account = new Account();

        account.setAccountId(result.getInt("account_id"));
        account.setUserId(result.getInt("user_id"));
        account.setBalance(result.getBigDecimal("balance"));
        return account;
    }

}
