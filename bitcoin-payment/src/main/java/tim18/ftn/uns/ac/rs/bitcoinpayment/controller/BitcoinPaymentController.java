package tim18.ftn.uns.ac.rs.bitcoinpayment.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import tim18.ftn.uns.ac.rs.bitcoinpayment.service.PaymentService;

@RestController
public class BitcoinPaymentController {
	
	@Autowired
	private PaymentService paymentService;
	
	@GetMapping("/printMe")
	public String printMe() {
		System.out.println("bitcoin payment");
		return "Hello from bitcoin-payment microservice.";
	}
	
	@GetMapping("/pay")
	public String payTest() {
		System.out.println("U kontroleru");
		Map<String, Object> retVal = paymentService.pay(new Integer(50), new Double(0.3)); // TODO: Promeniti
		System.out.println(retVal);
	
		return "Hello from bitcoin-payment microservice ST.";
	}
}
