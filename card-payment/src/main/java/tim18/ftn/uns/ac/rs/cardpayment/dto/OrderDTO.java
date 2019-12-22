package tim18.ftn.uns.ac.rs.cardpayment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class OrderDTO {

	private Integer orderIdScienceCenter;
	private Double price;
	private String callbackUrl;
	
	@Override
	public String toString() {
		return "OrderDTO [orderIdScienceCenter=" + orderIdScienceCenter + ", price=" + price + ", callbackUrl="
				+ callbackUrl + "]";
	}
	
	
}
