package tim18.ftn.uns.ac.rs.sciencecenter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tim18.ftn.uns.ac.rs.sciencecenter.model.Order;

public interface OrderRepository extends JpaRepository<Order, Integer>{

}
