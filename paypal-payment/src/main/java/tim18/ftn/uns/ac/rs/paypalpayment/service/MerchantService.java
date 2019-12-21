package tim18.ftn.uns.ac.rs.paypalpayment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import tim18.ftn.uns.ac.rs.paypalpayment.dto.MerchantRegisterDTO;
import tim18.ftn.uns.ac.rs.paypalpayment.model.Merchant;
import tim18.ftn.uns.ac.rs.paypalpayment.repository.MerchantRepository;

public class MerchantService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private MerchantRepository merchantRepository;

	/**
	 * Registers new user. Encrypts password. Adds role to user.
	 * 
	 * @param RegistrationDTO registration data
	 * @return User with set id
	 */
	public Merchant registerMerchant(MerchantRegisterDTO registrationDTO) {
		Merchant merchant = registrationDTO.createMerchant();
		// TODO : encrypt secret and client id
		return merchantRepository.save(merchant);
	}
}
