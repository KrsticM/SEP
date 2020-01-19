package tim18.ftn.uns.ac.rs.paymentconcentrator.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tim18.ftn.uns.ac.rs.paymentconcentrator.dto.ApplicationDTO;
import tim18.ftn.uns.ac.rs.paymentconcentrator.exceptions.NotFoundException;
import tim18.ftn.uns.ac.rs.paymentconcentrator.service.ApplicationService;
import tim18.ftn.uns.ac.rs.paymentconcentrator.service.UserService;

@RestController
@RequestMapping("/app")
public class ApplicationController {

	Logger logger = LoggerFactory.getLogger(ApplicationController.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private ApplicationService applicationService;
	
	
	@PreAuthorize("hasAnyRole('USER')") 
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getApplications(@RequestHeader(value="UserId") Integer userId) throws NotFoundException { // User je sistem prodavaca
		logger.info("Loading all applications of user with id " + userId);
		return new ResponseEntity<>(applicationService.getApplications(userId), HttpStatus.OK);
	}

	
	@PreAuthorize("hasAnyRole('USER')") // Trenutno je dodavanje moguce samo preko front-enda. Bice potrebno omoguciti dodavanje i iz Naucnih centrala. (API)
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addApplication(@RequestHeader(value="UserId") Integer userId, @RequestBody ApplicationDTO applicationDTO) throws NotFoundException { // User je sistem prodavaca		
		logger.info("Adding application with name " + applicationDTO.getName() + " for user with id " + userId);
		return new ResponseEntity<>(applicationService.addApplication(userId, applicationDTO), HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasAnyRole('USER')")
	@RequestMapping(value = "/{appId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> removeApplication(@RequestHeader(value="UserId") Integer userId, @PathVariable Integer appId) throws NotFoundException { // User je sistem prodavaca		
		logger.info("Removing application with id " + appId + " for user with id " + userId);
		return new ResponseEntity<>(applicationService.removeApplication(userId, appId), HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('USER')")
	@RequestMapping(value = "/{appId}", method = RequestMethod.GET)
	public ResponseEntity<?> getApplication(@RequestHeader(value="UserId") Integer userId, @PathVariable Integer appId) throws NotFoundException { // User je sistem prodavaca		
		logger.info("Load application with id " + appId + " for user with id " + userId);
		return new ResponseEntity<>(applicationService.getApplication(userId, appId), HttpStatus.OK);
	}
	
	// TODO: Regenerate api key
	@PreAuthorize("hasAnyRole('USER')")
	@RequestMapping(value = "/regenerate-token/{appId}", method = RequestMethod.GET)
	public ResponseEntity<?> generateToken(@RequestHeader Map<String, String> headers) throws NotFoundException {
		Integer userId = Integer.parseInt(headers.get("userId"));
		return new ResponseEntity<>(userService.regenerateToken(userId), HttpStatus.OK);
	}
	
}
