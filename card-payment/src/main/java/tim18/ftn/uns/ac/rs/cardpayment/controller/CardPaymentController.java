package tim18.ftn.uns.ac.rs.cardpayment.controller;

import java.util.Map;

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
		Map<String, Object> retVal = cardPaymentService.pay(new Integer(1), new Double(50)); // TODO: Promeniti
		System.out.println(retVal);
		System.out.println("REDIRECT URL: " + retVal.get("redirect_url"));
	
		return new ModelAndView("redirect:" + retVal.get("redirect_url"));
	}
	
}
