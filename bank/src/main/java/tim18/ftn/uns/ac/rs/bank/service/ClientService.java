package tim18.ftn.uns.ac.rs.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim18.ftn.uns.ac.rs.bank.model.Client;
import tim18.ftn.uns.ac.rs.bank.repository.ClientRepository;

@Service
public class ClientService {
	@Autowired
	private ClientRepository clientRepository;
	
	public Client save(Client client) {
		return clientRepository.save(client);
	}

	
	
}
