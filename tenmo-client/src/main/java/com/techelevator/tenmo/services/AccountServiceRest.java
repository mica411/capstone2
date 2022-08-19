package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Balance;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class AccountServiceRest implements AccountService{

    private static String API_BASE_URL = "http://localhost:8080";
    private String fakeUrl;
    private RestTemplate restTemplate ;
    private AuthenticatedUser authenticatedUser;

    public AccountServiceRest(String fakeUrl) {
        this.fakeUrl = fakeUrl;
        this.restTemplate= new RestTemplate();
    }

    // Unsure if to use BigDecimal or Balance as data type.
    @Override
    public Balance getBalance(AuthenticatedUser authenticatedUser) {
        HttpEntity entity = makeAuthEntity(authenticatedUser);
        Balance balance = null;
        try {
            balance = restTemplate.exchange(fakeUrl + "/balance", HttpMethod.GET, entity, Balance.class).getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return balance;
    }

    private HttpEntity makeAuthEntity(AuthenticatedUser authenticatedUser) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authenticatedUser.getToken());
        HttpEntity entity= new HttpEntity(headers);
            return entity;
    }
}
