package tim18.ftn.uns.ac.rs.paypalpayment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tim18.ftn.uns.ac.rs.paypalpayment.model.Merchant;

public interface MerchantRepository extends JpaRepository<Merchant, Integer> {
}