package tim18.ftn.uns.ac.rs.bitcoinpayment.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BTCOrder implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7878452072909611311L;
	private String order_id;
	private Double price_amount;
	private String price_currency = "BTC";
	private String receive_currency = "DO_NOT_CONVERT";
	private String title;
	private String description;
	private String callback_url;
	private String cancel_url;
	private String success_url;
	private String payment_url;
	private String token;


}
