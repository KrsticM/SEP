package tim18.ftn.uns.ac.rs.cardpayment.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import tim18.ftn.uns.ac.rs.cardpayment.dto.PaymentRequestDTO;
import tim18.ftn.uns.ac.rs.cardpayment.dto.PaymentResponseDTO;
import tim18.ftn.uns.ac.rs.cardpayment.exceptions.NotFoundException;
import tim18.ftn.uns.ac.rs.cardpayment.model.Merchant;

@Service
public class CardPaymentService {
	
	@Autowired
	private MerchantService merchantService;
	
	private String url = "http://localhost:8400";
	private String bankUrl = "http://localhost:5005/payment/create";

	
	
	public Map<String, Object> pay(Integer applicationId, Double amount) throws NotFoundException {
		Map<String, Object> response = new HashMap<String, Object>();		
		
		Merchant merchant = merchantService.findByAppId(applicationId);
		
		PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO();			
		paymentRequestDTO.setMerchantId(merchant.getMerchantId());
		paymentRequestDTO.setMerchantPassword(merchant.getMerchantPassword());
		paymentRequestDTO.setAmount(amount);
		paymentRequestDTO.setMerchantOrderId("Order123456");
		paymentRequestDTO.setMerchantTimestamp(new Date());
		paymentRequestDTO.setSuccessUrl(url + "/successURL");
		paymentRequestDTO.setFailedUrl(url + "/failedURL");
		paymentRequestDTO.setErrorUrl(url + "/errorURL");
		
		ResponseEntity<PaymentResponseDTO> responseEntity = new RestTemplate().exchange(bankUrl, HttpMethod.POST,
				new HttpEntity<PaymentRequestDTO>(paymentRequestDTO), PaymentResponseDTO.class);
		
		response.put("status", "success");
		response.put("redirect_url", responseEntity.getBody().getPaymentUrl());
		return response;
	}

}
