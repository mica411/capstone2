package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Balance;

public interface AccountService {

    Balance getBalance(AuthenticatedUser authenticatedUser);

}
