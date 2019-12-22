package tim18.ftn.uns.ac.rs.bitcoinpayment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import tim18.ftn.uns.ac.rs.bitcoinpayment.dto.CompletePaymentDTO;
import tim18.ftn.uns.ac.rs.bitcoinpayment.dto.OrderDTO;
import tim18.ftn.uns.ac.rs.bitcoinpayment.exeptions.NotFoundException;
import tim18.ftn.uns.ac.rs.bitcoinpayment.model.Order;
import tim18.ftn.uns.ac.rs.bitcoinpayment.service.OrderService;
import tim18.ftn.uns.ac.rs.bitcoinpayment.service.PaymentService;

@RestController
public class BitcoinPaymentController {
	
	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private OrderService orderService;
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public Order createOrder(@RequestBody OrderDTO orderDTO) throws NotFoundException { // Mora se znati kom prodavcu se uplacuje, koliko se uplacuje
		System.out.println("U kontroleru kreiranje ordera");
		System.out.println(orderDTO);
		
		Order o = new Order();
		o.setOrderIdScienceCenter(orderDTO.getOrderIdScienceCenter());
		o.setPrice(orderDTO.getPrice());
		o.setCallbackUrl(orderDTO.getCallbackUrl());
		
		Order savedOrder = orderService.saveOrder(o);
		return savedOrder;
	}
	
	@RequestMapping(value = "/pay/{appId}/{orderId}", method = RequestMethod.GET)
	public ModelAndView pay(@PathVariable Integer appId, @PathVariable Integer orderId) throws NotFoundException { // Mora se znati kom prodavcu se uplacuje, koliko se uplacuje
		System.out.println("U kontroleru");
		System.out.println("App ID: " + appId + " OrderId: " + orderId);
		String redirectUrl = paymentService.pay(appId, orderId); // TODO: Promeniti (prvi parametar je appId)
		System.out.println("REDIRECR URL: " + redirectUrl);
	
		return new ModelAndView("redirect:" + redirectUrl);
	}
	
	@RequestMapping(value = "/complete", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String complete(@RequestBody CompletePaymentDTO completePayment) {
		System.out.println("Complete payment");
		System.out.println(completePayment);
		return "Complete payment";
	}
}
