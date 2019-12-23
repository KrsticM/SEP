package tim18.ftn.uns.ac.rs.paypalpayment.service;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.HttpEntity;
import java.net.URLEncoder;

import tim18.ftn.uns.ac.rs.paypalpayment.model.Order;
import tim18.ftn.uns.ac.rs.paypalpayment.repository.OrderRepository;

@Service
public class OrderService {
	@Autowired
    AccessTokenService tokenService;
	
	@Autowired
	OrderRepository orderRepository;

	public Order createOrder(Order order) throws UnsupportedEncodingException {
        RestTemplate restTemplate = new RestTemplate();
        String paypalAPI = "https://api.sandbox.paypal.com/v2/checkout/orders";
        String defJson = "{\n" +
                "  \"intent\": \"CAPTURE\",\n" +
                "  \"application_context\": {\n" +
                "    \"return_url\": \"" + order.getCallbackUrl() + "\",\n" +
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
        System.out.println(defJson);
        String jsonResponse = restTemplate.postForObject(paypalAPI, entity, String.class);
        Gson gson = new Gson();
        order.setCreateTime(gson.fromJson(jsonResponse, JsonObject.class).get("create_time").getAsString());
        order.setPaypalOrderId(gson.fromJson(jsonResponse, JsonObject.class).get("id").getAsString());
        orderRepository.save(order);
        return order;
    }
	
	public Order capturePayment(int orderId) throws UnsupportedEncodingException {
		Order order = orderRepository.getOne(orderId);
		RestTemplate restTemplate = new RestTemplate();
        String paypalAPI = "https://api.sandbox.paypal.com/v2/checkout/orders/"
        	+ URLEncoder.encode(order.getPaypalOrderId(), "UTF-8")
        	+ "/capture";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + tokenService.getAccessToken(order.getMerchant()));
        HttpEntity<String> entity = new HttpEntity<String>("", headers); 
        String jsonResponse = restTemplate.postForObject(paypalAPI, entity, String.class);
        Gson gson = new Gson();
        order.setUpdateTime(gson.fromJson(jsonResponse, JsonObject.class).get("update_time").getAsString());
        String status = gson.fromJson(jsonResponse, JsonObject.class).get("status").getAsString();
        if (status.equalsIgnoreCase("COMPLETED")) {
        	order.setExecuted(true);
        }
        orderRepository.save(order);
        return order;
	}
	
	public String getOrderDetails(int orderId) throws UnsupportedEncodingException {
		Order order = orderRepository.getOne(orderId);
		RestTemplate restTemplate = new RestTemplate();
        String paypalAPI = "https://api.sandbox.paypal.com/v2/checkout/orders/"
        	+ URLEncoder.encode(order.getPaypalOrderId(), "UTF-8");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + tokenService.getAccessToken(order.getMerchant()));
        HttpEntity<String> entity = new HttpEntity<String>("", headers); 
        return restTemplate.postForObject(paypalAPI, entity, String.class);
	}
}
