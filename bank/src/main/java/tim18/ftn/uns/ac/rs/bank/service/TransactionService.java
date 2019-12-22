package tim18.ftn.uns.ac.rs.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim18.ftn.uns.ac.rs.bank.model.Transaction;
import tim18.ftn.uns.ac.rs.bank.repository.TransactionRepository;

@Service
public class TransactionService {
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	public Transaction save(Transaction transaction) {
		return transactionRepository.save(transaction);
	}
	
}
