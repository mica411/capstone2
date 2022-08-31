package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Balance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@PreAuthorize("isAuthenticated()")
public class AccountController {
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private UserDao userDao;

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(path = "/balance", method = RequestMethod.GET)
    public Balance getBalance(Principal principal) {
        return accountDao.getBalance(principal.getName());
    }

//    @PreAuthorize("hasRole('USER')")
//    @RequestMapping(path = "/balance/{id}", method= RequestMethod.PUT)
//    public Account addBalance(@RequestBody Account account, @PathVariable int id){
//      return null;
//    }
    @PreAuthorize("hasRole('USER')")
    @RequestMapping(path = "/accounts/{userId}", method = RequestMethod.GET)
    public Account getAccountByUserId(@PathVariable int userId) {
    return accountDao.getAccountByUserId(userId);
}


    @PreAuthorize("hasRole('USER')")
    @RequestMapping(path = "/account/{accountId}", method = RequestMethod.GET)
    public Account getAccountByAccountId(@PathVariable int accountId) {
        return accountDao.getAccountByAccountId(accountId);
    }




}
