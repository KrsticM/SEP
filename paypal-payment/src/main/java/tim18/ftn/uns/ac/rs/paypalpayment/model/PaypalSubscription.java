package tim18.ftn.uns.ac.rs.paypalpayment.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "paypal_subscriptions")
public class PaypalSubscription {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column
	private String subscriptionId;

	@Column
	private String approveURL;

	@Column
	private String status;

	@Column
	private Integer merchant;

	@Column
	private LocalDateTime createTimestamp;
	

	public PaypalSubscription() {
		super();
	}
	
	public PaypalSubscription(Integer merchantId, String subscriptionId, String approveURL, String status) {
		this.merchant = merchantId;
		this.subscriptionId = subscriptionId;
		this.approveURL = approveURL;
		this.status = status;
		this.createTimestamp = LocalDateTime.now();
	}
}
