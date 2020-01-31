package tim18.ftn.uns.ac.rs.paypalpayment.service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import tim18.ftn.uns.ac.rs.paypalpayment.dto.CompletePaymentResponseDTO;
import tim18.ftn.uns.ac.rs.paypalpayment.dto.SubscriptionPlanDTO;
import tim18.ftn.uns.ac.rs.paypalpayment.model.Order;
import tim18.ftn.uns.ac.rs.paypalpayment.model.PaypalPlan;
import tim18.ftn.uns.ac.rs.paypalpayment.model.PaypalSubscription;
import tim18.ftn.uns.ac.rs.paypalpayment.repository.OrderRepository;
import tim18.ftn.uns.ac.rs.paypalpayment.repository.PaypalSubscriptionRepository;

@Service
public class SubscriptionService {
	@Autowired
    AccessTokenService tokenService;
	
	@Autowired
	PaypalSubscriptionRepository subscriptionRepository;

	@Autowired
	OrderRepository ordersRepository;

	public Optional<PaypalSubscription> getSubscriptionByPlanId(String planId) {
		return subscriptionRepository.findByPlanId(planId);
	}

	public PaypalSubscription createSubscription(PaypalPlan plan, Order order, SubscriptionPlanDTO subscriptionDTO) throws UnsupportedEncodingException {
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
                "    \"email_address\": \"" + subscriptionDTO.getEmail() + "\"\n" +
                "  }" +
                // It's a problem to redirect back for localhost!
//                "  \"application_context\": {\n" +
//                "    \"return_url\": \"http://localhost:8200/view/successSubscriptionURL/" + plan.getId() + "/\",\n" +
//                "    \"cancel_url\": \"http://localhost:8200/view/cancelSubscriptionURL/" + plan.getId() + "/\"\n" +
//                "  }\n" +
                "}";
        
        System.out.println(defJson);

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
        
        subscription.setOrderScienceCenterId(order.getOrderIdScienceCenter());
        subscription.setMonthlyPrice(order.getPrice());
        subscription.setCallbackURL(order.getCallbackUrl());
        subscription.setDurationMonths(subscriptionDTO.getSubscriptionDuration());
        subscription.setSubscriber(subscriptionDTO.getEmail());
        
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
	public void checkActiveSubscriptions() throws UnsupportedEncodingException {
		List<PaypalSubscription> checkSubscriptions = subscriptionRepository.findAllByStatus("ACTIVE");
		List<Order> transactionalOrders = new ArrayList<Order>();
		for(PaypalSubscription subscription : checkSubscriptions) {
	    	String paypalAPI = "https://api.sandbox.paypal.com/v1/billing/subscriptions/"
	            	+ subscription.getSubscriptionId() + "/transactions";
	    	LocalDateTime start = subscription.getTransactionCheckTimestamp();
	    	if (start == null) {
	    		start = subscription.getCreateTimestamp();
	    	}
	    	ZonedDateTime transactionsStart = ZonedDateTime.of(start, ZoneId.systemDefault());
	        
	    	LocalDateTime end = LocalDateTime.now();
	    	ZonedDateTime transactionsEnd =  ZonedDateTime.of(end, ZoneId.systemDefault());
	    	paypalAPI = paypalAPI + "?start_time=" + transactionsStart.format(DateTimeFormatter.ISO_INSTANT) + "&end_time=" + transactionsEnd.format(DateTimeFormatter.ISO_INSTANT);
	    	RestTemplate restTemplate = new RestTemplate();
    		HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + tokenService.getAccessToken(subscription.getMerchant()));
            HttpEntity entity = new HttpEntity(headers);
        	try {
	            ResponseEntity<String> transactions = restTemplate.exchange(
	        		paypalAPI, HttpMethod.GET, entity, String.class);
	            Gson gson = new Gson();
		    	String response = transactions.getBody();
		    	JsonArray transactionResponse = gson.fromJson(response, JsonObject.class).get("transactions").getAsJsonArray();
		    	for (JsonElement oneTrans: transactionResponse) {
		    		Order order = new Order(
		    			subscription.getMerchant(),
		    			subscription.getSubscriber(),
		    			subscription.getMonthlyPrice(),
		    			subscription.getOrderScienceCenterId(),
		    			subscription.getCallbackURL()
		    		);
		    		order.setExecuted(true);
		    		order.setStatus(gson.fromJson(oneTrans, JsonObject.class).get("status").getAsString());
		    		order.setPaypalOrderId(gson.fromJson(oneTrans, JsonObject.class).get("id").getAsString());
		    		order.setRelatedSubscription(subscription.getSubscriptionId());
		    		transactionalOrders.add(order);
		    	}
            }  catch (final HttpClientErrorException e) {
            	subscription.setStatus("ERROR");
            }
            subscription.setTransactionCheckTimestamp(end);
		}
    	ordersRepository.saveAll(transactionalOrders);
    	subscriptionRepository.saveAll(checkSubscriptions);
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
				 	CompletePaymentResponseDTO completePaymentResponseDTO = new CompletePaymentResponseDTO();
	    			completePaymentResponseDTO.setOrder_id(subscription.getOrderScienceCenterId());
	    			completePaymentResponseDTO.setStatus("COMPLETED");
	    			completePaymentResponseDTO.set_subscription(true);
	    			restTemplate.exchange(subscription.getCallbackURL(), HttpMethod.POST, new HttpEntity<CompletePaymentResponseDTO>(completePaymentResponseDTO), String.class);
		    	}
		    	System.out.println(subscription.toString());
		    	
		    	subscription.setStatus(status);
            }  catch (final HttpClientErrorException e) {
            	subscription.setStatus("ERROR");
             	CompletePaymentResponseDTO completePaymentResponseDTO = new CompletePaymentResponseDTO();
    			completePaymentResponseDTO.setOrder_id(subscription.getOrderScienceCenterId());
    			completePaymentResponseDTO.setStatus("FAILED");
    			completePaymentResponseDTO.set_subscription(true);
    			restTemplate.exchange(subscription.getCallbackURL(), HttpMethod.POST, new HttpEntity<CompletePaymentResponseDTO>(completePaymentResponseDTO), String.class);
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
