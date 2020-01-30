package tim18.ftn.uns.ac.rs.bitcoinpayment.controller;

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

import tim18.ftn.uns.ac.rs.bitcoinpayment.dto.CompletePaymentDTO;
import tim18.ftn.uns.ac.rs.bitcoinpayment.dto.CompletePaymentResponseDTO;
import tim18.ftn.uns.ac.rs.bitcoinpayment.dto.OrderDTO;
import tim18.ftn.uns.ac.rs.bitcoinpayment.exeptions.NotFoundException;
import tim18.ftn.uns.ac.rs.bitcoinpayment.model.Order;
import tim18.ftn.uns.ac.rs.bitcoinpayment.model.OrderStatus;
import tim18.ftn.uns.ac.rs.bitcoinpayment.service.OrderService;
import tim18.ftn.uns.ac.rs.bitcoinpayment.service.PaymentService;

@RestController
public class BitcoinPaymentController {
	
	Logger logger = LoggerFactory.getLogger(BitcoinPaymentController.class);
	
	@Autowired 
	private PaymentService paymentService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public Order createOrder(@RequestBody OrderDTO orderDTO) throws NotFoundException { // Mora se znati kom prodavcu se uplacuje, koliko se uplacuje
		logger.info("Creating order for bitcoin."); // TODO: koja aplikacija
		System.out.println(orderDTO);
		
		Order o = new Order();
		o.setOrderIdScienceCenter(orderDTO.getOrderIdScienceCenter());
		o.setPrice(orderDTO.getPrice());
		o.setCallbackUrl(orderDTO.getCallbackUrl());
		o.setStatus(OrderStatus.CREATED);
		o.setTicks(0);
		
		Order savedOrder = orderService.saveOrder(o);
		logger.info("Saved order " + savedOrder.getId()); // TODO: koja aplikacija
		return savedOrder;
	}
	
	@RequestMapping(value = "/pay/{appId}/{orderId}", method = RequestMethod.GET)
	public ModelAndView pay(@PathVariable Integer appId, @PathVariable Integer orderId) throws NotFoundException { // Mora se znati kom prodavcu se uplacuje, koliko se uplacuje
		logger.info("Bitcoin controller: appId: " + appId + ", orderId: " + orderId); 
		System.out.println("App ID: " + appId + " OrderId: " + orderId);
		String redirectUrl = paymentService.pay(appId, orderId); 
		System.out.println("REDIRECR URL: " + redirectUrl);
	
		return new ModelAndView("redirect:" + redirectUrl);
	}
	
	@RequestMapping(value = "/complete", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String complete(@RequestBody CompletePaymentDTO completePaymentDTO) throws NotFoundException {
		System.out.println("Complete payment");
		logger.info("Complete bitcoin payment for order with id " + completePaymentDTO.getOrder_id()); 

		System.out.println(completePaymentDTO);
		
		Order o = orderService.findById(completePaymentDTO.getOrder_id());
		o.setStatus(OrderStatus.COMPLETED);
		orderService.saveOrder(o);
		logger.info("Order with id " + completePaymentDTO.getOrder_id() + " is completed"); 

		CompletePaymentResponseDTO completePaymentResponseDTO = new CompletePaymentResponseDTO();
		completePaymentResponseDTO.setOrder_id(o.getOrderIdScienceCenter());
		
		if(completePaymentDTO.getStatus().contentEquals("PAID")) {
			completePaymentResponseDTO.setStatus("COMPLETED");
		}
		else {
			completePaymentResponseDTO.setStatus("FAILED");
		} // TODO: Expired..
		
		
		ResponseEntity<String> responseEntity = restTemplate.exchange(o.getCallbackUrl(), HttpMethod.POST,
				new HttpEntity<CompletePaymentResponseDTO>(completePaymentResponseDTO), String.class);
		
		
		return responseEntity.getBody();
	}
}
