package tim18.ftn.uns.ac.rs.paypalpayment.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/paypal")
public class PaymentController {

	@GetMapping("/printMe")
	public void printMe() {
		System.out.println("PayPal payment");
	}
	
}
