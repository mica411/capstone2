package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class UserController {

    private UserDao userDao;
    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }


    @RequestMapping(path = "/user/{id}", method = RequestMethod.GET)
    public User listUserById(@PathVariable Long id) {
        return userDao.findById(id);
    }


    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public List<User> getAllUsers(Principal principal) {
        return userDao.findAll();
    }


//    @RequestMapping(path = "/users/account/{accountId}", method = RequestMethod.GET)
//    public User getUserByAccountId(@PathVariable int accountId){
//        return userDao.getUserByAccountId(accountId);
//    }
}
