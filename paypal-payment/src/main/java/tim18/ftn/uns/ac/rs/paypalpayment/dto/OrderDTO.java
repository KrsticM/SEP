package tim18.ftn.uns.ac.rs.paypalpayment.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tim18.ftn.uns.ac.rs.paypalpayment.model.Order;

@NoArgsConstructor
@Getter
@Setter
public class OrderDTO {
	private Double price;

	@NotBlank(message = "Please, enter an app ID.")
	private Integer appId;

	@NotBlank(message = "Please, enter payer detail.")
	private String payer;

	@NotBlank(message = "Please, enter callback url.")
	private String callbackUrl;

	public Order createOrder() {
		Order ret = new Order(
			this.appId,
			this.payer,
			this.price,
			this.callbackUrl
		);
		return ret;
	}
}
