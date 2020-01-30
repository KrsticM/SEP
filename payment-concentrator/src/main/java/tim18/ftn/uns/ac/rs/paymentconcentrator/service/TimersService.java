package tim18.ftn.uns.ac.rs.paymentconcentrator.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import tim18.ftn.uns.ac.rs.paymentconcentrator.dto.CompletePaymentResponseDTO;
import tim18.ftn.uns.ac.rs.paymentconcentrator.model.Order;
import tim18.ftn.uns.ac.rs.paymentconcentrator.model.OrderStatus;

@Service
public class TimersService {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	// Svaka porudzbina traje samo odredjeno vreme, odnosno mora se izvrsiti u konacnom vremenu
	// Ako se ne izvrsi u zadatom periodu, link postane nevalidan i nije moguce dalje uraditi redirekciju
	@Scheduled(fixedDelay = 60000) // na svaki minut
	public void checkOrders() { 
		List<Order> orders = orderService.findAll();
		
		for(Order o : orders) {
			if(o.getStatus() == OrderStatus.CREATED) { // Ako jos nije poslat
				if(o.getTicks() < 5) {
					o.setTicks(o.getTicks() + 1);
					orderService.saveOrder(o);
					System.out.println("\t\tDEBUG: Tics za order: " + o.getId() + " povecan");
				}
				else { // Javiti NC da je order neuspesan
					o.setStatus(OrderStatus.FAILED);
					orderService.saveOrder(o);
					
					CompletePaymentResponseDTO completePaymentResponseDTO = new CompletePaymentResponseDTO();
					completePaymentResponseDTO.setOrder_id(o.getOrderIdScienceCenter());
					completePaymentResponseDTO.setStatus("FAILED");
					
					System.out.println(o.getCallbackUrl());
					restTemplate.exchange(o.getCallbackUrl(), HttpMethod.POST,	new HttpEntity<CompletePaymentResponseDTO>(completePaymentResponseDTO), String.class);
					
				}
			}
		}
	}
	
}
