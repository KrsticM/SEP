package tim18.ftn.uns.ac.rs.paypalpayment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim18.ftn.uns.ac.rs.paypalpayment.dto.MerchantRegisterDTO;
import tim18.ftn.uns.ac.rs.paypalpayment.model.Merchant;
import tim18.ftn.uns.ac.rs.paypalpayment.repository.MerchantRepository;

@Service
public class MerchantService {

	@Autowired
	private MerchantRepository merchantRepository;

	/**
	 * Registers new merchant
	 * @param RegistrationDTO registration data
	 * @return Merchant
	 */
	public Merchant registerMerchant(MerchantRegisterDTO registrationDTO) {
		Merchant merchant = registrationDTO.createMerchant();
		return merchantRepository.save(merchant);
	}
}
