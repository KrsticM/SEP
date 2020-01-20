package tim18.ftn.uns.ac.rs.bank.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import tim18.ftn.uns.ac.rs.bank.dto.AcquirerResponseDTO;
import tim18.ftn.uns.ac.rs.bank.dto.ClientDTO;
import tim18.ftn.uns.ac.rs.bank.dto.CompletePaymentResponseDTO;
import tim18.ftn.uns.ac.rs.bank.dto.PaymentRequestDTO;
import tim18.ftn.uns.ac.rs.bank.dto.PaymentResponseDTO;
import tim18.ftn.uns.ac.rs.bank.dto.PccRequestDTO;
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
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${bankId}")
    private String bankId;
	
	private String pccUrl = "http://localhost:6006/pcc";  // Payment Card Center
	
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
		paymentRequest.setCallbackUrl(paymentRequestDTO.getCallbackUrl());
		PaymentRequest ret = paymentRepository.save(paymentRequest);
		return new PaymentResponseDTO(ret.getId(), "http://localhost:5005/view/form");
	}

	public String confirmPayment(ClientDTO clientDTO, Integer paymentRequestId) throws NotFoundException {
		// Proveravamo da li je to klijent ove banke
		System.err.println("bankId " + bankId);
		System.err.println("skraceni pan: " + getBankIdFromPan(clientDTO.getPanNumber()));
		
		Transaction transaction = new Transaction();
		PaymentRequest paymentRequest = getPaymentRequest(paymentRequestId);
		
		transaction.setAmount(paymentRequest.getAmount());
		transaction.setMerchantId(paymentRequest.getMerchantId());
		transaction.setMerchantOrderId(paymentRequest.getMerchantOrderId());
		transaction.setMerchantTimestamp(paymentRequest.getMerchantTimestamp());
		
		if(getBankIdFromPan(clientDTO.getPanNumber()).contentEquals(bankId)) {
			
			// To je ova banka
			System.out.println("Iz iste su banke");
			
			Optional<Client> clientOpt = clientService.getClient(clientDTO.getPanNumber());
			if(!clientOpt.isPresent()) {
				transaction.setStatus(TransactionStatus.FAILED);
				failPayment(paymentRequest);
				return paymentRequest.getFailedUrl();
			}
			
			Client client = clientOpt.get();
			transaction.setPanNumber(client.getPanNumber());
			String tempDate = clientDTO.getMm() + "/" + clientDTO.getYy();
			if (!client.getCardHolder().equals(clientDTO.getCardHolder()) || !client.getCvv().equals(clientDTO.getCvv())
					|| !client.getExpirationDate().equals(tempDate)) {
				System.err.println("ne podudara se");
				transaction.setStatus(TransactionStatus.FAILED);
				failPayment(paymentRequest);
				return paymentRequest.getFailedUrl();
			}

			if (paymentRequest.getAmount() > client.getAvailableFunds()) {
				System.err.println("nema sredstava");
				transaction.setStatus(TransactionStatus.FAILED);
				failPayment(paymentRequest);
				return paymentRequest.getFailedUrl();
			}

			String merchantId = paymentRequest.getMerchantId();
			Client merchant = clientService.getClientByMerchantId(merchantId);
			if (!merchant.getMerchantPassword().equals(paymentRequest.getMerchantPassword())){
				System.err.println("nije dobar merchant");
				transaction.setStatus(TransactionStatus.ERROR);
				failPayment(paymentRequest);
				return paymentRequest.getErrorUrl();
			}

			client.setAvailableFunds(client.getAvailableFunds() - paymentRequest.getAmount());
			clientService.save(client);
			merchant.setAvailableFunds(merchant.getAvailableFunds() + paymentRequest.getAmount());
			clientService.save(merchant);
			transaction.setStatus(TransactionStatus.SUCCESSFUL);
			transactionService.save(transaction);		
			
			CompletePaymentResponseDTO completePaymentResponseDTO = new CompletePaymentResponseDTO();
			completePaymentResponseDTO.setOrder_id(paymentRequest.getMerchantOrderId());
			completePaymentResponseDTO.setStatus("PAID");
			
			ResponseEntity<String> responseEntity = restTemplate.exchange(paymentRequest.getCallbackUrl() + "/complete", HttpMethod.POST,
					new HttpEntity<CompletePaymentResponseDTO>(completePaymentResponseDTO), String.class);
			System.out.println(responseEntity.getBody());
			
			return paymentRequest.getSuccessUrl();
		}
		else {
			// To nije ova banka idemo na pcc
			System.out.println("Nisu iz iste banke idemo na pcc");
			Transaction saved = transactionService.save(transaction);
			// ACQUIRER_ORDER_ID = saved.id
			// ACQUIRER_TIMESTAMP
			PccRequestDTO pccRequestDTO = new PccRequestDTO();
			pccRequestDTO.setAcquirerOrderId(saved.getId());
			pccRequestDTO.setAcquirerTimestamp(new Date());
			pccRequestDTO.setCardHolder(clientDTO.getCardHolder());
			pccRequestDTO.setCvv(clientDTO.getCvv());
			pccRequestDTO.setMm(clientDTO.getMm());
			pccRequestDTO.setPanNumber(clientDTO.getPanNumber());
			pccRequestDTO.setYy(clientDTO.getYy());
			pccRequestDTO.setAmount(paymentRequest.getAmount());
			
			HttpHeaders headers = new HttpHeaders();
		    headers.setContentType(MediaType.APPLICATION_JSON);
		    HttpEntity<PccRequestDTO> request = new HttpEntity<PccRequestDTO>(pccRequestDTO, headers);
		    
		    String merchantId = paymentRequest.getMerchantId();
			Client merchant = clientService.getClientByMerchantId(merchantId);
			if (!merchant.getMerchantPassword().equals(paymentRequest.getMerchantPassword())){
				System.err.println("nije dobar merchant");
				transaction.setStatus(TransactionStatus.ERROR);
				failPayment(paymentRequest);
				return paymentRequest.getErrorUrl();
			} // Ako ne potoji prodavac nece se ni otici do kupca
		    
		    // Ovde dobijamo podatke o uspesnosti transakcije
		    AcquirerResponseDTO response = restTemplate.postForObject(pccUrl + "/payRedirect", request, AcquirerResponseDTO.class);
		  
		    
		    if(response.getIsAuthentificated() && response.getIsTransactionAutorized()) {    	
		    	merchant.setAvailableFunds(merchant.getAvailableFunds() + paymentRequest.getAmount());
				clientService.save(merchant);
				transaction.setStatus(TransactionStatus.SUCCESSFUL);
				transactionService.save(transaction);
				
				// Obavetavamo KP o uspesnosti transakcije
			    CompletePaymentResponseDTO completePaymentResponseDTO = new CompletePaymentResponseDTO();
				completePaymentResponseDTO.setOrder_id(paymentRequest.getMerchantOrderId());
				completePaymentResponseDTO.setStatus("PAID");
				
				ResponseEntity<String> responseEntity = restTemplate.exchange(paymentRequest.getCallbackUrl() + "/complete", HttpMethod.POST,
						new HttpEntity<CompletePaymentResponseDTO>(completePaymentResponseDTO), String.class);
				System.out.println(responseEntity.getBody());
			     
				return paymentRequest.getSuccessUrl();
		    }
		   
		    return paymentRequest.getErrorUrl();
		}
		
		
		
	}
	
	private String getBankIdFromPan(String panNumber) {
	
		String withoutDashes = panNumber.replace("-", "");
		String number = withoutDashes.substring(1, 7);
		return number;
	}

	private void failPayment(PaymentRequest paymentRequest) {
		CompletePaymentResponseDTO completePaymentResponseDTO = new CompletePaymentResponseDTO();
		completePaymentResponseDTO.setOrder_id(paymentRequest.getMerchantOrderId());
		completePaymentResponseDTO.setStatus("FAILED");
		ResponseEntity<String> responseEntity = restTemplate.exchange(paymentRequest.getCallbackUrl() + "/complete", HttpMethod.POST,
				new HttpEntity<CompletePaymentResponseDTO>(completePaymentResponseDTO), String.class);
		System.out.println(responseEntity.getBody());
	}
}
