package tim18.ftn.uns.ac.rs.bitcoinpayment.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim18.ftn.uns.ac.rs.bitcoinpayment.exeptions.NotFoundException;
import tim18.ftn.uns.ac.rs.bitcoinpayment.model.Order;
import tim18.ftn.uns.ac.rs.bitcoinpayment.model.OrderStatus;
import tim18.ftn.uns.ac.rs.bitcoinpayment.repository.OrderRepository;


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
	
	public List<Order> findAllByStatus(OrderStatus status) {
		return orderRepository.findAllByStatus(status);
	}
	
}
