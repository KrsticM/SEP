package tim18.ftn.uns.ac.rs.paymentconcentrator.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import tim18.ftn.uns.ac.rs.paymentconcentrator.dto.PaymentInformationDTO;
import tim18.ftn.uns.ac.rs.paymentconcentrator.exceptions.NotFoundException;
import tim18.ftn.uns.ac.rs.paymentconcentrator.model.Application;
import tim18.ftn.uns.ac.rs.paymentconcentrator.model.PaymentMethod;
import tim18.ftn.uns.ac.rs.paymentconcentrator.service.ApplicationService;
import tim18.ftn.uns.ac.rs.paymentconcentrator.service.PaymentMethodService;

@Controller
public class ViewController {

	@Autowired
	private PaymentMethodService paymentMethodService;
	
	@Autowired
	private ApplicationService applicationService;
	
	// Primer poziva: http://localhost:8762/payment-concentrator/choosePaymentMethod/1d5d705c-c332-477c-8c1a-33e277b251ef
	// Body:
	// OrderId : Integer
	// OrderPrice : Double
	// CallBackUrl : String
	
	@RequestMapping(value = "/choosePaymentMethod/{appApiKey}", method = RequestMethod.GET) 
	public String index(@PathVariable UUID appApiKey, Model model) throws NotFoundException { // @RequestBody PaymentInformationDTO paymentInformationDTO
		System.out.println(appApiKey);
		List<PaymentMethod> paymentMethods = paymentMethodService.getPaymentMethods(appApiKey);
		System.out.println(paymentMethods);
		
		//System.err.println(paymentInformationDTO);
		String appApiKeyString = appApiKey.toString();
		Application app = applicationService.findByApiKey(appApiKeyString);
		
		model.addAttribute("paymentMethods", paymentMethods);
		model.addAttribute("appId", app.getId());
		return "choosePaymentMethod";
	}
}
