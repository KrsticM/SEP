package tim18.ftn.uns.ac.rs.bank.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tim18.ftn.uns.ac.rs.bank.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

	Optional<Transaction> findByMerchantOrderId(Integer orderId);

}
