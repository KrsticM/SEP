package tim18.ftn.uns.ac.rs.bitcoinpayment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CompletePaymentDTO {

	private Integer id;
	private String order_id;
	private String status;
	private Double price_amount;
	private String price_currency;
	private String receive_currency;
	private Double receive_amount;
	private Double pay_amount;
	private String pay_currency;
	private String created_at;
	
	@Override
	public String toString() {
		return "CompletePaymentDTO [id=" + id + ", order_id=" + order_id + ", status=" + status + ", price_amount="
				+ price_amount + ", price_currency=" + price_currency + ", receive_currency=" + receive_currency
				+ ", receive_amount=" + receive_amount + ", pay_amount=" + pay_amount + ", pay_currency=" + pay_currency
				+ ", created_at=" + created_at + "]";
	}
	
	
}
