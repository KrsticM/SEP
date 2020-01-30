package tim18.ftn.uns.ac.rs.paymentconcentrator.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tim18.ftn.uns.ac.rs.paymentconcentrator.dto.OrderInformationDTO;
import tim18.ftn.uns.ac.rs.paymentconcentrator.model.Order;
import tim18.ftn.uns.ac.rs.paymentconcentrator.model.OrderStatus;
import tim18.ftn.uns.ac.rs.paymentconcentrator.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

	Logger logger = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	private OrderService orderService;
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	private Order createOrder(@RequestBody OrderInformationDTO orderInformationDTO) {
		System.out.println("[CONTROLLER]: /order/create");
		logger.info("Creating order ");
		System.err.println(orderInformationDTO);
		
		Order o = new Order();
		o.setOrderIdScienceCenter(orderInformationDTO.getId());
		o.setPrice(orderInformationDTO.getPrice());
		o.setCallbackUrl(orderInformationDTO.getCallbackUrl());
		o.setTicks(0);
		o.setStatus(OrderStatus.CREATED);
		
		Order savedOrder = orderService.saveOrder(o);
		logger.info("Saving order with id " + savedOrder.getId());
		return savedOrder;
	}
	
}
