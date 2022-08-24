package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class TransferServiceRest {

    private final String baseUrl;
    private RestTemplate restTemplate;
    private AuthenticatedUser authenticatedUser;

    public TransferServiceRest(String baseUrl, RestTemplate restTemplate) {
        this.baseUrl = baseUrl;
        this.restTemplate = restTemplate;
    }

    public Transfer[] getTransfers(AuthenticatedUser authenticatedUser){
        Transfer [] transfers=null;
        try{
            transfers= restTemplate.exchange(baseUrl+ "/transfers",
                    HttpMethod.GET,
                    makeAuthEntity(authenticatedUser),
                    Transfer[].class).getBody();

        }catch(RestClientResponseException | ResourceAccessException e){
            BasicLogger.log(e.getMessage());
        }
        return transfers;
    }

    public boolean create(AuthenticatedUser authenticatedUser, Transfer transfer){
      boolean transferCreated= false;
        try{
            restTemplate.exchange(baseUrl+"/transfers/"+ transfer.getTransferId(),
                    HttpMethod.POST,
                    makeEntity(transfer),
                    Transfer.class);
            transferCreated = true;
    }catch(RestClientResponseException | ResourceAccessException e){
            BasicLogger.log(e.getMessage());
        }
        return transferCreated;

}

    public Transfer getTransferByTransferId(AuthenticatedUser authenticatedUser, int id){
        Transfer transfer= null;
        try{
            transfer= restTemplate.exchange(baseUrl+"/transfers/"+ id,
                    HttpMethod.GET,
                    makeAuthEntity(authenticatedUser),
                    Transfer.class).getBody();
        } catch(RestClientResponseException | ResourceAccessException e){
            BasicLogger.log(e.getMessage());
        }
        return transfer;
    }

    public boolean updateTransfer(AuthenticatedUser authenticatedUser, Transfer transfer){
        boolean updated= false;
        try{
            restTemplate.exchange(baseUrl+"/transfers/"+ transfer.getTransferId(),
                    HttpMethod.PUT,
                    makeEntity(transfer),
                    Transfer.class);
            updated=true;
        }catch(RestClientResponseException | ResourceAccessException e){
            BasicLogger.log(e.getMessage());
        }
    return updated;
    }

    public Transfer[] getTransfersByUserId(AuthenticatedUser authenticatedUser, int userId){
        Transfer[] transfers= null;
        try{
            transfers= restTemplate.exchange(baseUrl+"transfers/user/"+userId, HttpMethod.GET, makeAuthEntity(authenticatedUser), Transfer[].class).getBody();
        }catch(RestClientResponseException | ResourceAccessException e){
            BasicLogger.log(e.getMessage());
        }
        return transfers;
    }

    private HttpEntity<Void> makeAuthEntity(AuthenticatedUser authenticatedUser) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authenticatedUser.getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(headers);
}
    private HttpEntity<Transfer> makeEntity(Transfer transfer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authenticatedUser.getToken());
         HttpEntity<Transfer> transferEntity= new HttpEntity<> (transfer, headers);
    return transferEntity;
}
}
