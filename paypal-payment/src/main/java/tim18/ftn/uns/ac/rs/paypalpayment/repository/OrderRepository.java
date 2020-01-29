package tim18.ftn.uns.ac.rs.paypalpayment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tim18.ftn.uns.ac.rs.paypalpayment.model.Order;

public interface OrderRepository extends JpaRepository<Order, Integer>{
	List<Order> findAllByExecuted(Boolean executed);
}
