package tim18.ftn.uns.ac.rs.bitcoinpayment.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim18.ftn.uns.ac.rs.bitcoinpayment.exeptions.NotFoundException;
import tim18.ftn.uns.ac.rs.bitcoinpayment.model.Merchant;
import tim18.ftn.uns.ac.rs.bitcoinpayment.repository.MerchantRepository;

@Service
public class MerchantService {
	
	@Autowired
	private MerchantRepository merchantRepository;
	
	public Merchant saveMerchant(Merchant merchant) {
		return merchantRepository.save(merchant);
	}
	
	public Merchant findByAppId(Integer appId) throws NotFoundException {
		Optional<Merchant> merchant = merchantRepository.findByApplicationId(appId);

		if (!merchant.isPresent()) {
			throw new NotFoundException(appId, Merchant.class.getSimpleName());
		}

		return merchant.get();
	}
}
