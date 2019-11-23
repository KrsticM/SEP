package tim18.ftn.uns.ac.rs.paymentconcentrator.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthenticationData extends AbstractAuthenticationToken {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8969473487991624792L;
	private final UserDetails principle;

	public AuthenticationData(UserDetails principle) {
		super(principle.getAuthorities());
		this.principle = principle;
	}

	@Override
	public boolean isAuthenticated() {
		return true;
	}

	@Override
	public Object getCredentials() {
		return principle.getUsername();
	}

	@Override
	public UserDetails getPrincipal() {
		return principle;
	}
}
