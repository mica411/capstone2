package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Balance;

import java.math.BigDecimal;
import java.security.Principal;

public interface AccountDao {

    Balance getBalanceByUserId(int id);
    Balance getBalance(String user);


}
