package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;

import java.math.BigDecimal;

public interface AccountService {
    BigDecimal getBalance();

}
