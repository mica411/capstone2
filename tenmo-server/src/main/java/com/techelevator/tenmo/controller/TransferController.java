package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@PreAuthorize("isAuthenticated()")
public class TransferController {

    @Autowired
    private AccountDao accountDao;
    @Autowired
    private UserDao userDao;
//    @Autowired
    private TransferDao transferDao;


    @GetMapping (path = "transfer/amount")
    public Transfer getTransferAmount () {
        return transferDao.getTransferAmount();
    }

    //@GetMapping (path = "/transfers")
//    public Transfer[] allTransfers (Principal principal){
//    return transferDao.getTransferAmount(principal.getName());
//}
}
