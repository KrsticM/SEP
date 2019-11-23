package tim18.ftn.uns.ac.rs.paymentconcentrator.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import tim18.ftn.uns.ac.rs.paymentconcentrator.dto.temp.TokenValidationResponse;
import tim18.ftn.uns.ac.rs.paymentconcentrator.model.temporary.CustomUserDetails;
import tim18.ftn.uns.ac.rs.paymentconcentrator.security.DeviceProvider;
import tim18.ftn.uns.ac.rs.paymentconcentrator.security.TokenUtils;


@Service
public class AuthenticationService {
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	@Autowired
	private AuthenticationManager authenticationManager;
	// UTILS for generating token
	@Autowired
	private TokenUtils tokenUtils;
	@Autowired
	private DeviceProvider deviceProvider;

	public String login(String email, String password, HttpServletRequest request) {
		// check if user exists
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		// find device
		Device device = deviceProvider.getCurrentDevice(request);
		return this.tokenUtils.generateToken(customUserDetailsService.loadUserByUsername(email), device);
	}

	/**
	 * Checks if token is valid. If token is valid returns respose object with set
	 * username and list of authorites owned by user.
	 * 
	 * @param token
	 * @return object containting information about validity of token and token
	 *         owner
	 */
	public TokenValidationResponse validateToken(String token) {
		System.out.println("Poetak metode validacije tokena");
		// create new response in which token is marked as invalid
		TokenValidationResponse response = new TokenValidationResponse();
		// remove Bearer keyword
		token = tokenUtils.getToken(token);
		System.out.println("ucitan token");
		System.out.println(token);
		if (token != null) {
			System.out.println("token nije null");
			// find user linked to token
			String email = tokenUtils.getEmailFromToken(token);
			if (email != null) {
				System.out.println("email ovde nije nullllll");
				// load user and check if token is valid
				UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
				if (tokenUtils.validateToken(token, userDetails)) {
					System.out.println("Token je validan po nekim proracunima");
					CustomUserDetails customUserDetails = (CustomUserDetails)userDetails;
					// set information
					response.setIsValid(true);
					response.setUserId(customUserDetails.getId());
					response.setEmail(email);
					response.setAuthorities(StringUtils.join(userDetails.getAuthorities(), ','));
				}
			}
		}
		return response;
	}
}

