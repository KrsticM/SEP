package tim18.ftn.uns.ac.rs.paymentconcentrator.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import tim18.ftn.uns.ac.rs.paymentconcentrator.exceptions.NotFoundException;
import tim18.ftn.uns.ac.rs.paymentconcentrator.model.PaymentMethod;
import tim18.ftn.uns.ac.rs.paymentconcentrator.service.PaymentMethodService;

@Controller
public class ViewController {

	@Autowired
	private PaymentMethodService paymentMethodService;
	
	@RequestMapping(value = "/choosePaymentMethod/{appApiKey}") // Razmisliti da li ce ovde slati i podatke o narudzbini
	public String index(@PathVariable UUID appApiKey, Model model) throws NotFoundException { // Ovde ce se verovato primati app token umesto app id
		System.out.println(appApiKey);
		List<PaymentMethod> paymentMethods = paymentMethodService.getPaymentMethods(appApiKey);
		System.out.println(paymentMethods);
		
		String appApiKeyString = appApiKey.toString();
		model.addAttribute("paymentMethods", paymentMethods);
		model.addAttribute("appId", appApiKeyString);
		return "choosePaymentMethod";
	}
}
