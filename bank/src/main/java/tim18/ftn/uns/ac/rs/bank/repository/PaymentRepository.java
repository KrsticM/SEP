package tim18.ftn.uns.ac.rs.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tim18.ftn.uns.ac.rs.bank.model.PaymentRequest;

public interface PaymentRepository extends JpaRepository<PaymentRequest, Integer> {

}
