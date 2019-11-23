package tim18.ftn.uns.ac.rs.paymentconcentrator.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/payment")
public class PaymentController {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private DiscoveryClient discoveryClient;

	@GetMapping("/{method}")
	public String test(@PathVariable("method") String method) {

		String result = restTemplate.getForObject("http://" + method + "/printMe", String.class);

		return result;
	}

	@GetMapping("/all-services")
	public List<String> getAll() {
		List<String> allServices = discoveryClient.getServices();
		allServices.remove("payment-concentrator");
		return allServices;
	}
	
	
}
