package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Balance;
import com.techelevator.tenmo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

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
    @RequestMapping(path = "/accounts/{id}", method = RequestMethod.GET)
    public Account getAccountByUserId(@PathVariable Long userId) {
    return accountDao.getAccountByUserId(userId);
}



}
