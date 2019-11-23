package tim18.ftn.uns.ac.rs.paymentconcentrator.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import tim18.ftn.uns.ac.rs.paymentconcentrator.dto.temp.TokenValidationResponse;
import tim18.ftn.uns.ac.rs.paymentconcentrator.service.AuthenticationService;
import tim18.ftn.uns.ac.rs.paymentconcentrator.service.CustomUserDetailsService;

public class AuthenticationFilter extends OncePerRequestFilter {

	private AuthenticationService authenticationService;

	private CustomUserDetailsService customUserDetailsService;

	public AuthenticationFilter(CustomUserDetailsService customUserDetailsService, AuthenticationService authenticationService) {
		this.customUserDetailsService = customUserDetailsService;
		this.authenticationService = authenticationService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		System.out.println("Izfiltrirano");
		
		String authHeader = request.getHeader("Authorization");
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			
			TokenValidationResponse retValue = authenticationService.validateToken(authHeader);
			String email = retValue.getEmail();
			if(email != null) {
				UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
				AuthenticationData authentication = new AuthenticationData(userDetails);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}
		filterChain.doFilter(request, response);

	}

}