package tim18.ftn.uns.ac.rs.cardpayment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tim18.ftn.uns.ac.rs.cardpayment.model.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

}
