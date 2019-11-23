package tim18.ftn.uns.ac.rs.paymentconcentrator.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Parameters for tokens.
 */
@Getter
@Setter
@NoArgsConstructor
public class TokenParameters {
	private String header = "Authorization";
	private String prefix = "Bearer";
	private int expiration = 24*60*60;
	private String secret = "tajna";
	
	private String AUDIENCE_UNKNOWN = "unknown";
	private String AUDIENCE_WEB = "web";
	private String AUDIENCE_MOBILE = "mobile";
	private String AUDIENCE_TABLET = "tablet";
}
