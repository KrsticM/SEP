package tim18.ftn.uns.ac.rs.paypalpayment.controller;

import java.io.UnsupportedEncodingException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tim18.ftn.uns.ac.rs.paypalpayment.dto.CompletePaymentDTO;
import tim18.ftn.uns.ac.rs.paypalpayment.dto.OrderDTO;
import tim18.ftn.uns.ac.rs.paypalpayment.model.Order;
import tim18.ftn.uns.ac.rs.paypalpayment.service.OrderService;

@RestController
public class PaypalPaymentController {
	@Autowired
	OrderService orderService;
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<Order> createOrder(@RequestBody @Valid OrderDTO orderDTO) throws UnsupportedEncodingException{
		// TODO: implement
		Order order = orderDTO.createOrder();
		Order savedOrder = orderService.createOrder(order);
		System.out.println("Created order: \n" + savedOrder.toString());
		return ResponseEntity.ok(savedOrder);
	}

	@RequestMapping(value = "/pay/{appId}/{orderId}", method = RequestMethod.GET)
	public ResponseEntity<Order> pay(@PathVariable Integer appId, @PathVariable Integer orderId) throws UnsupportedEncodingException{
		Order savedOrder = orderService.capturePayment(orderId);
		System.out.println("Paid for order: \n" + savedOrder.toString());
		return ResponseEntity.ok(savedOrder);
	}
	
	@RequestMapping(value = "/complete", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> complete(@RequestBody CompletePaymentDTO completePayment) {
		// TODO: implement
		System.out.println("Complete payment");
		return ResponseEntity.ok("OK");
	}
}
