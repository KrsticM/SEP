package tim18.ftn.uns.ac.rs.bitcoinpayment.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import tim18.ftn.uns.ac.rs.bitcoinpayment.dto.CompletePaymentDTO;
import tim18.ftn.uns.ac.rs.bitcoinpayment.service.PaymentService;

@RestController
public class BitcoinPaymentController {
	
	@Autowired
	private PaymentService paymentService;
	
	@RequestMapping(value = "/pay", method = RequestMethod.GET)
	public ModelAndView payTest() { // Mora se znati kom prodavcu se uplacuje, koliko se uplacuje
		System.out.println("U kontroleru");
		Map<String, Object> retVal = paymentService.pay(new Integer(50), new Double(0.3)); // TODO: Promeniti
		System.out.println(retVal);
		System.out.println("REDIRECR URL: " + retVal.get("redirect_url"));
	
		return new ModelAndView("redirect:" + retVal.get("redirect_url"));
	}
	
	
	
	
	@RequestMapping(value = "/complete", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String complete(@RequestBody CompletePaymentDTO completePayment) {
		System.out.println("Complete payment");
		System.out.println(completePayment);
		return "Complete payment";
	}
}
