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
	private String callbackURL;
	
	@Column
	private String planId;
	
	@Column
	private Double monthlyPrice;
	
	@Column
	private Integer orderScienceCenterId;


	@Column
	private String approveURL;

	@Column
	private String status;

	@Column
	private Integer merchant;
	
	@Column
	private String subscriber;
	
	@Column
	private Integer durationMonths;

	@Column
	private LocalDateTime createTimestamp;

	@Column
	private LocalDateTime transactionCheckTimestamp;
	

	public PaypalSubscription() {
		super();
	}
	
	public PaypalSubscription(Integer merchantId, String subscriptionId, String planId, String approveURL, String status) {
		this.merchant = merchantId;
		this.planId = planId;
		this.subscriptionId = subscriptionId;
		this.approveURL = approveURL;
		this.status = status;
		this.createTimestamp = LocalDateTime.now();
	}

	@Override
	public String toString() {
		return "PaypalSubscription [id=" + id + ", subscriptionId=" + subscriptionId + ", approveURL=" + approveURL
				+ ", status=" + status + ", merchant=" + merchant + ", createTimestamp=" + createTimestamp + "]";
	}
}
