package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Balance;

public interface AccountDao {
    Balance getBalanceByUserId(int id);

    Balance getBalance(String user);
}
