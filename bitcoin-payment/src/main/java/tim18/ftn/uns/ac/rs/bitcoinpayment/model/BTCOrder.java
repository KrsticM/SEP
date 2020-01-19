package tim18.ftn.uns.ac.rs.bitcoinpayment.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@NoArgsConstructor
@Entity
@ToString
public class BTCOrder implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7878452072909611311L;
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column
	private String order_id;
	
	@Column
	private Double price_amount;
	
	@Column
	private String price_currency = "USD";
	
	@Column
	private String receive_currency = "DO_NOT_CONVERT";
	
	@Column
	private String title;
	
	@Column
	private String description;
	
	@Column
	private String callback_url;
	
	@Column
	private String cancel_url;
	
	@Column
	private String success_url;
	
	@Column
	private String payment_url;
	
	@Column
	private String token;


}
