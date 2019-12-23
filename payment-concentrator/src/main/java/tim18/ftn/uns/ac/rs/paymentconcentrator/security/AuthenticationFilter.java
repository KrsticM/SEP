package tim18.ftn.uns.ac.rs.paymentconcentrator.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import tim18.ftn.uns.ac.rs.paymentconcentrator.model.temporary.CustomUserDetails;
import tim18.ftn.uns.ac.rs.paymentconcentrator.service.AuthenticationService;
import tim18.ftn.uns.ac.rs.paymentconcentrator.service.CustomUserDetailsService;

public class AuthenticationFilter extends OncePerRequestFilter {

	private AuthenticationService authenticationService;

	private CustomUserDetailsService customUserDetailsService;

	public AuthenticationFilter(CustomUserDetailsService customUserDetailsService,
			AuthenticationService authenticationService) {
		this.customUserDetailsService = customUserDetailsService;
		this.authenticationService = authenticationService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String emailHeader = request.getHeader("Email");
		if (emailHeader != null) {
			CustomUserDetails userDetails = (CustomUserDetails) customUserDetailsService.loadUserByUsername(emailHeader); // Username je kod nas Email
			AuthenticationData authentication = new AuthenticationData(userDetails);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		filterChain.doFilter(request, response);
	}

	/*@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		System.out.println("Izfiltrirano");

		MutableHttpServletRequest mutableRequest = new MutableHttpServletRequest(request);

		String authHeader = request.getHeader("Authorization");
		System.out.println("authHeader: " + authHeader);
		if (authHeader != null) {
			if (authHeader.startsWith("Bearer ")) {

				TokenValidationResponse retValue = authenticationService.validateToken(authHeader);

				String email = retValue.getEmail();
				if (email != null) {
					UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
					AuthenticationData authentication = new AuthenticationData(userDetails);
					SecurityContextHolder.getContext().setAuthentication(authentication);

					System.out.println("postavljamo header userId: " + retValue.getUserId());

					mutableRequest.putHeader("userId", retValue.getUserId().toString());

				}
			} 
				 * else {
				 * 
				 * CustomUserDetails userDetails =
				 * customUserDetailsService.loadUserByApiKey(authHeader); AuthenticationData
				 * authentication = new AuthenticationData(userDetails);
				 * SecurityContextHolder.getContext().setAuthentication(authentication);
				 * 
				 * System.out.println("postavljamo header userId: " + userDetails.getUserId());
				 * 
				 * mutableRequest.putHeader("userId", userDetails.getUserId().toString()); }
				 

		}
		filterChain.doFilter(mutableRequest, response);
	}*/

}