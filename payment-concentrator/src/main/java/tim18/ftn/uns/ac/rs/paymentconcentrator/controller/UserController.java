package tim18.ftn.uns.ac.rs.paymentconcentrator.controller;

import java.util.Map;
import java.util.UUID;

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
@RequestMapping("/user")
public class UserController {

	@Autowired
	private PaymentMethodService paymentMethodService;

	@GetMapping("/generate-token")
	public UUID genrateToken() {

		return null;
	}

	@PreAuthorize("hasAnyRole('PERSONAL', 'ENTERPRISE')")
	@RequestMapping(value = "/add-method/{method}", method = RequestMethod.POST)
	public ResponseEntity<?> addMethod(@PathVariable String method, @RequestHeader Map<String, String> headers)
			throws NotFoundException { // TODO: @Valid @RequestBody dataDTO
		Integer userId = Integer.parseInt(headers.get("userId"));
		System.out.println(headers.get("userId"));
		System.out.println("add Metode placanja:" + method + " za korisnika: " + userId);
		return new ResponseEntity<>(paymentMethodService.addPaymentMethod(method, userId), HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('PERSONAL', 'ENTERPRISE')")
	@RequestMapping(value = "/remove-method/{method}", method = RequestMethod.DELETE)
	public ResponseEntity<?> removeMethod(@PathVariable String method, @RequestHeader Map<String, String> headers)
			throws NotFoundException {

		Integer userId = Integer.parseInt(headers.get("userId"));
		System.out.println("remove Metode placanja:" + method + " za korisnika: " + userId);
		return new ResponseEntity<>(paymentMethodService.removePaymentMethod(method, userId), HttpStatus.OK);
	}

	@GetMapping("/listHeaders")
	public ResponseEntity<String> listAllHeaders(@RequestHeader Map<String, String> headers) {
		headers.forEach((key, value) -> {
			System.out.println(String.format("Header '%s' = %s", key, value));
		});

		return new ResponseEntity<String>(String.format("Listed %d headers", headers.size()), HttpStatus.OK);
	}

}
