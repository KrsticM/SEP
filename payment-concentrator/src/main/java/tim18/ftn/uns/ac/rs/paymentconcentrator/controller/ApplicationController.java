package tim18.ftn.uns.ac.rs.paymentconcentrator.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tim18.ftn.uns.ac.rs.paymentconcentrator.exceptions.NotFoundException;
import tim18.ftn.uns.ac.rs.paymentconcentrator.service.UserService;

@RestController
@RequestMapping("/app")
public class ApplicationController {

	@Autowired
	private UserService userService;
	
	// TODO: Add application
	
	// TODO: Remove application
	
	// TODO: Regenerate api key
	@PreAuthorize("hasAnyRole('USER')")
	@RequestMapping(value = "/regenerate-token/{appId}", method = RequestMethod.GET)
	public ResponseEntity<?> generateToken(@RequestHeader Map<String, String> headers) throws NotFoundException {
		Integer userId = Integer.parseInt(headers.get("userId"));
		return new ResponseEntity<>(userService.regenerateToken(userId), HttpStatus.OK);
	}
	
}
