package tim18.ftn.uns.ac.rs.paypalpayment.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tim18.ftn.uns.ac.rs.paypalpayment.model.Merchant;

@NoArgsConstructor
@Getter
@Setter
public class MerchantRegisterDTO {

	@NotBlank(message = "Please, enter an app ID.")
	private String appId;

	@NotBlank(message = "Please, enter a client ID for your paypal account.")
	private String clientId;

	@NotBlank(message = "Please, enter a secret for your paypal account.")
	private String clientSecret;

	public Merchant createMerchant() {
		Merchant ret = new Merchant();
		ret.setAppId(this.appId);
		ret.setClientId(this.clientId);
		ret.setClientSecret(this.clientSecret);
		return ret;
	}
}