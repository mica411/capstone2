package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Balance;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

public interface AccountDao {
    Balance getBalanceByUserId(int id);

    Balance getBalance(String user);



}
