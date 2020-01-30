package tim18.ftn.uns.ac.rs.bank.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim18.ftn.uns.ac.rs.bank.exceptions.NotFoundException;
import tim18.ftn.uns.ac.rs.bank.model.PaymentRequest;
import tim18.ftn.uns.ac.rs.bank.model.Transaction;
import tim18.ftn.uns.ac.rs.bank.repository.TransactionRepository;

@Service
public class TransactionService {
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	public Transaction save(Transaction transaction) {
		return transactionRepository.save(transaction);
	}
	
	public Transaction findOne(Integer orderId) throws NotFoundException {
		Optional<Transaction> opt = transactionRepository.findByMerchantOrderId(orderId);
		
		if(!opt.isPresent()) {
			throw new NotFoundException(orderId, Transaction.class.getSimpleName());
		}
		
		return opt.get();
		
	}
	
}
