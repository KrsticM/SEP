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
@Table(name = "paypal_plans")
public class PaypalPlan {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column
	private String planId;

	@Column
	private String productId;
	
	@Column
	private String name;
	
	@Column
	private String status;

	@Column
	private String createTime;

	@Column
	private LocalDateTime createTimestamp;

	@Column
	private Integer merchant;
	
	public PaypalPlan() {
		super();
	}
	
	public PaypalPlan(Integer merchantId, String planId, String productId, String name, String status) {
		this.merchant = merchantId;
		this.planId = planId;
		this.productId = productId;
		this.name = name;
		this.status = status;
		this.createTimestamp = LocalDateTime.now();
	}
}
