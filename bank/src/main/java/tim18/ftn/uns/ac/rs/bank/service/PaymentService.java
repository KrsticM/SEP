package tim18.ftn.uns.ac.rs.bank.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim18.ftn.uns.ac.rs.bank.dto.ClientDTO;
import tim18.ftn.uns.ac.rs.bank.dto.PaymentRequestDTO;
import tim18.ftn.uns.ac.rs.bank.dto.PaymentResponseDTO;
import tim18.ftn.uns.ac.rs.bank.exceptions.NotFoundException;
import tim18.ftn.uns.ac.rs.bank.model.Client;
import tim18.ftn.uns.ac.rs.bank.model.PaymentRequest;
import tim18.ftn.uns.ac.rs.bank.model.Transaction;
import tim18.ftn.uns.ac.rs.bank.model.TransactionStatus;
import tim18.ftn.uns.ac.rs.bank.repository.PaymentRepository;

@Service
public class PaymentService {
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private TransactionService transactionService;
	
	public PaymentRequest getPaymentRequest(Integer id) throws NotFoundException {
		Optional<PaymentRequest> paymentRequest = paymentRepository.findById(id);
		if(!paymentRequest.isPresent()) {
			throw new NotFoundException(id, PaymentRequest.class.getSimpleName());

		}
		return paymentRequest.get();
	}
	
	public PaymentResponseDTO createPayment(PaymentRequestDTO paymentRequestDTO) {
		PaymentRequest paymentRequest = new PaymentRequest();
		paymentRequest.setAmount(paymentRequestDTO.getAmount());
		paymentRequest.setMerchantId(paymentRequestDTO.getMerchantId());
		paymentRequest.setMerchantPassword(paymentRequestDTO.getMerchantPassword());
		paymentRequest.setMerchantOrderId(paymentRequestDTO.getMerchantOrderId());
		paymentRequest.setMerchantTimestamp(paymentRequestDTO.getMerchantTimestamp());
		paymentRequest.setSuccessUrl(paymentRequestDTO.getSuccessUrl());
		paymentRequest.setFailedUrl(paymentRequestDTO.getFailedUrl());
		paymentRequest.setErrorUrl(paymentRequestDTO.getErrorUrl());
		PaymentRequest ret = paymentRepository.save(paymentRequest);
		return new PaymentResponseDTO(ret.getId(), "http://localhost:5005/view/form");
	}

	public String confirmPayment(ClientDTO clientDTO, Integer paymentRequestId) throws NotFoundException {
		Transaction transaction = new Transaction();
		PaymentRequest paymentRequest = getPaymentRequest(paymentRequestId);
		
		transaction.setAmount(paymentRequest.getAmount());
		transaction.setMerchantId(paymentRequest.getMerchantId());
		transaction.setMerchantOrderId(paymentRequest.getMerchantOrderId());
		transaction.setMerchantTimestamp(paymentRequest.getMerchantTimestamp());
		Optional<Client> clientOpt = clientService.getClient(clientDTO.getPanNumber());
		if(!clientOpt.isPresent()) {
			transaction.setStatus(TransactionStatus.FAILED);
			return paymentRequest.getFailedUrl();
		}
		
		Client client = clientOpt.get();
		transaction.setPanNumber(client.getPanNumber());
		String tempDate = clientDTO.getMm() + "/" + clientDTO.getYy();
		if (!client.getCardHolder().equals(clientDTO.getCardHolder()) || !client.getCvv().equals(clientDTO.getCvv())
				|| !client.getExpirationDate().equals(tempDate)) {
			System.err.println("ne podudara se");
			transaction.setStatus(TransactionStatus.FAILED);
			return paymentRequest.getFailedUrl();
		}

		if (paymentRequest.getAmount() > client.getAvailableFunds()) {
			System.err.println("nema sredstava");
			transaction.setStatus(TransactionStatus.FAILED);
			return paymentRequest.getFailedUrl();
		}

		String merchantId = paymentRequest.getMerchantId();
		Client merchant = clientService.getClientByMerchantId(merchantId);
		if (!merchant.getMerchantPassword().equals(paymentRequest.getMerchantPassword())){
			System.err.println("nije dobar merchant");
			transaction.setStatus(TransactionStatus.ERROR);
			return paymentRequest.getErrorUrl();
		}

		client.setAvailableFunds(client.getAvailableFunds() - paymentRequest.getAmount());
		clientService.save(client);
		merchant.setAvailableFunds(merchant.getAvailableFunds() + paymentRequest.getAmount());
		clientService.save(merchant);
		transaction.setStatus(TransactionStatus.SUCCESSFUL);
		transactionService.save(transaction);		
		return paymentRequest.getSuccessUrl();
	}
}
