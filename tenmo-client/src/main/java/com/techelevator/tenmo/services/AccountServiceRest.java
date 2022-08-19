package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class AccountServiceRest implements AccountService{

    private static String API_BASE_URL = "http://localhost:8080/";
    private RestTemplate restTemplate = new RestTemplate();
    private AuthenticatedUser authenticatedUser;

    public AccountServiceRest(String API_BASE_URL, AuthenticatedUser authenticatedUser) {
        this.API_BASE_URL = API_BASE_URL;
        this.authenticatedUser = authenticatedUser;
    }

    // Unsure if to use BigDecimal or Balance as data type.
    @Override
    public BigDecimal getBalance() {
        HttpEntity<Void> entity = makeAuthEntity();
        BigDecimal balance = null;
        try {
            balance = restTemplate.exchange(API_BASE_URL + "balance/" + authenticatedUser.getUser().getId(), HttpMethod.GET, entity, BigDecimal.class).getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return balance;
    }

    private HttpEntity<Void>makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authenticatedUser.getToken());
        return new HttpEntity<>(headers);
    }
}
