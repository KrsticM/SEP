package tim18.ftn.uns.ac.rs.bank.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim18.ftn.uns.ac.rs.bank.exceptions.NotFoundException;
import tim18.ftn.uns.ac.rs.bank.model.Client;
import tim18.ftn.uns.ac.rs.bank.repository.ClientRepository;

@Service
public class ClientService {
	@Autowired
	private ClientRepository clientRepository;
	
	public Client save(Client client) {
		return clientRepository.save(client);
	}
	
	public Optional<Client> getClient(String panNumber) {
		return clientRepository.findByPanNumber(panNumber);
	}
	
	public Client getClientByPanNumber(String panNumber) throws NotFoundException {
		Optional<Client> client = clientRepository.findByPanNumber(panNumber);
		if(!client.isPresent()) {
			throw new NotFoundException(panNumber, Client.class.getSimpleName());
		}
		return client.get();
	}
	
	public Client getClientByMerchantId(String merchantId) throws NotFoundException {
		Optional<Client> client = clientRepository.findByMerchantId(merchantId);
		if(!client.isPresent()) {
			// TODO: ako ne postoji transakcija nije validna idi na faild url
			throw new NotFoundException(merchantId, Client.class.getSimpleName());
		}
		return client.get();
	}
	
	

	
	
}
