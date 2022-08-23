package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Balance;
import com.techelevator.tenmo.model.User;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

public interface AccountService {
    Balance getBalance(AuthenticatedUser authenticatedUser);


//    List <User> userList (long id, String username);
//
//    List<User> userList();

//    List<User> userList();
}
