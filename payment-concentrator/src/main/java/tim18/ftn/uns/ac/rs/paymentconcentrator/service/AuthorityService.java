package tim18.ftn.uns.ac.rs.paymentconcentrator.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim18.ftn.uns.ac.rs.paymentconcentrator.model.Authority;
import tim18.ftn.uns.ac.rs.paymentconcentrator.repository.AuthorityRepository;

@Service
public class AuthorityService {

	@Autowired
	private AuthorityRepository authorityRepository;

	/**
	 * Finds {@link Authority}. named @param name. If no authority with that name is
	 * found, creates new authority.
	 * 
	 * @param name
	 * @return
	 */
	public Authority findByName(String name) {
		Authority ret = null;
		Optional<Authority> authority = authorityRepository.findByName(name);
		if (authority.isPresent()) {
			ret = authority.get();
		} else {
			ret = saveAuthority(name);
		}
		return ret;
	}

	private Authority saveAuthority(String name) {
		Authority authority = new Authority();
		authority.setName(name);
		return authorityRepository.save(authority);
	}

}
