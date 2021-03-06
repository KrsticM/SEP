package tim18.ftn.uns.ac.rs.cardpayment.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim18.ftn.uns.ac.rs.cardpayment.dto.MerchantInfoDTO;
import tim18.ftn.uns.ac.rs.cardpayment.exceptions.NotFoundException;
import tim18.ftn.uns.ac.rs.cardpayment.model.Merchant;
import tim18.ftn.uns.ac.rs.cardpayment.repository.MerchantRepository;

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

	// TODO: proveriti da li postoji u banci taj merchant
	public String addOrUpdateConfig(MerchantInfoDTO merchantInfoDTO, Integer appId) {
		Optional<Merchant> merchantOpt = findByAppIdOpt(appId);

		if (!merchantOpt.isPresent()) {
			Merchant merchant = new Merchant(merchantInfoDTO, appId);
			saveMerchant(merchant);
			logger.info("Saved merchant with id " + merchantInfoDTO.getMerchantId() + " and application with id" + appId);
		} else {
			Merchant merchant = merchantOpt.get();
			merchant.setMerchantId(merchantInfoDTO.getMerchantId());
			merchant.setMerchantPassword(merchantInfoDTO.getMerchantPassword());
			saveMerchant(merchant);
			logger.info("Updated merchant with id " + merchantInfoDTO.getMerchantId() + " and application with id" + appId);

		}
		return "http://localhost:8400/view/successfulConfig";
	}

}
