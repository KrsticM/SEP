package tim18.ftn.uns.ac.rs.paypalpayment.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaypalPaymentController {

	@GetMapping("/printMe")
	public String printMe() {
		System.out.println("PayPal payment");
		return "Hello from paypal-payment microservice.";
	}
	
	@GetMapping("/payTest")
	public String payTest() {
		System.out.println("PayPal payment test");
		return "Hello from paypal-payment microservice TEST.";
	}
	
}
