package tim18.ftn.uns.ac.rs.paypalpayment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
public class ViewController {

	@RequestMapping(value = "/successURL")
	public String successBtc() {	
		return "success";
	}
	
	@RequestMapping(value = "/cancelURL")
	public String cancelBtc() {	
		return "cancel";
	}
	
	@RequestMapping(value = "/register/{appId}") 
	public String form(@PathVariable Integer appId, Model model) {
		System.out.println("Register form ");
		model.addAttribute("appId", appId);
		return "register";
	}
	
	@RequestMapping(value = "/successfulConfig") 
	public String successfulConfig(Model model) { 
		System.out.println("successfulConfig ");
		return "successfulConfig";
	}
	
}
