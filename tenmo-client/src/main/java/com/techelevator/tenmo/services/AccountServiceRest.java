package com.techelevator.tenmo.services;

//import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.*;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

//public class AccountServiceRest implements AccountService {
public class AccountServiceRest {
    //Deleted old API_BASE_URL not needed.
    private final String baseUrl;
    //Do not change to *final* even if it recommends it.
    private RestTemplate restTemplate;
    private AuthenticatedUser authenticatedUser;
//    private String authToken = null;

    public AccountServiceRest(String baseUrl) {
        this.baseUrl = baseUrl;
        this.restTemplate = new RestTemplate();
    }

//    @Override
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
    public User[] getAllUsers(AuthenticatedUser authenticatedUser){
        HttpEntity<Void> entity = makeAuthEntity(authenticatedUser);
        User[] users = null;
        try{
            ResponseEntity<User[]> response =
                    restTemplate.exchange(baseUrl +"/users", HttpMethod.GET, entity, User[].class);
            users = response.getBody();
        }catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return users;
    }

    public Account getAccountByUserId(Long userId, AuthenticatedUser authenticatedUser){
       // HttpEntity<Void> entity= makeAuthEntity(authenticatedUser);
        Account account= new Account();
        try {
            account= restTemplate.exchange(baseUrl+ "accounts/"+ userId, HttpMethod.GET, makeAuthEntity(authenticatedUser), Account.class).getBody();
            return account;
            }catch (RestClientResponseException rcre) {
            BasicLogger.log(rcre.getRawStatusCode() + " : " + rcre.getStatusText());
        } catch (ResourceAccessException rae) {
            BasicLogger.log(rae.getMessage());
        }catch(NullPointerException npe){
            npe.printStackTrace();
        }
        return account;
        }


    public User getUsersById(int id, AuthenticatedUser authenticatedUser) {
        HttpEntity<Void> entity = makeAuthEntity(authenticatedUser);
        User user = null;
        try {
            user = restTemplate.exchange(baseUrl + "user/" + id, HttpMethod.GET, entity,
                    User.class).getBody();

        } catch (RestClientResponseException rcre) {
            BasicLogger.log(rcre.getRawStatusCode() + " : " + rcre.getStatusText());
        } catch (ResourceAccessException rae) {
            BasicLogger.log(rae.getMessage());
        }
        return user;
    }




}
