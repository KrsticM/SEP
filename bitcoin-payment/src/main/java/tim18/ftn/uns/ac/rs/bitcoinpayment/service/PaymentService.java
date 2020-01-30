package tim18.ftn.uns.ac.rs.bitcoinpayment.service;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import tim18.ftn.uns.ac.rs.bitcoinpayment.dto.CompletePaymentResponseDTO;
import tim18.ftn.uns.ac.rs.bitcoinpayment.dto.OrderInfoDTO;
import tim18.ftn.uns.ac.rs.bitcoinpayment.exeptions.NotFoundException;
import tim18.ftn.uns.ac.rs.bitcoinpayment.model.BTCOrder;
import tim18.ftn.uns.ac.rs.bitcoinpayment.model.Merchant;
import tim18.ftn.uns.ac.rs.bitcoinpayment.model.Order;
import tim18.ftn.uns.ac.rs.bitcoinpayment.model.OrderStatus;

@Service
public class PaymentService {
	
	Logger logger = LoggerFactory.getLogger(PaymentService.class);
	
	@Autowired
	private MerchantService merchantService;

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	private String url = "http://localhost:8300";
	private String sandboxUrl = "https://api-sandbox.coingate.com/v2/orders";
	private String token;

	public String pay(Integer appId, Integer orderId) throws NotFoundException {
		
		Merchant merchant = merchantService.findByAppId(appId);
		logger.info("Payment to the seller " + merchant.getCoingateToken());
		
		Order order = orderService.findById(orderId);
		logger.info("OrderId: " + order.getId());

		BTCOrder btcOrder = new BTCOrder();
		btcOrder.setOrder_id(order.getId().toString());
		btcOrder.setPrice_amount(order.getPrice());
		btcOrder.setCancel_url(url + "/view/cancelBtc");
		token = UUID.randomUUID().toString();
		btcOrder.setSuccess_url(url + "/view/successBtc");
		btcOrder.setCallback_url("http://localhost:8080/api/bitcoin/complete/payment");
		btcOrder.setToken(token);

		logger.info("Payment amount " + order.getPrice());
		
		String clientSecret = "Bearer " + merchant.getCoingateToken(); 
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", clientSecret);
		System.out.println("Slanje btcOrder");
		ResponseEntity<BTCOrder> responseEntity = new RestTemplate().exchange(sandboxUrl, HttpMethod.POST,
				new HttpEntity<BTCOrder>(btcOrder, headers), BTCOrder.class);

		
		System.out.println(responseEntity.getBody());
		
		order.setOrderIdCoinGate(responseEntity.getBody().getId());
		order.setMerchant(merchant);
		orderService.saveOrder(order);
		
		System.out.println(responseEntity);
		logger.info("Saving order" + order.getId() + " for application: " + appId + ", order status: " + order.getStatus()); 

		return responseEntity.getBody().getPayment_url();
	}

	//Task koji ce proveravati sve narudzbine i menjati im stanja
	@Scheduled(fixedDelay = 60000)
	public void fetchCoingate() {
	    System.out.println("Proveravanje transakcija... ");
	    List<Order> unfinishedOrders = orderService.findAllByStatus(OrderStatus.CREATED);
	    
	    for(Order order : unfinishedOrders) {
	    	System.out.println(order);
	    	
	    	// Saljemo upit na coingate i zahtevamo informacije
	    	String clientSecret = "Bearer " + order.getMerchant().getCoingateToken();
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", clientSecret);
			System.out.println("Slanje zahteva za proveravanje narudzbine... 	" + clientSecret);
			HttpEntity entity = new HttpEntity(headers);
			
			System.err.println("URL: " + sandboxUrl + "/" + order.getOrderIdCoinGate());
			ResponseEntity<OrderInfoDTO> response = restTemplate.exchange(sandboxUrl + "/" + order.getOrderIdCoinGate(), HttpMethod.GET, entity, OrderInfoDTO.class);
	    	System.out.println(response.getBody());
	    	
	    	CompletePaymentResponseDTO completePaymentResponseDTO = new CompletePaymentResponseDTO();
			completePaymentResponseDTO.setOrder_id(order.getOrderIdScienceCenter());
			
	    	
	    	OrderInfoDTO responseBody = response.getBody();
	    	// Status paid (Kada je placanje uspesno proslo)
	    	if(responseBody.getStatus().contentEquals("paid")) {
	    		// Narudzbina je placenja, javi to naucnoj centrali
	    		order.setStatus(OrderStatus.COMPLETED);
	    		completePaymentResponseDTO.setStatus("COMPLETED");
	    		orderService.saveOrder(order);
		    	System.out.println(completePaymentResponseDTO);
		    	restTemplate.exchange(order.getCallbackUrl(), HttpMethod.POST, new HttpEntity<CompletePaymentResponseDTO>(completePaymentResponseDTO), String.class);
	    	} 

	    	// Status expired (Kada je isteklo vreme ili se prekine placanje)
	    	else if(responseBody.getStatus().contentEquals("expired")) {
	    		order.setStatus(OrderStatus.FAILED);
	    		completePaymentResponseDTO.setStatus("FAILED");
	    		orderService.saveOrder(order);
		    	System.out.println(completePaymentResponseDTO);
		    	restTemplate.exchange(order.getCallbackUrl(), HttpMethod.POST, new HttpEntity<CompletePaymentResponseDTO>(completePaymentResponseDTO), String.class);
	    	}
	    	
	    	else if(responseBody.getStatus().contentEquals("invalid")) {
	    		order.setStatus(OrderStatus.FAILED);
	    		completePaymentResponseDTO.setStatus("FAILED");
	    		orderService.saveOrder(order);
		    	System.out.println(completePaymentResponseDTO);
		    	restTemplate.exchange(order.getCallbackUrl(), HttpMethod.POST, new HttpEntity<CompletePaymentResponseDTO>(completePaymentResponseDTO), String.class);
	    	}
	    }
	    
	}
	
	/* 
	 	System.out.println("Complete payment");
		logger.info("Complete bitcoin payment for order with id " + completePaymentDTO.getOrder_id()); 

		System.out.println(completePaymentDTO);
		
		Order o = orderService.findById(completePaymentDTO.getOrder_id());
		o.setStatus(OrderStatus.COMPLETED);
		orderService.saveOrder(o);
		logger.info("Order with id " + completePaymentDTO.getOrder_id() + " is completed"); 

		CompletePaymentResponseDTO completePaymentResponseDTO = new CompletePaymentResponseDTO();
		completePaymentResponseDTO.setOrder_id(o.getOrderIdScienceCenter());
		
		if(completePaymentDTO.getStatus().contentEquals("PAID")) {
			completePaymentResponseDTO.setStatus("COMPLETED");
		}
		else {
			completePaymentResponseDTO.setStatus("FAILED");
		} // TODO: Expired..
		
		
		ResponseEntity<String> responseEntity = restTemplate.exchange(o.getCallbackUrl(), HttpMethod.POST,
				new HttpEntity<CompletePaymentResponseDTO>(completePaymentResponseDTO), String.class);
		
		
		return responseEntity.getBody();
	 */
	 
}
