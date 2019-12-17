package tim18.ftn.uns.ac.rs.bitcoinpayment.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import tim18.ftn.uns.ac.rs.bitcoinpayment.dto.BTCOrder;

@Service
public class PaymentService {

	private String url = "http://localhost:8300";
	private String sandboxUrl = "https://api-sandbox.coingate.com/v2/orders";
	private String token;

	public Map<String, Object> pay(Integer merchantId, Double price) {
		
		Map<String, Object> response = new HashMap<String, Object>();
		BTCOrder btcOrder = new BTCOrder();

		
		btcOrder.setOrder_id("Merchant's ID");
		btcOrder.setPrice_amount(price);
		btcOrder.setCancel_url(url + "/cancelbtc");
		token = UUID.randomUUID().toString();
		btcOrder.setSuccess_url(url + "/btcsuccess/" + token);
		btcOrder.setCallback_url("http://localhost:8080/api/bitcoin/complete/payment");
		btcOrder.setToken(token);

		String clientSecret = "Bearer xHdExMvwHJzNbKhmdQTF_WRLuovaEF18M_rypaEV";
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", clientSecret);
		System.out.println("Slanje btcOrder");
		ResponseEntity<BTCOrder> responseEntity = new RestTemplate().exchange(sandboxUrl, HttpMethod.POST,
				new HttpEntity<BTCOrder>(btcOrder, headers), BTCOrder.class);

		System.out.println("Ovde");
		if (responseEntity.getStatusCode() == HttpStatus.UNPROCESSABLE_ENTITY) {
			System.out.println("UNPROCESSABLE_ENTITY");
			return response;
		}

		System.out.println(responseEntity);
		// orderService.save(order);
		response.put("status", "success");
		response.put("redirect_url", responseEntity.getBody().getPayment_url());

		return response;
	}

}
