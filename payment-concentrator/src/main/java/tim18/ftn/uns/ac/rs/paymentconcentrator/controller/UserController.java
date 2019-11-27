package tim18.ftn.uns.ac.rs.paymentconcentrator.controller;

import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

	
	@GetMapping("/generate-token")
	public UUID genrateToken() {
	
		return null;
	}
	
	@RequestMapping(value = "/add-method/{method}", method = RequestMethod.POST)
	public ResponseEntity<?> addMethod(@PathVariable String method, @RequestHeader(value="userId") String userId) { // TODO: @Valid @RequestBody dataDTO
		
		System.out.println("add Metode placanja:" + method + " za korisnika: " + userId);
		return null;
	}
	
	@RequestMapping(value = "/remove-method/{method}", method = RequestMethod.DELETE)
	public ResponseEntity<?> removeMethod(@PathVariable String method, @RequestHeader(value="userId") String userId) { 
		
		System.out.println("remove Metode placanja:" + method + " za korisnika: " + userId);
		return null;
	}
	
	@GetMapping("/listHeaders")
	public ResponseEntity<String> listAllHeaders(
	  @RequestHeader Map<String, String> headers) {
	    headers.forEach((key, value) -> {
	        System.out.println(String.format("Header '%s' = %s", key, value));
	    });
	 
	    return new ResponseEntity<String>(
	      String.format("Listed %d headers", headers.size()), HttpStatus.OK);
	}
	
}
