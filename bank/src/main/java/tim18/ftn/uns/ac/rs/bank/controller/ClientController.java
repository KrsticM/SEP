package tim18.ftn.uns.ac.rs.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tim18.ftn.uns.ac.rs.bank.model.Client;
import tim18.ftn.uns.ac.rs.bank.service.ClientService;

@RestController
@RequestMapping("/client")
public class ClientController {
	
	@Autowired
	private ClientService clientService;
	
	/**
	 * Adds a new client.
	 * 
	 * @param client  - informations of the client
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Client> addClient(@RequestBody Client client) {
		return new ResponseEntity<>(clientService.save(client), HttpStatus.CREATED);
	}
	
	

}
