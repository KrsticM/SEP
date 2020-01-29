package tim18.ftn.uns.ac.rs.paymentconcentrator.service;

import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim18.ftn.uns.ac.rs.paymentconcentrator.exceptions.NotFoundException;
import tim18.ftn.uns.ac.rs.paymentconcentrator.model.Order;
import tim18.ftn.uns.ac.rs.paymentconcentrator.repository.OrderRepository;

@Service
public class OrderService {

	Logger logger = LoggerFactory.getLogger(OrderService.class);

	@Autowired
	private OrderRepository orderRepository;
	
	public Order saveOrder(Order order) {
		return orderRepository.save(order);
	}
	
	public Order findById(UUID id) throws NotFoundException {
		Optional<Order> order = orderRepository.findById(id);
		
		if(!order.isPresent()) {
			logger.error("Order with id " + id + " not found." );
			throw new NotFoundException(id.toString(), Order.class.getSimpleName());
		}
		
		return order.get();
	}
}
