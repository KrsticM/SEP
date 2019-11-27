package tim18.ftn.uns.ac.rs.bitcoinpayment.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BitcoinPaymentController {
	@GetMapping("/printMe")
	public String printMe() {
		System.out.println("bitcoin payment");
		return "Hello from bitcoin-payment microservice.";
	}
	
	@GetMapping("/payTest")
	public String payTest() {
		System.out.println("Bitcoin payment test");
		return "Hello from bitcoin-payment microservice TEST.";
	}
}
