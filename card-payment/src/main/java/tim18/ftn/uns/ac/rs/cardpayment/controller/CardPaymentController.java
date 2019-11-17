package tim18.ftn.uns.ac.rs.cardpayment.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CardPaymentController {
	
	@GetMapping("/printMe")
	public String printMe() {
		System.out.println("card payment");
		return "Hello from card-payment microservice.";
	}
}
