package tim18.ftn.uns.ac.rs.paypalpayment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tim18.ftn.uns.ac.rs.paypalpayment.model.PaypalProduct;

public interface PaypalProductRepository extends JpaRepository<PaypalProduct, Integer>{
}
