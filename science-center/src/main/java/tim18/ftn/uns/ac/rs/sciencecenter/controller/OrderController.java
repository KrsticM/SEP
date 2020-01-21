package tim18.ftn.uns.ac.rs.sciencecenter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tim18.ftn.uns.ac.rs.sciencecenter.dto.CompletePaymentDTO;
import tim18.ftn.uns.ac.rs.sciencecenter.exceptions.NotFoundException;
import tim18.ftn.uns.ac.rs.sciencecenter.model.Order;
import tim18.ftn.uns.ac.rs.sciencecenter.model.OrderStatus;
import tim18.ftn.uns.ac.rs.sciencecenter.service.OrderService;



@RestController
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private OrderService orderService;

	@RequestMapping(value = "/complete", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String complete(@RequestBody CompletePaymentDTO completePaymentDTO) throws NotFoundException {
		System.out.println("Complete payment");
		System.out.println(completePaymentDTO);
		if(completePaymentDTO.getStatus().contentEquals("COMPLETED")) {
			Order o = orderService.findById(completePaymentDTO.getOrder_id());
			o.setStatus(OrderStatus.COMPLETED);	
			orderService.saveOrder(o);
			return "Success";
		}
		else if(completePaymentDTO.getStatus().contentEquals("FAILED")) {
			Order o = orderService.findById(completePaymentDTO.getOrder_id());
			o.setStatus(OrderStatus.FAILED);	
			orderService.saveOrder(o);
			return "Failed";
		}
		
		return "Error";
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public List<Order> completedOrders() {
		return orderService.findAllCompleted();
	}
	
}
