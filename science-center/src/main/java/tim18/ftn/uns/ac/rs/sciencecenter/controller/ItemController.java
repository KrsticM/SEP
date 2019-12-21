package tim18.ftn.uns.ac.rs.sciencecenter.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import tim18.ftn.uns.ac.rs.sciencecenter.dto.ItemDTO;
import tim18.ftn.uns.ac.rs.sciencecenter.dto.OrderResponseDTO;
import tim18.ftn.uns.ac.rs.sciencecenter.dto.PaymentRequestDTO;
import tim18.ftn.uns.ac.rs.sciencecenter.exceptions.NotFoundException;
import tim18.ftn.uns.ac.rs.sciencecenter.model.Item;
import tim18.ftn.uns.ac.rs.sciencecenter.model.Order;
import tim18.ftn.uns.ac.rs.sciencecenter.service.ItemService;
import tim18.ftn.uns.ac.rs.sciencecenter.service.OrderService;

@RestController
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private OrderService orderService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getItems() {
		List<ItemDTO> ret = itemService.getItems();
		return ResponseEntity.ok(ret);
	}
	
	@RequestMapping(value="/pay", method = RequestMethod.POST)
	public String  makePayment(@RequestBody PaymentRequestDTO paymentRequestDTO, HttpServletResponse httpServletResponse) throws NotFoundException {
		System.out.println(paymentRequestDTO);
		// Pretpostavka: Svi artikli bi trebalo da pripadaju jednom prodavcu
		String merchantApiKey = "";
		
		for(Integer id : paymentRequestDTO.getIds()) {
			merchantApiKey = itemService.getItem(id).getMerchant().getApi_key();
			break; // Za sve artikle je isti
		}
		
		double price = 0;
		for(Integer id : paymentRequestDTO.getIds()) {
			Item i = itemService.getItem(id);
			price += i.getPrice();
		}
		
		Order o = new Order();
		o.setPrice(price);
		o.setCallbackUrl("http://localhost:8008/order/complete");
		Order savedOrder = orderService.saveOrder(o);
		
		String paymentConcentratorUrl = "http://localhost:8762/payment-concentrator";
		System.out.println(paymentConcentratorUrl + "/order/create");
		ResponseEntity<OrderResponseDTO> responseEntity = restTemplate.exchange(paymentConcentratorUrl + "/order/create", HttpMethod.POST,
				new HttpEntity<Order>(savedOrder), OrderResponseDTO.class);
		
		return paymentConcentratorUrl + "/choosePaymentMethod/" + merchantApiKey + "/" + responseEntity.getBody().getId();
	}
	
}
