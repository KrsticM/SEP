package tim18.ftn.uns.ac.rs.paymentconcentrator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tim18.ftn.uns.ac.rs.paymentconcentrator.dto.OrderInformationDTO;
import tim18.ftn.uns.ac.rs.paymentconcentrator.model.Order;
import tim18.ftn.uns.ac.rs.paymentconcentrator.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	private Order createOrder(@RequestBody OrderInformationDTO orderInformationDTO) {
		System.out.println("[CONTROLLER]: /order/create");
		System.err.println(orderInformationDTO);
		
		Order o = new Order();
		o.setOrderIdScienceCenter(orderInformationDTO.getId());
		o.setPrice(orderInformationDTO.getPrice());
		o.setCallbackUrl(orderInformationDTO.getCallbackUrl());
		
		Order savedOrder = orderService.saveOrder(o);
		return savedOrder;
	}
	
}
