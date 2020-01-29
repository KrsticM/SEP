package tim18.ftn.uns.ac.rs.paypalpayment.service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import tim18.ftn.uns.ac.rs.paypalpayment.model.PaypalPlan;
import tim18.ftn.uns.ac.rs.paypalpayment.model.PaypalSubscription;
import tim18.ftn.uns.ac.rs.paypalpayment.repository.PaypalSubscriptionRepository;

@Service
public class SubscriptionService {
	@Autowired
    AccessTokenService tokenService;
	
	@Autowired
	PaypalSubscriptionRepository subscriptionRepository;

	public PaypalSubscription createSubscription(PaypalPlan plan) throws UnsupportedEncodingException {
		RestTemplate restTemplate = new RestTemplate();
		Optional<PaypalSubscription> alreadyCreated = subscriptionRepository.findByPlanId(plan.getPlanId());
		if (alreadyCreated.isPresent()) {
			return alreadyCreated.get();
		}
        String paypalAPI = "https://api.sandbox.paypal.com/v1/billing/subscriptions";
        String defJson = "{\n" +
                "  \"plan_id\": \"" + plan.getPlanId() + "\",\n" +
                "  \"quantity\": 1,\n" +
                "  \"subscirber\": {\n" +
                "    \"email_address\": \"TO_BE_ADDED\"\n" +
                "  }\n" +
                "}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + tokenService.getAccessToken(plan.getMerchant()));

        HttpEntity<String> entity = new HttpEntity<String>(defJson, headers);
        String jsonResponse = restTemplate.postForObject(paypalAPI, entity, String.class);
        Gson gson = new Gson();
        PaypalSubscription subscription = new PaypalSubscription(
        		plan.getMerchant(),
        		gson.fromJson(jsonResponse, JsonObject.class).get("id").getAsString(),
        		plan.getPlanId(),
            	gson.fromJson(jsonResponse, JsonObject.class)
            		.get("links")
            		.getAsJsonArray()
            		.get(0)
            		.getAsJsonObject()
            		.get("href")
            		.getAsString(),
            	gson.fromJson(jsonResponse, JsonObject.class).get("status").getAsString()
        );
        
        subscriptionRepository.save(subscription);
        
        return subscription;
	}
	

	public ResponseEntity<String> getSubscriptionDetails(PaypalSubscription subscription) throws UnsupportedEncodingException {
		String paypalAPI = "https://api.sandbox.paypal.com/v1/billing/subscriptions/"
        	+ subscription.getSubscriptionId();
		RestTemplate restTemplate = new RestTemplate();
		
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + tokenService.getAccessToken(subscription.getMerchant()));
        HttpEntity entity = new HttpEntity(headers);
        return restTemplate.exchange(
        		paypalAPI, HttpMethod.GET, entity, String.class);
	}
	

	@Scheduled(fixedDelay = 180000)
	public void checkSubscriptions() throws UnsupportedEncodingException {
		List<PaypalSubscription> checkSubscriptions = subscriptionRepository.findAllByStatus("APPROVAL_PENDING");

	    for(PaypalSubscription subscription : checkSubscriptions) {
	    	String paypalAPI = "https://api.sandbox.paypal.com/v1/billing/subscriptions/"
	            	+ subscription.getSubscriptionId();
    		RestTemplate restTemplate = new RestTemplate();
    		
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + tokenService.getAccessToken(subscription.getMerchant()));
            HttpEntity entity = new HttpEntity(headers);
            try {
	            ResponseEntity<String> subscriptionDetail = restTemplate.exchange(
	        		paypalAPI, HttpMethod.GET, entity, String.class);
		    	Gson gson = new Gson();
		    	String response = subscriptionDetail.getBody();
		    	String status = gson.fromJson(response, JsonObject.class).get("status").getAsString();
		    	if(status.contentEquals("APPROVED")) {
	//	    		Aktiviranje subskripcije
		    		String paypalAPIActivate = "https://api.sandbox.paypal.com/v1/billing/subscriptions/"
			        	+ subscription.getSubscriptionId() + "/activate";
		    		HttpEntity<String> postEntity = new HttpEntity<String>("", headers); 
			    	String responseActivation = restTemplate.postForObject(paypalAPIActivate, postEntity, String.class);
					subscription.setStatus("ACTIVE");
		    	}
		    	System.out.println(subscription.toString());
		    	
		    	subscription.setStatus(status);
            }  catch (final HttpClientErrorException e) {
            	subscription.setStatus("ERROR");
            }
	    }
    	subscriptionRepository.saveAll(checkSubscriptions);
	    
	}

	public String activateSubscription(int subscriptionId, int appId) throws UnsupportedEncodingException {
		String paypalAPI = "https://api.sandbox.paypal.com/v1/billing/subscriptions/"
        	+ subscriptionId + "/activate";
		RestTemplate restTemplate = new RestTemplate();
		
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + tokenService.getAccessToken(appId));
        HttpEntity entity = new HttpEntity<String>("", headers); 
        return restTemplate.postForObject(paypalAPI, entity, String.class);
	}
	
	public String cancelSubscription(int subscriptionId, int appId) throws UnsupportedEncodingException {
		String paypalAPI = "https://api.sandbox.paypal.com/v1/billing/subscriptions/"
        	+ subscriptionId + "/cancel";
		RestTemplate restTemplate = new RestTemplate();
		
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + tokenService.getAccessToken(appId));
        HttpEntity entity = new HttpEntity<String>("", headers); 
        return restTemplate.postForObject(paypalAPI, entity, String.class);
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
