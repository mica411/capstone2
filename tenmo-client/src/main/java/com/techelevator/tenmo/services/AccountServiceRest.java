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

public class AccountServiceRest implements AccountService {
    //Deleted old API_BASE_URL not needed.
    private final String baseUrl;
    //Do not change to *final* even if it recommends it.
    private RestTemplate restTemplate;
    private AuthenticatedUser authenticatedUser;

    public AccountServiceRest(String baseUrl) {
        this.baseUrl = baseUrl;
        this.restTemplate = new RestTemplate();
    }

    @Override
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

    private HttpEntity<Void> makeAuthEntity(AuthenticatedUser authenticatedUser) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authenticatedUser.getToken());
        return new HttpEntity<>(headers);
    }
}
