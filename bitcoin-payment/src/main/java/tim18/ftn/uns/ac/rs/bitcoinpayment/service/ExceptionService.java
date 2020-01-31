package tim18.ftn.uns.ac.rs.bitcoinpayment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import tim18.ftn.uns.ac.rs.bitcoinpayment.dto.CompletePaymentResponseDTO;
import tim18.ftn.uns.ac.rs.bitcoinpayment.model.Order;
import tim18.ftn.uns.ac.rs.bitcoinpayment.model.OrderStatus;

@Service
public class ExceptionService {

	@Autowired
	private OrderService orderService;

	@Autowired
	private RestTemplate restTemplate;

	@Scheduled(fixedDelay = 60000)
	public void checkOrders() { // U sluaju kada bi coingate pukao

		List<Order> orders = orderService.findAllByStatus(OrderStatus.CREATED);

		for (Order order : orders) {
			System.err.println("usao");
			System.err.println(order);
			System.err.println("ticks " + order.getTicks());
			if (order.getTicks() < 3) { // broj minuta
				order.setTicks(order.getTicks() + 1);
				orderService.saveOrder(order);
				System.out.println("Tick za order: " + order.getId() + " povecan za 1");
			} else {
				System.out.println("Order istekao: " + order.getId());
				order.setStatus(OrderStatus.EXPIRED); // Nesto je poslo po zlu, javi to i naucnoj centrali
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
