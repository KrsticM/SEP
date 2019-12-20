package tim18.ftn.uns.ac.rs.bank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
public class ViewController {
	
	@RequestMapping(value = "/form") 
	public String form(Model model) { // Ovde ce se verovato primati app token umesto app id
		System.out.println("Form");
		
		return "form";
	}

}
