package tim18.ftn.uns.ac.rs.paypalpayment.service;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.http.HttpEntity;
import java.net.URLEncoder;
import java.util.List;

import tim18.ftn.uns.ac.rs.paypalpayment.model.Order;
import tim18.ftn.uns.ac.rs.paypalpayment.model.PaypalSubscription;
import tim18.ftn.uns.ac.rs.paypalpayment.repository.OrderRepository;

@Service
public class OrderService {
	@Autowired
    AccessTokenService tokenService;
	
	@Autowired
	OrderRepository orderRepository;
	
	public Order getOrder(Integer orderId) {
		return orderRepository.getOne(orderId);
	}

	public Order createOrder(Order order) throws UnsupportedEncodingException {
        RestTemplate restTemplate = new RestTemplate();
        String paypalAPI = "https://api.sandbox.paypal.com/v2/checkout/orders";
        String defJson = "{\n" +
                "  \"intent\": \"CAPTURE\",\n" +
                "  \"application_context\": {\n" +
                "    \"return_url\": \"http://localhost:8200/view/successURL\",\n" +
                "    \"cancel_url\": \"http://localhost:8200/view/cancelURL\"\n" +
                "  },\n" +
                "  \"purchase_units\": [{\n" +
                "    \"amount\": {\n" +
                "      \"value\": \"" + order.getPrice() + "\",\n" +
                "      \"currency_code\": \"EUR\"\n" +
                "    }\n" +
                "  }]\n" +
                "}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + tokenService.getAccessToken(order.getMerchant()));

        HttpEntity<String> entity = new HttpEntity<String>(defJson, headers);
        System.out.println(tokenService.getAccessToken(order.getMerchant()));
        System.out.println(defJson);
        String jsonResponse = restTemplate.postForObject(paypalAPI, entity, String.class);
        Gson gson = new Gson();
        order.setStatus(gson.fromJson(jsonResponse, JsonObject.class).get("status").getAsString());
        order.setPaypalOrderId(gson.fromJson(jsonResponse, JsonObject.class).get("id").getAsString());
        orderRepository.save(order);
        return order;
    }
	
	public String capturePayment(int orderId) throws UnsupportedEncodingException {
		Order order = orderRepository.getOne(orderId);
		RestTemplate restTemplate = new RestTemplate();
        String paypalAPI = "https://api.sandbox.paypal.com/v2/checkout/orders/"
        	+ order.getPaypalOrderId()
        	+ "/capture";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + tokenService.getAccessToken(order.getMerchant()));
        HttpEntity<String> entity = new HttpEntity<String>("", headers); 
        String jsonResponse = restTemplate.postForObject(paypalAPI, entity, String.class);
        Gson gson = new Gson();
        String status = gson.fromJson(jsonResponse, JsonObject.class).get("status").getAsString();
        String payer = gson.fromJson(jsonResponse, JsonObject.class).get("payer").getAsJsonObject().get("email_address").getAsString();
        System.out.println(payer);
        order.setStatus(status);
        if (status.equalsIgnoreCase("COMPLETED")) {
        	order.setExecuted(true);
        }
        orderRepository.save(order);
        return jsonResponse;
	}
	
	@Scheduled(fixedDelay = 180000)
	public void checkOrders() throws UnsupportedEncodingException {
		List<Order> checkOrders = orderRepository.findAllByExecuted(false);
		 for(Order order : checkOrders) {
		        String paypalAPI = "https://api.sandbox.paypal.com/v2/checkout/orders/"
		            	+ order.getPaypalOrderId();
	    		RestTemplate restTemplate = new RestTemplate();
	    		
	            HttpHeaders headers = new HttpHeaders();
	            headers.setContentType(MediaType.APPLICATION_JSON);
	            headers.set("Authorization", "Bearer " + tokenService.getAccessToken(order.getMerchant()));
	            HttpEntity entity = new HttpEntity(headers);
	            System.out.println(tokenService.getAccessToken(order.getMerchant()));
	            System.out.println(paypalAPI);
	            try {
	            	ResponseEntity<String> orderDetails = restTemplate.exchange(
			        		paypalAPI, HttpMethod.GET, entity, String.class);
			    	Gson gson = new Gson();
			    	String response = orderDetails.getBody();
			    	String status = gson.fromJson(response, JsonObject.class).get("status").getAsString();
	
			        if (status.equalsIgnoreCase("COMPLETED")) {
			        	order.setExecuted(true);
			        }
			    	System.out.println(order.toString());
			    	
			    	order.setStatus(status);
	            } catch (final HttpClientErrorException e) {
	            	order.setStatus("ERROR");
	            	order.setExecuted(true);
	            }
		    }
	    	orderRepository.saveAll(checkOrders);
	}
	
	public ResponseEntity<String> getOrderDetails(int orderId) throws UnsupportedEncodingException {
		Order order = orderRepository.getOne(orderId);
		RestTemplate restTemplate = new RestTemplate();
		System.out.println(orderId);
		System.out.println(order.getPaypalOrderId());
        String paypalAPI = "https://api.sandbox.paypal.com/v2/checkout/orders/"
        	+ order.getPaypalOrderId();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + tokenService.getAccessToken(order.getMerchant()));
        HttpEntity entity = new HttpEntity(headers);
        return restTemplate.exchange(
        		paypalAPI, HttpMethod.GET, entity, String.class);
	}
}
