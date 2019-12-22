package tim18.ftn.uns.ac.rs.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tim18.ftn.uns.ac.rs.bank.dto.ClientDTO;
import tim18.ftn.uns.ac.rs.bank.dto.PaymentRequestDTO;
import tim18.ftn.uns.ac.rs.bank.dto.PaymentResponseDTO;
import tim18.ftn.uns.ac.rs.bank.exceptions.NotFoundException;
import tim18.ftn.uns.ac.rs.bank.service.PaymentService;

@RestController
@RequestMapping("/payment")
public class PaymentController {

	@Autowired
	private PaymentService paymentService;

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	private ResponseEntity<?> createPayment(@RequestBody PaymentRequestDTO paymentRequestDTO) {
		System.out.println("Create payment");

		PaymentResponseDTO paymentResponseDTO = paymentService.createPayment(paymentRequestDTO);
		return new ResponseEntity<>(paymentResponseDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/confirm/{paymentRequestId}", method = RequestMethod.POST)
	private String confirmPayment(@RequestBody ClientDTO clientDTO, @PathVariable Integer paymentRequestId)
			throws NotFoundException {
		System.out.println("Payment request id: " + paymentRequestId);
		return paymentService.confirmPayment(clientDTO, paymentRequestId);
	}

}
