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
	
	private Integer appId;

	private Integer orderIdScienceCenter;

	private String payer;

	@NotBlank(message = "Please, enter callback url.")
	private String callbackUrl;

	public Order createOrder() {
		Order ret = new Order(
			this.appId,
			this.payer,
			this.price,
			this.orderIdScienceCenter,
			this.callbackUrl
		);
		return ret;
	}
}
