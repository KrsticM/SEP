package tim18.ftn.uns.ac.rs.bitcoinpayment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tim18.ftn.uns.ac.rs.bitcoinpayment.model.Order;

public interface OrderRepository extends JpaRepository<Order, Integer>{

}
