package tim18.ftn.uns.ac.rs.sciencecenter.controller;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class TestController {

	@Autowired
	private RestTemplate restTemplate;
	
	@GetMapping("/test")
	public ResponseEntity<?> test() {
		String authToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJKZWxlbmFAZ21haWwuY29tIiwiYXVkaWVuY2UiOiJ1bmtub3duIiwiY3JlYXRlZCI6MTU3NDg4OTQ3NDI2Miwicm9sZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfUEVSU09OQUwifV0sImV4cCI6MTU3NDk3NTg3NH0.ywuv6KWD08pROuoNpQ9dqsZtGfjx7SX7H_-xBz-eq0ltKadctgTvek7u8QJdFhJ44bhnMxqAy6qYFJNZSjHBOw";
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));        
	    headers.add("User-Agent", "Spring's RestTemplate" );  // value can be whatever
	    headers.add("Authorization", "Bearer "+authToken );
	    
	    HttpEntity entity = new HttpEntity(headers);

	    String url = "http://localhost:8100/payment/pay/6167cb01-a67c-4ec7-8acc-72c7449f4d35/paypal-payment";
	    ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		return response;
	}
	
}
