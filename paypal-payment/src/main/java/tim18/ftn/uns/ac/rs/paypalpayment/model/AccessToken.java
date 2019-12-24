package tim18.ftn.uns.ac.rs.paypalpayment.model;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import tim18.ftn.uns.ac.rs.paypalpayment.config.StringConverter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class AccessToken {

	@Id
	private Integer appId;
	
	@Column
	@Convert(converter = StringConverter.class)
	String token;
	
	@Column
	LocalDateTime expiresAt;
	
	public AccessToken() {
		
	}
	
	public AccessToken(Integer applicationId, String accessToken, long expiresIn) {
		this.appId = applicationId;
		this.token = accessToken;
		this.expiresAt = LocalDateTime.now().plusSeconds(expiresIn);
	}
	
	public void setExpiresAt(long expiresIn) {
		this.expiresAt = LocalDateTime.now().plusSeconds(expiresIn);
	}
}