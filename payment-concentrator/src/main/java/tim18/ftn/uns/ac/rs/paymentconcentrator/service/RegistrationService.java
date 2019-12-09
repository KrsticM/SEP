package tim18.ftn.uns.ac.rs.paymentconcentrator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import tim18.ftn.uns.ac.rs.paymentconcentrator.dto.RegistrationDTO;
import tim18.ftn.uns.ac.rs.paymentconcentrator.model.Authority;
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
		User user = registrationDTO.createUser();
		return saveUser(user, registrationDTO.getPassword());
	}

	private User saveUser(User user, String password) {
		// postavi kriptovanu sifru
		user.setPassword(passwordEncoder.encode(password));
		// dodavanje uloge
		Authority authority = authorityService.findByName("USER");
		user.getUserAuthorities().add(authority);
		return userService.saveUser(user);
	}

}
