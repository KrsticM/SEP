package tim18.ftn.uns.ac.rs.paypalpayment.model;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import tim18.ftn.uns.ac.rs.paypalpayment.config.StringConverter;

@Entity
@Getter
@Setter
public class Merchant {


	@Id
	private String appId;
	
	@Column
	@Convert(converter = StringConverter.class)
	String clientId;
	
	@Column
	@Convert(converter = StringConverter.class)
	String clientSecret;
	
	public Merchant() {
		
	}
	
	public Merchant(String appId, String clientId, String clientSecret) {
		this.appId = appId;
		this.clientId = clientId;
		this.clientSecret = clientSecret;
	}

}
