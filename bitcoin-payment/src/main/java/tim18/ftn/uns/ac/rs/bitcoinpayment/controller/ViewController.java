package tim18.ftn.uns.ac.rs.bitcoinpayment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {

	@RequestMapping(value = "/successBtc")
	public String successBtc() {	
		return "success";
	}
	
	@RequestMapping(value = "/cancelBtc")
	public String cancelBtc() {	
		return "cancel";
	}
	
}
