package tim18.ftn.uns.ac.rs.bitcoinpayment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
public class ViewController {

	@RequestMapping(value = "/successBtc")
	public String successBtc() {	
		return "success";
	}
	
	@RequestMapping(value = "/cancelBtc")
	public String cancelBtc() {	
		return "cancel";
	}
	
	@RequestMapping(value = "/register/{appId}") 
	public String form(@PathVariable Integer appId, Model model) { // Ovde ce se verovato primati app token umesto app id
		System.out.println("Register form ");
		model.addAttribute("appId", appId);
		return "register";
	}
	
}
