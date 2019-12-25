package tim18.ftn.uns.ac.rs.paypalpayment.model;

import java.time.LocalDateTime;

import javax.persistence.*;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column
	private String paypalOrderId;

	@Column
	private LocalDateTime createTimestamp;

	@Column
	private String createTime;

	@Column
	private String updateTime;

	@Column
	private String payer;

	@Column
	private Double price;

	@Column
	private Boolean executed;

	@Column
	private Integer merchant;

	@Column
	private String callbackUrl;

	public Order() {
		super();
	}

	public Order(Integer merchantId, String payer, Double price, String callbackUrl) {
		this.merchant = merchantId;
		this.payer = payer;
		this.price = price;
		this.callbackUrl = callbackUrl;
		this.executed = false;
		this.createTimestamp = LocalDateTime.now();
	}
}