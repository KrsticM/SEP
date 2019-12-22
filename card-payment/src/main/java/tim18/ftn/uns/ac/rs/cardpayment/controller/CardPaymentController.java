package tim18.ftn.uns.ac.rs.cardpayment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import tim18.ftn.uns.ac.rs.cardpayment.exceptions.NotFoundException;
import tim18.ftn.uns.ac.rs.cardpayment.service.CardPaymentService;

@RestController
public class CardPaymentController {
	
	@Autowired
	private CardPaymentService cardPaymentService;
	
	@RequestMapping(value = "/pay", method = RequestMethod.GET)
	public ModelAndView payTest() throws NotFoundException { // Mora se znati kom prodavcu se uplacuje, koliko se uplacuje
		System.out.println("U kontroleru");
		String retUrl = cardPaymentService.pay(new Integer(1), new Double(50)); // TODO: Promeniti
		System.out.println("REDIRECT URL: " + retUrl);
	
		return new ModelAndView("redirect:" + retUrl);
	}
	
}
