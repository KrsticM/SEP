package tim18.ftn.uns.ac.rs.paymentconcentrator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import tim18.ftn.uns.ac.rs.paymentconcentrator.dto.RegistrationDTO;
import tim18.ftn.uns.ac.rs.paymentconcentrator.model.Authority;
import tim18.ftn.uns.ac.rs.paymentconcentrator.model.EnterpriseUser;
import tim18.ftn.uns.ac.rs.paymentconcentrator.model.PersonalUser;
import tim18.ftn.uns.ac.rs.paymentconcentrator.model.User;

@Service
public class RegistrationService {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthorityService authorityService;
	
	@Autowired
	private UserService userService;

	/**
	 * Registers new user. Encrypts password. Adds role to user.
	 * 
	 * @param RegistrationDTO registration data
	 * @return User with set id
	 */
	public User registerUser(RegistrationDTO registrationDTO) {

		if (registrationDTO.getAuthority().contentEquals("PERSONAL")) {
			PersonalUser personalUser = registrationDTO.createPersonalUser();
			return saveUser(personalUser, registrationDTO.getPassword(), "PERSONAL");
		} else if (registrationDTO.getAuthority().contentEquals("ENTERPRISE")) {
			EnterpriseUser enterpriseUser = registrationDTO.createEnterpriseUser();
			return saveUser(enterpriseUser, registrationDTO.getPassword(), "ENTERPRISE");
		}

		return null;
	}

	private User saveUser(User user, String password, String role) {
		// postavi kriptovanu sifru
		user.setPassword(passwordEncoder.encode(password));
		// dodavanje uloge
		Authority authority = authorityService.findByName(role);
		user.getUserAuthorities().add(authority);
		return userService.saveUser(user);
	}

}
