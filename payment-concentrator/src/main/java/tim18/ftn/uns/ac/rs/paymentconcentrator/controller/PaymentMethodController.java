package tim18.ftn.uns.ac.rs.paymentconcentrator.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tim18.ftn.uns.ac.rs.paymentconcentrator.exceptions.NotFoundException;
import tim18.ftn.uns.ac.rs.paymentconcentrator.service.PaymentMethodService;

@RestController
@RequestMapping("/payment-method")
public class PaymentMethodController {
	
	@Autowired
	private PaymentMethodService paymentMethodService;

	@PreAuthorize("hasAnyRole('USER')") // Trenutno radi samo za front-end usera. Kako bi i casopisi mogli sami da dodaju metode potrebno je izloziti API
	@RequestMapping(value = "/{appId}/{method}", method = RequestMethod.POST)
	public ResponseEntity<?> addMethod(@RequestHeader("UserId") Integer userId, @PathVariable Integer appId, @PathVariable String method) throws NotFoundException {
		System.out.println("add Metode placanja:" + method + " za aplikaciju: " + appId);
		return new ResponseEntity<>(paymentMethodService.addPaymentMethod(method, userId, appId), HttpStatus.CREATED);
	}

	@PreAuthorize("hasAnyRole('USER')")
	@RequestMapping(value = "/{appId}/{method}", method = RequestMethod.DELETE)
	public ResponseEntity<?> removeMethod(@RequestHeader("UserId") Integer userId, @PathVariable Integer appId, @PathVariable String method) throws NotFoundException {
		System.out.println("remove Metode placanja:" + method + " za korisnika: " + userId);
		return new ResponseEntity<>(paymentMethodService.removePaymentMethod(method, userId, appId), HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('USER')")
	@GetMapping("/{appId}")
	public ResponseEntity<?> getUserPaymentMethods(@RequestHeader("UserId") Integer userId, @PathVariable Integer appId)
			throws NotFoundException {
		return new ResponseEntity<>(paymentMethodService.getPaymentMethods(userId, appId), HttpStatus.OK);
	}
}
