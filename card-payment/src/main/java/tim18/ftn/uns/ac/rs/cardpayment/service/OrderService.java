package tim18.ftn.uns.ac.rs.cardpayment.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim18.ftn.uns.ac.rs.cardpayment.exceptions.NotFoundException;
import tim18.ftn.uns.ac.rs.cardpayment.model.Order;
import tim18.ftn.uns.ac.rs.cardpayment.repository.OrderRepository;

@Service
public class OrderService {
	
	Logger logger = LoggerFactory.getLogger(OrderService.class);

	@Autowired
	private OrderRepository orderRepository;
	
	public Order saveOrder(Order order) {
		return orderRepository.save(order);
	}
	
	public Order findById(Integer id) throws NotFoundException {
		Optional<Order> order = orderRepository.findById(id);
		
		if(!order.isPresent()) {
			logger.error("Order with id " + id + " not found.");
			throw new NotFoundException(id, Order.class.getSimpleName());
		}
		
		return order.get();
	}
}
