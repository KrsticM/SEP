package tim18.ftn.uns.ac.rs.bitcoinpayment.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import tim18.ftn.uns.ac.rs.bitcoinpayment.exeptions.NotFoundException;
import tim18.ftn.uns.ac.rs.bitcoinpayment.model.BTCOrder;
import tim18.ftn.uns.ac.rs.bitcoinpayment.model.Merchant;

@Service
public class PaymentService {
	
	@Autowired
	private MerchantService merchantService;

	private String url = "http://localhost:8300";
	private String sandboxUrl = "https://api-sandbox.coingate.com/v2/orders";
	private String token;

	public String pay(Integer appId, Double price) throws NotFoundException {
		
		Merchant merchant = merchantService.findByAppId(appId);
		
		BTCOrder btcOrder = new BTCOrder();
		btcOrder.setOrder_id("Merchant's ID");
		btcOrder.setPrice_amount(price);
		btcOrder.setCancel_url(url + "/cancelBtc");
		token = UUID.randomUUID().toString();
		btcOrder.setSuccess_url(url + "/successBtc");
		btcOrder.setCallback_url("http://localhost:8080/api/bitcoin/complete/payment");
		btcOrder.setToken(token);

		String clientSecret = "Bearer " + merchant.getCoingateToken(); 
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", clientSecret);
		System.out.println("Slanje btcOrder");
		ResponseEntity<BTCOrder> responseEntity = new RestTemplate().exchange(sandboxUrl, HttpMethod.POST,
				new HttpEntity<BTCOrder>(btcOrder, headers), BTCOrder.class);


		System.out.println(responseEntity);
		return responseEntity.getBody().getPayment_url();
	}

}
