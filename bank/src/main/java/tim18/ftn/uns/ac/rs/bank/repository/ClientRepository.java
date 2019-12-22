package tim18.ftn.uns.ac.rs.bank.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tim18.ftn.uns.ac.rs.bank.model.Client;

public interface ClientRepository  extends JpaRepository<Client, Integer>{
	Optional<Client> findByPanNumber(String panNumber);
	Optional<Client> findByMerchantId(String merchantId);
}
