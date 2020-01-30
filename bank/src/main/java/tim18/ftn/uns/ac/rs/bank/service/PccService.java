package tim18.ftn.uns.ac.rs.bank.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim18.ftn.uns.ac.rs.bank.dto.PccRequestDTO;
import tim18.ftn.uns.ac.rs.bank.dto.PccResponseDTO;
import tim18.ftn.uns.ac.rs.bank.model.Client;

@Service
public class PccService {

	@Autowired 
	private ClientService clientService;
	
	public PccResponseDTO pay(PccRequestDTO pccRequestDTO) {
		
		Optional<Client> clientOpt = clientService.getClient(pccRequestDTO.getPanNumber());
		if(!clientOpt.isPresent()) {
			return failAuthentification(pccRequestDTO); // Ako klijent sa tim PAN-om ne postoji u ovoj banci
		} 
		
		Client client = clientOpt.get();
		String tempDate = pccRequestDTO.getMm() + "/" + pccRequestDTO.getYy();
		if (!client.getCardHolder().equals(pccRequestDTO.getCardHolder()) || !client.getCvv().equals(pccRequestDTO.getCvv())
				|| !client.getExpirationDate().equals(tempDate)) {
			System.err.println("PccService: Podaci se ne podudaraju. ");
			return failAuthentification(pccRequestDTO);
		}

		if (pccRequestDTO.getAmount() > client.getAvailableFunds()) {
			System.err.println("nema sredstava");
			return failPayment(pccRequestDTO);
		}

		client.setAvailableFunds(client.getAvailableFunds() - pccRequestDTO.getAmount());
		clientService.save(client);	
		
		return successPayment(pccRequestDTO);
	}

	private PccResponseDTO successPayment(PccRequestDTO pccRequestDTO) {
		PccResponseDTO ret = new PccResponseDTO();
		ret.setIsAuthentificated(true);
		ret.setIsTransactionAutorized(true);
		ret.setAcquirerOrderId(pccRequestDTO.getAcquirerOrderId());
		ret.setAcquirerTimestamp(pccRequestDTO.getAcquirerTimestamp());
		return ret;
	}

	private PccResponseDTO failAuthentification(PccRequestDTO pccRequestDTO) {
		PccResponseDTO ret = new PccResponseDTO();
		ret.setIsAuthentificated(false);
		ret.setIsTransactionAutorized(false);
		ret.setAcquirerOrderId(pccRequestDTO.getAcquirerOrderId());
		ret.setAcquirerTimestamp(pccRequestDTO.getAcquirerTimestamp());
		return ret;
	}
	
	private PccResponseDTO failPayment(PccRequestDTO pccRequestDTO) {
		PccResponseDTO ret = new PccResponseDTO();
		ret.setIsAuthentificated(true);
		ret.setIsTransactionAutorized(false);
		ret.setAcquirerOrderId(pccRequestDTO.getAcquirerOrderId());
		ret.setAcquirerTimestamp(pccRequestDTO.getAcquirerTimestamp());
		return ret;
	}

}
