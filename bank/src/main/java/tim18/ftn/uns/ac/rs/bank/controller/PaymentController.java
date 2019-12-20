package tim18.ftn.uns.ac.rs.bank.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tim18.ftn.uns.ac.rs.bank.dto.PaymentRequestDTO;
import tim18.ftn.uns.ac.rs.bank.dto.PaymentResponseDTO;

@RestController
@RequestMapping("/payment")
public class PaymentController {

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	private ResponseEntity<?> createPayment(@RequestBody PaymentRequestDTO paymentRequestDTO) {
		System.out.println("Create payment");
		
		
		PaymentResponseDTO paymentResponseDTO = new PaymentResponseDTO();
		paymentResponseDTO.setPaymentUrl("http://localhost:5005/view/form");
		return new ResponseEntity<>(paymentResponseDTO, HttpStatus.OK);
	}
	
}
