package tim18.ftn.uns.ac.rs.pcc.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim18.ftn.uns.ac.rs.pcc.model.BankInfo;
import tim18.ftn.uns.ac.rs.pcc.repository.BankInfoRepository;

@Service
public class BankInfoService {
	
	@Autowired
	private BankInfoRepository bankInfoRepository;

	public String getBankUrl(String bankId) {
		Optional<BankInfo> opt = bankInfoRepository.findByBankIdFromPan(bankId);
		
		if(!opt.isPresent()) {
			System.err.println("Nisu pronadjene informacije za tu banku");
		}
		
		return opt.get().getBankUrl();
	}
	
}
