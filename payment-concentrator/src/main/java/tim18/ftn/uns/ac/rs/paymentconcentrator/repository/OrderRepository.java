package tim18.ftn.uns.ac.rs.paymentconcentrator.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tim18.ftn.uns.ac.rs.paymentconcentrator.model.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

}
