package tim18.ftn.uns.ac.rs.paypalpayment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import tim18.ftn.uns.ac.rs.paypalpayment.model.Order;
import tim18.ftn.uns.ac.rs.paypalpayment.service.OrderService;

@Controller
@RequestMapping("/view")
public class ViewController {
	@Autowired
	OrderService orderService;

	@RequestMapping(value = "/successURL")
	public String successBtc() {	
		return "success";
	}

	@RequestMapping(value = "/paypal_payment/{orderId}")
	public String paypal(@PathVariable Integer orderId, Model model) {	
		Order order = orderService.getOrder(orderId);
		model.addAttribute("orderId", orderId);
		model.addAttribute("appId", order.getMerchant());
		model.addAttribute("paypalOrderId", order.getPaypalOrderId());
		return "order";
	}

	@RequestMapping(value = "/errorURL")
	public String errorBtc() {	
		return "error";
	}

	@RequestMapping(value = "/cancelURL")
	public String cancelBtc() {	
		return "cancel";
	}
	
	@RequestMapping(value = "/register/{appId}") 
	public String form(@PathVariable String appId, Model model) {
		System.out.println("Register form ");
		System.out.println(appId);
		model.addAttribute("appId", appId);
		return "register";
	}
	
	@RequestMapping(value = "/successfulConfig") 
	public String successfulConfig(Model model) { 
		System.out.println("successfulConfig ");
		return "successfulConfig";
	}
	
}
