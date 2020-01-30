package tim18.ftn.uns.ac.rs.cardpayment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import tim18.ftn.uns.ac.rs.cardpayment.dto.CompletePaymentResponseDTO;
import tim18.ftn.uns.ac.rs.cardpayment.dto.TransactionDTO;
import tim18.ftn.uns.ac.rs.cardpayment.dto.TransactionStatus;
import tim18.ftn.uns.ac.rs.cardpayment.model.Order;
import tim18.ftn.uns.ac.rs.cardpayment.model.OrderStatus;

@Service
public class ExceptionService {

	@Autowired
	private OrderService orderService;

	@Autowired
	private RestTemplate restTemplate;
	
	private String bankUrl = "http://localhost:5005/transaction";  // banka prodavca

	@Scheduled(fixedDelay = 60000)
	public void checkOrders() {

		List<Order> orders = orderService.findAll();
		
		

		for (Order order : orders) {

			if (order.getOrderStatus() == OrderStatus.CREATED) {
				if (order.getTicks() < 3) { // broj minuta
					order.setTicks(order.getTicks() + 1);
					orderService.saveOrder(order);
					System.out.println("Tick za order: " + order.getId() + " povecan za 1");
					// Moze se desiti da ovaj servis pukne prilikom prihvatanja zahteva od banke (/complete)
					// Tako da sve dok je order u statusu CREATED, mozemo proveravati banku i da li je Transakcija koja je vezana za ovu porudzbinu mozda uspesna
					System.out.println("URL: " + bankUrl + "/" + order.getId());
					try {
						ResponseEntity<TransactionDTO> responseEntity = new RestTemplate().getForEntity(bankUrl + "/" + order.getId(), TransactionDTO.class);
						if(responseEntity.getBody().getStatus() == TransactionStatus.SUCCESSFUL) {
							// sve je proslo kao treba obavesti NC
							order.setOrderStatus(OrderStatus.COMPLETED);
							orderService.saveOrder(order);
							
							CompletePaymentResponseDTO completePaymentResponseDTO = new CompletePaymentResponseDTO();
							completePaymentResponseDTO.setOrder_id(order.getOrderIdScienceCenter());
							completePaymentResponseDTO.setStatus("COMPLETED");
							
							restTemplate.exchange(order.getCallbackUrl(), HttpMethod.POST,new HttpEntity<CompletePaymentResponseDTO>(completePaymentResponseDTO), String.class);
						}
					}
					catch(Exception e) {
						System.out.println("Transakcija jos ne postoji!");
					}
					
					
				} else {
					System.out.println("Order istekao: " + order.getId());
					order.setOrderStatus(OrderStatus.FAILED); // Nesto je poslo po zlu, javi to i naucnoj centrali
					orderService.saveOrder(order);

					CompletePaymentResponseDTO completePaymentResponseDTO = new CompletePaymentResponseDTO();
					completePaymentResponseDTO.setOrder_id(order.getOrderIdScienceCenter());
					completePaymentResponseDTO.setStatus("FAILED");

					restTemplate.exchange(order.getCallbackUrl(), HttpMethod.POST,
							new HttpEntity<CompletePaymentResponseDTO>(completePaymentResponseDTO), String.class);

				}
			}
		}
	}
}
