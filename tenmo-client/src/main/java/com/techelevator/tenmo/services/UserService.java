package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

public class UserService {
    String baseUrl;

    private RestTemplate restTemplate = new RestTemplate();

    public UserService(String baseUrl) {
        this.baseUrl = baseUrl;
        this.restTemplate = restTemplate;
    }

    public User getUser(Long id, AuthenticatedUser authenticatedUser) {
        HttpEntity<Void> entity = makeAuthEntity(authenticatedUser);
        User user = new User();

        return restTemplate.exchange(baseUrl + "/user/" + id, HttpMethod.GET, entity, User.class).getBody();

    }

    private HttpEntity<Void> makeAuthEntity(AuthenticatedUser authenticatedUser) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authenticatedUser.getToken());
        HttpEntity entity = new HttpEntity<>(headers);
        return entity;

    }
}
