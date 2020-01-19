package tim18.ftn.uns.ac.rs.bitcoinpayment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tim18.ftn.uns.ac.rs.bitcoinpayment.model.Order;
import tim18.ftn.uns.ac.rs.bitcoinpayment.model.OrderStatus;

public interface OrderRepository extends JpaRepository<Order, Integer>{
	List<Order> findAllByStatus(OrderStatus status);
}
