package tim18.ftn.uns.ac.rs.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tim18.ftn.uns.ac.rs.bank.dto.PccRequestDTO;
import tim18.ftn.uns.ac.rs.bank.service.PccService;

@RestController
@RequestMapping("/pcc")
public class PccController {
	
	@Autowired
	private PccService pccService;

	// Endpoint koji se gadja iz PCC-a. Ovaj endpoint treba da proveri da li su podaci validni i da u zavisnosti od toga izvrsi 
	// ili ne izvrsi transakciju
	@RequestMapping(value = "/pay", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<?> pay(@RequestBody PccRequestDTO pccRequestDTO) {
		return new ResponseEntity<>(pccService.pay(pccRequestDTO), HttpStatus.OK);
	}
}
