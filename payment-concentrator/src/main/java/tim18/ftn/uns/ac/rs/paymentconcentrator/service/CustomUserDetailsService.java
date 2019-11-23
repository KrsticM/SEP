package tim18.ftn.uns.ac.rs.paymentconcentrator.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import tim18.ftn.uns.ac.rs.paymentconcentrator.model.User;
import tim18.ftn.uns.ac.rs.paymentconcentrator.model.temporary.CustomUserDetails;
import tim18.ftn.uns.ac.rs.paymentconcentrator.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> optionalUser = userRepository.findByEmail(email);
		optionalUser.orElseThrow(() -> new UsernameNotFoundException("User with email " + email  + "not found."));
		return optionalUser.map(CustomUserDetails::new).get();
	}
}
