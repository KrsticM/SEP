package tim18.ftn.uns.ac.rs.sciencecenter.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PaymentResponseDTO {
	
	private String redirectUrl;
	private Integer orderId;
	private Double price;
	private String callbackUrl;
	
}
