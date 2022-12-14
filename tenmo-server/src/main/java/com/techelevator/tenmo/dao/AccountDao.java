package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Balance;

import java.math.BigDecimal;

public interface AccountDao {
    Balance getBalanceByUserId(int id);

    Balance getBalance(String user);

    void updateAccountBalance(Account account);

    void addToBalance(BigDecimal amount, int id);

    void subtractFromBalance(BigDecimal amount, int id);

    Account findAccountById(int id);

    Account getAccountByAccountId(int accountId);

    Account getAccountByUserId(int userId);

    Account getUsernameByAccountId (int accountId);





}
