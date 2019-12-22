package tim18.ftn.uns.ac.rs.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tim18.ftn.uns.ac.rs.bank.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

}
