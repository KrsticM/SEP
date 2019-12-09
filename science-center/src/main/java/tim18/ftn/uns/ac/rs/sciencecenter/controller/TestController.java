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
		String api_key = "445ee1cb-ba9f-49b7-b150-02fb3bc78f7f";
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));        
	    headers.add("User-Agent", "Spring's RestTemplate" );  // value can be whatever
	    headers.add("Authorization", api_key );
	    
	    HttpEntity entity = new HttpEntity(headers);

	    String url = "http://localhost:8100/payment/pay/paypal-payment";
	    ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		return response;
	}
	
}
