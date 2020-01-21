package tim18.ftn.uns.ac.rs.paypalpayment.service;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import tim18.ftn.uns.ac.rs.paypalpayment.model.Order;

public class BillingPlanService {
	@Autowired
    AccessTokenService tokenService;
	
//	public String createProduct(Order order) {
//		RestTemplate restTemplate = new RestTemplate();
//        String paypalAPI = "https://api.sandbox.paypal.com/v1/catalogs/products";
//        String defJson = "{\n" +
//                "  \"name\": \"Item " + order.getId() + "\",\n" +
//                "  \"type\": \"" + order.getType() + "\",\n" +
//                "  \"category\": \"" order.getCategory() + "\"\n" +
//                "  }]\n" +
//                "}";
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("Authorization", "Bearer " + tokenService.getAccessToken(order.getMerchant()));
//
//        HttpEntity<String> entity = new HttpEntity<String>(defJson, headers);
//	}
	
	public String createBillingPlan(Order order, String productId) throws UnsupportedEncodingException {
		RestTemplate restTemplate = new RestTemplate();
        String paypalAPI = "https://api.sandbox.paypal.com/v1/billing/plans";
        String defJson = "{\n" +
                "  \"name\": \"Subscription for order " + order.getId() + "\",\n" +
                "  \"product_id\": \"" + order.getId() + "\",\n" +
                "  \"billing_cycles\": [{\n" +
                "    \"frequency\": {\n" +
                "      \"interval_unit\": \"MONTH\",\n" +
                "      \"interval_count\": 1\n" +
                "    },\n" +
                "	\"tenure_type\": \"REGULAR\",\n" +
                "	\"sequence\": 1,\n" +
                "	\"total_cycles\": 12\n" +
                "  }]\n" +
                "  \"payment_preferences\": {\n" +
                "    \"payment_failure_threshold\": 3,\n" +
                "    \"auto_bill_outstanding\": true\n" +
                "  },\n" +
                "}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + tokenService.getAccessToken(order.getMerchant()));

        HttpEntity<String> entity = new HttpEntity<String>(defJson, headers);
        System.out.println(tokenService.getAccessToken(order.getMerchant()));
        System.out.println(defJson);
        String jsonResponse = restTemplate.postForObject(paypalAPI, entity, String.class);
        return jsonResponse;
	}
	

	public ResponseEntity<String> getPlanDetails(int planId, int appId) throws UnsupportedEncodingException {
		String paypalAPI = "https://api.sandbox.paypal.com/v1/billing/plans/"
        	+ planId;
		RestTemplate restTemplate = new RestTemplate();
		
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + tokenService.getAccessToken(appId));
        HttpEntity entity = new HttpEntity(headers);
        return restTemplate.exchange(
        		paypalAPI, HttpMethod.GET, entity, String.class);
	}

	public ResponseEntity<String> activateBillingPlan(int planId, int appId) throws UnsupportedEncodingException {
		String paypalAPI = "https://api.sandbox.paypal.com/v1/billing/plans/"
        	+ planId + "/activate";
		RestTemplate restTemplate = new RestTemplate();
		
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + tokenService.getAccessToken(appId));
        HttpEntity entity = new HttpEntity(headers);
        return restTemplate.exchange(
        		paypalAPI, HttpMethod.POST, entity, String.class);
	}
	
	public ResponseEntity<String> deactivateBillingPlan(int planId, int appId) throws UnsupportedEncodingException {
		String paypalAPI = "https://api.sandbox.paypal.com/v1/billing/plans/"
        	+ planId + "/deactivate";
		RestTemplate restTemplate = new RestTemplate();
		
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + tokenService.getAccessToken(appId));
        HttpEntity entity = new HttpEntity(headers);
        return restTemplate.exchange(
        		paypalAPI, HttpMethod.POST, entity, String.class);
	}
}
