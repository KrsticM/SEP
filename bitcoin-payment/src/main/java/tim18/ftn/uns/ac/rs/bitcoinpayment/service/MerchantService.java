package tim18.ftn.uns.ac.rs.bitcoinpayment.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim18.ftn.uns.ac.rs.bitcoinpayment.dto.MerchantInfoDTO;
import tim18.ftn.uns.ac.rs.bitcoinpayment.exeptions.NotFoundException;
import tim18.ftn.uns.ac.rs.bitcoinpayment.model.Merchant;
import tim18.ftn.uns.ac.rs.bitcoinpayment.repository.MerchantRepository;

@Service
public class MerchantService {
	
	Logger logger = LoggerFactory.getLogger(MerchantService.class);
	
	@Autowired
	private MerchantRepository merchantRepository;
	
	public Merchant saveMerchant(Merchant merchant) {
		return merchantRepository.save(merchant);
	}
	
	public Optional<Merchant> findByAppIdOpt(Integer appId) {
		return merchantRepository.findByApplicationId(appId);
	}
	
	public Merchant findByAppId(Integer appId) throws NotFoundException {
		Optional<Merchant> merchant = merchantRepository.findByApplicationId(appId);

		if (!merchant.isPresent()) {
			logger.error("Merchant with appId " + appId + " not found.");
			throw new NotFoundException(appId, Merchant.class.getSimpleName());
		}

		return merchant.get();
	}
	
	public String addOrUpdateConfig(MerchantInfoDTO merchantInfoDTO, Integer appId) {
		Optional<Merchant> merchantOpt = findByAppIdOpt(appId);

		if (!merchantOpt.isPresent()) {
			Merchant merchant = new Merchant(merchantInfoDTO, appId);
			saveMerchant(merchant);
			logger.info("Saved merchant with coingate token " + merchantInfoDTO.getCoingateToken() + " and application with id" + appId);
		} else {
			Merchant merchant = merchantOpt.get();
			merchant.setCoingateToken(merchantInfoDTO.getCoingateToken());
			saveMerchant(merchant);
			logger.info("Updated merchant with coingate token " + merchantInfoDTO.getCoingateToken() + " and application with id" + appId);

		}
		return "http://localhost:8300/view/successfulConfig";
	}
}
