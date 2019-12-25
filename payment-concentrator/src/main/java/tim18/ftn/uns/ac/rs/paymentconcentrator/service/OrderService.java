package tim18.ftn.uns.ac.rs.paymentconcentrator.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim18.ftn.uns.ac.rs.paymentconcentrator.exceptions.NotFoundException;
import tim18.ftn.uns.ac.rs.paymentconcentrator.model.Order;
import tim18.ftn.uns.ac.rs.paymentconcentrator.repository.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	public Order saveOrder(Order order) {
		return orderRepository.save(order);
	}
	
	public Order findById(Integer id) throws NotFoundException {
		Optional<Order> order = orderRepository.findById(id);
		
		if(!order.isPresent()) {
			throw new NotFoundException(id, Order.class.getSimpleName());
		}
		
		return order.get();
	}
}