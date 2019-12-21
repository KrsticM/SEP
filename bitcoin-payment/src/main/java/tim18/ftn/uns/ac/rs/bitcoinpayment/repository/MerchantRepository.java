package tim18.ftn.uns.ac.rs.bitcoinpayment.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tim18.ftn.uns.ac.rs.bitcoinpayment.model.Merchant;

public interface MerchantRepository extends JpaRepository<Merchant, Integer> {

	Optional<Merchant> findByApplicationId(Integer applicationId);

}
