package tim18.ftn.uns.ac.rs.pcgateway.filter.authentification;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;


public class AuthenticationFilter extends ZuulFilter {
	
	@Autowired
	private RestTemplate restTemplate;

	private String authUrl = "http://payment-concentrator/auth/validate-token";

	@Override
	public boolean shouldFilter() {
		// filter every request
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		// get token
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		String authHeader = request.getHeader("Authorization");
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			// create request and send it
			HttpHeaders headers = new HttpHeaders();
			headers.setBearerAuth(authHeader.substring(7));
			HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
			ResponseEntity<TokenValidationResponse> responseEntity = restTemplate.exchange(authUrl, HttpMethod.GET,
					entity, TokenValidationResponse.class);
			TokenValidationResponse response = responseEntity.getBody();
			// if token is valid add data
			if (response.getIsValid()) {
				ctx.addZuulRequestHeader("UserId", response.getUserId().toString());
				ctx.addZuulRequestHeader("Email", response.getEmail());
			}

		}
		return null;
	}

	@Override
	public String filterType() {
		return FilterConstants.PRE_TYPE;
	}

	@Override
	public int filterOrder() {
		return 0;
	}

}
