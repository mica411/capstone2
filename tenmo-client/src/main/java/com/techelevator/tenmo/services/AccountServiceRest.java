package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.*;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class AccountServiceRest {

    private final String baseUrl;
    private RestTemplate restTemplate;
    private AuthenticatedUser authenticatedUser;

    public AccountServiceRest(String baseUrl) {
        this.baseUrl = baseUrl;
        this.restTemplate = new RestTemplate();
    }

    public Balance getBalance(AuthenticatedUser authenticatedUser) {
        HttpEntity<Void> entity = makeAuthEntity(authenticatedUser);
        Balance balance = null;
        try {
            balance = restTemplate.exchange(baseUrl + "/balance", HttpMethod.GET, entity, Balance.class).getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return balance;
    }

    public User[] getAllUsers(AuthenticatedUser authenticatedUser) {
        HttpEntity<Void> entity = makeAuthEntity(authenticatedUser);
        User[] users = null;
        try {
            ResponseEntity<User[]> response =
                    restTemplate.exchange(baseUrl + "/users", HttpMethod.GET, entity, User[].class);
            users = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return users;
    }

    public Account getAccount(int accountId, AuthenticatedUser authenticatedUser) {
        HttpEntity<Void> entity = makeAuthEntity(authenticatedUser);
        Account account = new Account();

        account = restTemplate.exchange(baseUrl + "account/" + accountId, HttpMethod.GET, entity, Account.class).getBody();
        return account;
    }

    public Account getAccountByUserId(Long userId, AuthenticatedUser authenticatedUser) {
        HttpEntity<Void> entity = makeAuthEntity(authenticatedUser);
        Account account = new Account();
        account = restTemplate.exchange(baseUrl + "accounts/" + userId, HttpMethod.GET, entity, Account.class).getBody();
        return account;
    }


    public User getUserByAccountId(int accountId, AuthenticatedUser authenticatedUser) {
        HttpEntity<Void> entity = makeAuthEntity(authenticatedUser);
        User user = new User();

        return restTemplate.exchange(baseUrl + "/users/account/" + accountId, HttpMethod.GET, entity, User.class).getBody();

    }


    private HttpEntity<Void> makeAuthEntity(AuthenticatedUser authenticatedUser) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authenticatedUser.getToken());
        HttpEntity entity = new HttpEntity<>(headers);
        return entity;

    }

    private HttpEntity<Account> makeEntity(Account account) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authenticatedUser.getToken());
        HttpEntity<Account> accountEntity = new HttpEntity<>(account, headers);
        return accountEntity;
    }


}
