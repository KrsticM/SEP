package tim18.ftn.uns.ac.rs.sciencecenter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim18.ftn.uns.ac.rs.sciencecenter.model.Order;
import tim18.ftn.uns.ac.rs.sciencecenter.repository.OrderRepository;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	public Order saveOrder(Order order) {
		return orderRepository.save(order);
	}
}
