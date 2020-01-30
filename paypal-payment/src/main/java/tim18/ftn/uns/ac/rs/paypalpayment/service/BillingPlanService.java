package tim18.ftn.uns.ac.rs.paypalpayment.service;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import tim18.ftn.uns.ac.rs.paypalpayment.model.Order;
import tim18.ftn.uns.ac.rs.paypalpayment.model.PaypalPlan;
import tim18.ftn.uns.ac.rs.paypalpayment.model.PaypalProduct;
import tim18.ftn.uns.ac.rs.paypalpayment.repository.PaypalPlanRepository;
import tim18.ftn.uns.ac.rs.paypalpayment.repository.PaypalProductRepository;

@Service
public class BillingPlanService {
	@Autowired
    AccessTokenService tokenService;
	
	@Autowired
	PaypalProductRepository productRepository;
	
	@Autowired
	PaypalPlanRepository planRepository;
	
	public PaypalProduct createProduct(Order order) throws UnsupportedEncodingException {
		RestTemplate restTemplate = new RestTemplate();
        String paypalAPI = "https://api.sandbox.paypal.com/v1/catalogs/products";
        String defJson = "{\n" +
                "  \"name\": \"Order " + order.getId() + "\",\n" +
                "  \"type\": \"DIGITAL\",\n" +
                "  \"category\": \"BOOKS_AND_MAGAZINES\"\n" +
                "  }]\n" +
                "}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + tokenService.getAccessToken(order.getMerchant()));

        HttpEntity<String> entity = new HttpEntity<String>(defJson, headers);

        String jsonResponse = restTemplate.postForObject(paypalAPI, entity, String.class);
        Gson gson = new Gson();
        PaypalProduct product = new PaypalProduct(
        		order.getMerchant(),
        		gson.fromJson(jsonResponse, JsonObject.class).get("id").getAsString(),
        		gson.fromJson(jsonResponse, JsonObject.class).get("name").getAsString()
        );
        productRepository.save(product);
        return product;
	}
	
	public PaypalPlan createBillingPlan(PaypalProduct product, Order order, Integer duration) throws UnsupportedEncodingException {
		RestTemplate restTemplate = new RestTemplate();
        String paypalAPI = "https://api.sandbox.paypal.com/v1/billing/plans";
        String defJson = "{\n" +
                "  \"name\": \"Subscription for " + product.getName() + "\",\n" +
                "  \"product_id\": \"" + product.getProductId() + "\",\n" +
                "  \"billing_cycles\": [{\n" +
                "    \"frequency\": {\n" +
                "      \"interval_unit\": \"MONTH\",\n" +
                "      \"interval_count\": 1\n" +
                "    },\n" +
                "    \"pricing_scheme\": {\n" +
            	"	    \"fixed_price\": {\n" +
                "    	  \"value\": \"" + order.getPrice() + "\",\n" +
                "     	  \"currency_code\": \"EUR\"\n" +
                "    	}\n" +
                "    },\n" +
                "	\"tenure_type\": \"REGULAR\",\n" +
                "	\"sequence\": 1,\n" +
                "	\"total_cycles\": " + duration + "\n" +
                "  }],\n" +
                "  \"payment_preferences\": {\n" +
                "    \"payment_failure_threshold\": 3,\n" +
                "    \"auto_bill_outstanding\": true\n" +
                "  }\n" +
                "}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + tokenService.getAccessToken(product.getMerchant()));

        HttpEntity<String> entity = new HttpEntity<String>(defJson, headers);
        System.out.println(defJson);
        String jsonResponse = restTemplate.postForObject(paypalAPI, entity, String.class);
        Gson gson = new Gson();
        PaypalPlan billingPlan = new PaypalPlan(
        	product.getMerchant(),
        	gson.fromJson(jsonResponse, JsonObject.class).get("id").getAsString(),
    		product.getProductId(),
    		product.getName(),
    		gson.fromJson(jsonResponse, JsonObject.class).get("status").getAsString()
    	);
        planRepository.save(billingPlan); 		
        return billingPlan;
	}
	
	public PaypalPlan getPlan(int planId) {
		return planRepository.getOne(planId);
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
