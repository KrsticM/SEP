package tim18.ftn.uns.ac.rs.paymentconcentrator.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import tim18.ftn.uns.ac.rs.paymentconcentrator.dto.CreateOrderDTO;
import tim18.ftn.uns.ac.rs.paymentconcentrator.dto.CreateOrderResponseDTO;
import tim18.ftn.uns.ac.rs.paymentconcentrator.exceptions.NotFoundException;
import tim18.ftn.uns.ac.rs.paymentconcentrator.model.Order;
import tim18.ftn.uns.ac.rs.paymentconcentrator.model.OrderStatus;
import tim18.ftn.uns.ac.rs.paymentconcentrator.service.OrderService;

@RestController
@RequestMapping("/redirect")
public class RedirectController {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private OrderService orderService;

	@RequestMapping(value = "/{method}/{orderIdSellerService}", method = RequestMethod.POST)
	public ResponseEntity<?> createOrderInOneOfPaymentServices(@PathVariable String method,@PathVariable UUID orderIdSellerService, @RequestBody CreateOrderDTO createOrderDTO) throws NotFoundException { // User je sistem prodavaca		
		System.out.println("\t\tDEBUG: method: " + method);
		System.out.println(createOrderDTO);
		System.out.println("http://localhost:8762/" + method + "/create");
		System.out.println("ORDER ID Science center: " + orderIdSellerService);
		
		Order order = orderService.findById(orderIdSellerService);
		
		if(order.getTicks() >= 5) { // Ima 5 minuta da odabere metodu placanja
			System.err.println("\t\tIsteklo je vreme za ovaj order!");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		
		// Slanje ordera dalje
		ResponseEntity<CreateOrderResponseDTO> responseEntity = restTemplate.exchange("http://localhost:8762/" + method + "/create", HttpMethod.POST, new HttpEntity<CreateOrderDTO>(createOrderDTO), CreateOrderResponseDTO.class);
		
		order.setStatus(OrderStatus.SENT);
		orderService.saveOrder(order);
		
		System.out.println(responseEntity.getBody());
		return new ResponseEntity<>(responseEntity.getBody(), HttpStatus.OK);
	}
}
