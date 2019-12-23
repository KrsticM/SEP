package tim18.ftn.uns.ac.rs.cardpayment.service;

import java.util.Date;

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
import tim18.ftn.uns.ac.rs.cardpayment.model.Order;
import tim18.ftn.uns.ac.rs.cardpayment.model.OrderStatus;

@Service
public class CardPaymentService {
	
	@Autowired
	private MerchantService merchantService;
	
	@Autowired
	private OrderService orderService;
	
	private String url = "http://localhost:8400/view";
	private String bankUrl = "http://localhost:5005/payment/create";

	public String pay(Integer applicationId, Integer orderId) throws NotFoundException {
		
		Merchant merchant = merchantService.findByAppId(applicationId);
		Order order = orderService.findById(orderId);
		
		
		PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO();			
		paymentRequestDTO.setMerchantId(merchant.getMerchantId());
		paymentRequestDTO.setMerchantPassword(merchant.getMerchantPassword());
		paymentRequestDTO.setAmount(order.getPrice());
		paymentRequestDTO.setMerchantOrderId(order.getId());
		paymentRequestDTO.setMerchantTimestamp(new Date());
		paymentRequestDTO.setSuccessUrl(url + "/successURL");
		paymentRequestDTO.setFailedUrl(url + "/failedURL");
		paymentRequestDTO.setErrorUrl(url + "/errorURL");
		
		paymentRequestDTO.setCallbackUrl("http://localhost:8762/card-payment");
		
		ResponseEntity<PaymentResponseDTO> responseEntity = new RestTemplate().exchange(bankUrl, HttpMethod.POST,
				new HttpEntity<PaymentRequestDTO>(paymentRequestDTO), PaymentResponseDTO.class);
		
		order.setOrderStatus(OrderStatus.PAID);
		orderService.saveOrder(order);
		
		return responseEntity.getBody().getPaymentUrl() + "/" + responseEntity.getBody().getPaymentId();
	}

}
