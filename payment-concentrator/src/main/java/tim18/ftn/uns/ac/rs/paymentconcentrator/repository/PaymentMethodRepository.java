package tim18.ftn.uns.ac.rs.paymentconcentrator.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tim18.ftn.uns.ac.rs.paymentconcentrator.model.PaymentMethod;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Integer> {
	Optional<PaymentMethod> findByName(String name);
}
