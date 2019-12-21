package tim18.ftn.uns.ac.rs.bitcoinpayment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import tim18.ftn.uns.ac.rs.bitcoinpayment.dto.CompletePaymentDTO;
import tim18.ftn.uns.ac.rs.bitcoinpayment.exeptions.NotFoundException;
import tim18.ftn.uns.ac.rs.bitcoinpayment.service.PaymentService;

@RestController
public class BitcoinPaymentController {
	
	@Autowired
	private PaymentService paymentService;
	
	@RequestMapping(value = "/pay/{appId}", method = RequestMethod.GET)
	public ModelAndView pay(@PathVariable Integer appId) throws NotFoundException { // Mora se znati kom prodavcu se uplacuje, koliko se uplacuje
		System.out.println("U kontroleru");
		System.out.println("App ID: " + appId);
		String redirectUrl = paymentService.pay(appId, new Double(0.3)); // TODO: Promeniti (prvi parametar je appId)
		System.out.println("REDIRECR URL: " + redirectUrl);
	
		return new ModelAndView("redirect:" + redirectUrl);
	}
	
	@RequestMapping(value = "/complete", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String complete(@RequestBody CompletePaymentDTO completePayment) {
		System.out.println("Complete payment");
		System.out.println(completePayment);
		return "Complete payment";
	}
}
