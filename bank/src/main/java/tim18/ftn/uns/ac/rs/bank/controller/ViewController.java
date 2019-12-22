package tim18.ftn.uns.ac.rs.bank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
public class ViewController {
	
	@RequestMapping(value = "/form/{paymentRequestId}") 
	public String form(@PathVariable Integer paymentRequestId, Model model) { // Ovde ce se verovato primati app token umesto app id
		System.out.println("Form " + paymentRequestId);
		model.addAttribute("paymentRequestId", paymentRequestId);
		return "form";
	}

}
