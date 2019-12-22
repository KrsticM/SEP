package tim18.ftn.uns.ac.rs.paymentconcentrator.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class OrderInformationDTO {
	private Integer id;
	private Double price;
	private String callbackUrl;
	
	@Override
	public String toString() {
		return "PaymentInformationDTO [orderId=" + id + ", orderPrice=" + price + ", callbackUrl="
				+ callbackUrl + "]";
	}
	
	
}
