package tim18.ftn.uns.ac.rs.paymentconcentrator.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tim18.ftn.uns.ac.rs.paymentconcentrator.dto.RegistrationDTO;
import tim18.ftn.uns.ac.rs.paymentconcentrator.dto.auth.AuthenticationRequest;
import tim18.ftn.uns.ac.rs.paymentconcentrator.dto.auth.AuthenticationResponse;
import tim18.ftn.uns.ac.rs.paymentconcentrator.dto.temp.TokenValidationResponse;
import tim18.ftn.uns.ac.rs.paymentconcentrator.model.User;
import tim18.ftn.uns.ac.rs.paymentconcentrator.service.AuthenticationService;
import tim18.ftn.uns.ac.rs.paymentconcentrator.service.RegistrationService;

@RestController
@RequestMapping("/auth")
public class AuthentificationController {

	@Autowired
	private RegistrationService registrationService;
	
	@Autowired
	private AuthenticationService authenticationService;

	@RequestMapping(value = "/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> registerUser(@RequestBody @Valid RegistrationDTO registrationDTO) {
		User user = registrationService.registerUser(registrationDTO);
		return ResponseEntity.ok(user);
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid AuthenticationRequest authenticationRequest, HttpServletRequest request){		
		String token = authenticationService.login(authenticationRequest.getEmail(), authenticationRequest.getPassword(), request);
		AuthenticationResponse response = new AuthenticationResponse(token);
		return ResponseEntity.ok(response);
	}
	
	@RequestMapping(value = "/validate-token")
	public ResponseEntity<TokenValidationResponse> validateToken(@RequestHeader(value="Authorization") String token){
		return ResponseEntity.ok(authenticationService.validateToken(token));
	}

}
