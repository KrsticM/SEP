package tim18.ftn.uns.ac.rs.paymentconcentrator.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tim18.ftn.uns.ac.rs.paymentconcentrator.dto.RegistrationDTO;
import tim18.ftn.uns.ac.rs.paymentconcentrator.model.User;
import tim18.ftn.uns.ac.rs.paymentconcentrator.service.RegistrationService;

@RestController 
public class RegistrationController {

	@Autowired
	private RegistrationService registrationService;

	@RequestMapping(value = "/registerPersonal", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> registerUser(@RequestBody @Valid RegistrationDTO registrationDTO) {
		User user = registrationService.registerUser(registrationDTO);
		return ResponseEntity.ok(user);
	}
	

}
