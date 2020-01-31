package tim18.ftn.uns.ac.rs.paymentconcentrator.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class CreateOrderDTO {
	private Integer orderIdScienceCenter;
	private Double price;
	private Integer appId;
	private String callbackUrl;
	private String redirectUrl;
}
