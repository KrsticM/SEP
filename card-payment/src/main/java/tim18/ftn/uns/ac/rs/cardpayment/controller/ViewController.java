package tim18.ftn.uns.ac.rs.cardpayment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
public class ViewController {
	
	@RequestMapping(value = "/register") 
	public String form(Model model) { // Ovde ce se verovato primati app token umesto app id
		System.out.println("Form ");
		return "register";
	}
	
	
	@RequestMapping(value = "/successURL") 
	public String successURL(Model model) { 
		System.out.println("successURL ");
		return "successful";
	}
	
	@RequestMapping(value = "/failedURL") 
	public String failedURL(Model model) { 
		System.out.println("failedURL ");
		return "failed";
	}
	
	@RequestMapping(value = "/errorURL") 
	public String errorURL(Model model) { 
		System.out.println("errorURL ");
		return "error";
	}

}
