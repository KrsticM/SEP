package tim18.ftn.uns.ac.rs.bitcoinpayment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class OrderInfoDTO {

	private Integer id;
	private String status;
	private String price_currency;
	private String price_amount;
	private String receive_currency;
	private String receive_amount;
	private String created_at;
	private String expire_at;
	private String payment_address;
	private String order_id;
	private String underpaid_amount;
	private String overpaid_amount;
	private Boolean is_refundable;
	private String payment_url;
}