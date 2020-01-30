package tim18.ftn.uns.ac.rs.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tim18.ftn.uns.ac.rs.bank.exceptions.NotFoundException;
import tim18.ftn.uns.ac.rs.bank.service.TransactionService;


@RestController
@RequestMapping("/transaction")
public class TransactionController {

	@Autowired
	private TransactionService transactionService;
	
	@RequestMapping(value = "/{orderId}", method = RequestMethod.GET)
	private ResponseEntity<?> getTransaction(@PathVariable Integer orderId) throws NotFoundException {
		System.out.println("Getting transaction for order id:" + orderId);
		return new ResponseEntity<>(transactionService.findOne(orderId), HttpStatus.OK);
	}
	

}
