package tim18.ftn.uns.ac.rs.paypalpayment.service;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class SubscriptionService {
	@Autowired
    AccessTokenService tokenService;

	public String createSubscription(String planId, int appId) throws UnsupportedEncodingException {
		RestTemplate restTemplate = new RestTemplate();
        String paypalAPI = "https://api.sandbox.paypal.com/v1/billing/subscriptions";
        String defJson = "{\n" +
                "  \"plan_id\": \"" + planId + "\",\n" +
                "  \"quantity\": 1,\n" +
                "  \"subscirber\": {\n" +
                "    \"email_address\": \"TO_BE_ADDED\"\n" +
                "  }\n" +
                "}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + tokenService.getAccessToken(appId));

        HttpEntity<String> entity = new HttpEntity<String>(defJson, headers);
        String jsonResponse = restTemplate.postForObject(paypalAPI, entity, String.class);
        return jsonResponse;
	}
	

	public ResponseEntity<String> getSubscriptionDetails(int subscriptionId, int appId) throws UnsupportedEncodingException {
		String paypalAPI = "https://api.sandbox.paypal.com/v1/billing/subscriptions/"
        	+ subscriptionId;
		RestTemplate restTemplate = new RestTemplate();
		
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + tokenService.getAccessToken(appId));
        HttpEntity entity = new HttpEntity(headers);
        return restTemplate.exchange(
        		paypalAPI, HttpMethod.GET, entity, String.class);
	}

	public ResponseEntity<String> activateSubscription(int subscriptionId, int appId) throws UnsupportedEncodingException {
		String paypalAPI = "https://api.sandbox.paypal.com/v1/billing/subscriptions/"
        	+ subscriptionId + "/activate";
		RestTemplate restTemplate = new RestTemplate();
		
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + tokenService.getAccessToken(appId));
        HttpEntity entity = new HttpEntity(headers);
        return restTemplate.exchange(
        		paypalAPI, HttpMethod.POST, entity, String.class);
	}
	
	public ResponseEntity<String> cancelSubscription(int subscriptionId, int appId) throws UnsupportedEncodingException {
		String paypalAPI = "https://api.sandbox.paypal.com/v1/billing/subscriptions/"
        	+ subscriptionId + "/cancel";
		RestTemplate restTemplate = new RestTemplate();
		
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + tokenService.getAccessToken(appId));
        HttpEntity entity = new HttpEntity(headers);
        return restTemplate.exchange(
        		paypalAPI, HttpMethod.POST, entity, String.class);
	}

	public String suspendSubscription(int subscriptionId, int appId, String reason) throws UnsupportedEncodingException {
		String paypalAPI = "https://api.sandbox.paypal.com/v1/billing/subscriptions/"
        	+ subscriptionId + "/suspend";
		RestTemplate restTemplate = new RestTemplate();
		String defJson = "{\n" +
                "  \"reason\": \"" + reason + "\"\n" +
                "}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + tokenService.getAccessToken(appId));

        HttpEntity<String> entity = new HttpEntity<String>(defJson, headers);
        String jsonResponse = restTemplate.postForObject(paypalAPI, entity, String.class);
        return jsonResponse;
	}
}
