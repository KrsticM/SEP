package tim18.ftn.uns.ac.rs.paypalpayment.controller;

import java.io.UnsupportedEncodingException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import tim18.ftn.uns.ac.rs.paypalpayment.dto.CompletePaymentDTO;
import tim18.ftn.uns.ac.rs.paypalpayment.dto.OrderDTO;
import tim18.ftn.uns.ac.rs.paypalpayment.model.Order;
import tim18.ftn.uns.ac.rs.paypalpayment.service.OrderService;

@RestController
public class PaypalPaymentController {
	
	Logger logger = LoggerFactory.getLogger(PaypalPaymentController.class);

	@Autowired
	OrderService orderService;
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public Order createOrder(@RequestBody @Valid OrderDTO orderDTO) throws UnsupportedEncodingException{
		Order order = orderDTO.createOrder();
		
		Order savedOrder = orderService.createOrder(order);
		System.out.println("Created order: \n" + savedOrder.toString());
		logger.info("Created order: " + savedOrder.toString());
		return savedOrder;
	}

	@RequestMapping(value = "/getOrder/{orderId}", method = RequestMethod.GET)
	public ResponseEntity<String> createOrderForPaypal(@PathVariable Integer orderId) throws UnsupportedEncodingException{
		ResponseEntity<String> order = orderService.getOrderDetails(orderId);
		System.out.println("Created order: \n" + order);
		logger.info("Created order: " + order);
		return order;
	}
	

	@RequestMapping(value = "/capturePayment/{appId}/{orderId}", method = RequestMethod.GET)
	public ResponseEntity<String> capturePayment(@PathVariable Integer appId, @PathVariable Integer orderId) throws UnsupportedEncodingException{
		String savedOrder = orderService.capturePayment(orderId);
		System.out.println("Paid for order: \n" + savedOrder);
		logger.info("Paid for order: " + savedOrder);
		return ResponseEntity.ok(savedOrder);
	}

	@RequestMapping(value = "/pay/{appId}/{orderId}", method = RequestMethod.GET)
	public ModelAndView pay(@PathVariable Integer appId, @PathVariable Integer orderId) throws UnsupportedEncodingException{
		System.out.println("App ID: " + appId + " OrderId: " + orderId);
		logger.info("Paypal controller pay, app id " + appId + ", order id " + orderId);
		String redirectUrl = "http://localhost:8200/view/paypal_payment/" + orderId;
		System.out.println("REDIRECR URL: " + redirectUrl);
	
		return new ModelAndView("redirect:" + redirectUrl);
	}
	
	@RequestMapping(value = "/complete", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> complete(@RequestBody CompletePaymentDTO completePayment) {
		// TODO: implement
		System.out.println("Complete payment");
		logger.info("Complete paypal payment");
		return ResponseEntity.ok("OK");
	}
}
