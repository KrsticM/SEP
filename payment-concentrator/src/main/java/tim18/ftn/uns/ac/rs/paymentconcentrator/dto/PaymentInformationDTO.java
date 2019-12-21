package tim18.ftn.uns.ac.rs.paymentconcentrator.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PaymentInformationDTO {
	private Integer orderId;
	private Double orderPrice;
	private String callbackUrl;
	
	@Override
	public String toString() {
		return "PaymentInformationDTO [orderId=" + orderId + ", orderPrice=" + orderPrice + ", callbackUrl="
				+ callbackUrl + "]";
	}
	
	
}
