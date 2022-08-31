package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class TransferController {

    @Autowired
    private AccountDao accountDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private TransferDao transferDao;


    @GetMapping(path = "/transfers/amount")
    public Transfer getTransferAmount() {
        return transferDao.getTransferAmount();
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(path = "/transfers", method = RequestMethod.GET)
    public List<Transfer> listTransfers() {
        List<Transfer> transfers = new ArrayList<>();

        return transferDao.getAllTransfers();

    }

    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('USER')")
    @RequestMapping(path = "/transfers", method = RequestMethod.POST)
    public Transfer create(@RequestBody Transfer transferMade) {

        BigDecimal amountTransfer = transferMade.getAmount();
        Account accountF = accountDao.getAccountByAccountId(transferMade.getAccountFrom());
        Account accounT = accountDao.getAccountByAccountId(transferMade.getAccountTo());

        accountDao.subtractFromBalance(amountTransfer, accountF.getUserId());
        accountDao.addToBalance(amountTransfer, accounT.getUserId());


        transferDao.createTransfer(transferMade);

        return transferMade;
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(path = "transfers/{id}", method = RequestMethod.GET)
    public Transfer getTransfer(@PathVariable int id) {
        return transferDao.getTransferByTransferId(id);
    }

}
