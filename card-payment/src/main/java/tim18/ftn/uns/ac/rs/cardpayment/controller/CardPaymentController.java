package tim18.ftn.uns.ac.rs.cardpayment.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import tim18.ftn.uns.ac.rs.cardpayment.dto.CompletePaymentDTO;
import tim18.ftn.uns.ac.rs.cardpayment.dto.CompletePaymentResponseDTO;
import tim18.ftn.uns.ac.rs.cardpayment.dto.OrderDTO;
import tim18.ftn.uns.ac.rs.cardpayment.exceptions.NotFoundException;
import tim18.ftn.uns.ac.rs.cardpayment.model.Order;
import tim18.ftn.uns.ac.rs.cardpayment.model.OrderStatus;
import tim18.ftn.uns.ac.rs.cardpayment.service.CardPaymentService;
import tim18.ftn.uns.ac.rs.cardpayment.service.OrderService;

@RestController
public class CardPaymentController {
	
	Logger logger = LoggerFactory.getLogger(CardPaymentController.class);
	
	@Autowired
	private CardPaymentService cardPaymentService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	// TODO: prebaciti u servise
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public Order createOrder(@RequestBody OrderDTO orderDTO) throws NotFoundException { // Mora se znati kom prodavcu se uplacuje, koliko se uplacuje
		logger.info("Creating order"); // TODO: koja aplikacija
		System.out.println(orderDTO);
		
		Order o = new Order();
		o.setOrderIdScienceCenter(orderDTO.getOrderIdScienceCenter());
		o.setPrice(orderDTO.getPrice());
		o.setCallbackUrl(orderDTO.getCallbackUrl());
		o.setOrderStatus(OrderStatus.CREATED);
		
		Order savedOrder = orderService.saveOrder(o);
		logger.info("Saved order " + savedOrder.getId()); // TODO: koja aplikacija
		return savedOrder;
	}
	
	@RequestMapping(value = "/pay/{appId}/{orderId}", method = RequestMethod.GET)
	public ModelAndView payTest(@PathVariable Integer appId, @PathVariable Integer orderId) throws NotFoundException { // Mora se znati kom prodavcu se uplacuje, koliko se uplacuje
		logger.info("Card payment controller: appId: " + appId + ", orderId: " + orderId); 
		String retUrl = cardPaymentService.pay(appId, orderId);
		System.out.println("REDIRECT URL: " + retUrl);
	
		return new ModelAndView("redirect:" + retUrl);
	}
	
	// TODO: prebaciti u servise
	@RequestMapping(value = "/complete", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String complete(@RequestBody CompletePaymentDTO completePayment) throws NotFoundException {
		logger.info("Complete payment for order with id " + completePayment.getOrder_id()); 
		System.out.println("Complete payment");
		System.out.println(completePayment);
		if(completePayment.getStatus().contentEquals("PAID")) {
			Order order = orderService.findById(completePayment.getOrder_id());
			order.setOrderStatus(OrderStatus.COMPLETED);
			orderService.saveOrder(order);
			logger.info("Order with id " + completePayment.getOrder_id() + "is paid"); 
			
			CompletePaymentResponseDTO completePaymentResponseDTO = new CompletePaymentResponseDTO();
			completePaymentResponseDTO.setOrder_id(order.getOrderIdScienceCenter());
			completePaymentResponseDTO.setStatus("COMPLETED");

			ResponseEntity<String> responseEntity = restTemplate.exchange(order.getCallbackUrl(), HttpMethod.POST,
					new HttpEntity<CompletePaymentResponseDTO>(completePaymentResponseDTO), String.class);


			return responseEntity.getBody();
			
		}
		else if(completePayment.getStatus().contentEquals("FAILED")) {
			Order order = orderService.findById(completePayment.getOrder_id());
			order.setOrderStatus(OrderStatus.FAILED);
			orderService.saveOrder(order);
			logger.info("Order with id " + completePayment.getOrder_id() + "is failed"); 
			
			CompletePaymentResponseDTO completePaymentResponseDTO = new CompletePaymentResponseDTO();
			completePaymentResponseDTO.setOrder_id(order.getOrderIdScienceCenter());
			completePaymentResponseDTO.setStatus("FAILED");

			ResponseEntity<String> responseEntity = restTemplate.exchange(order.getCallbackUrl(), HttpMethod.POST,
					new HttpEntity<CompletePaymentResponseDTO>(completePaymentResponseDTO), String.class);


			return responseEntity.getBody();
			
			
		}
		logger.info("Order with id " + completePayment.getOrder_id() + "is not paid"); 

		return "Order not paid";
	}
	
}
