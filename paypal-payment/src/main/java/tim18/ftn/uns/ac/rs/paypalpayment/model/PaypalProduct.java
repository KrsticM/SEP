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
@Table(name = "paypal_products")
public class PaypalProduct {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column
	private String productId;

	@Column
	private String name;

	@Column
	private Integer merchant;

	@Column
	private LocalDateTime createTimestamp;
	

	public PaypalProduct() {
		super();
	}
	
	public PaypalProduct(Integer merchantId, String productId, String name) {
		this.merchant = merchantId;
		this.productId = productId;
		this.name = name;
		this.createTimestamp = LocalDateTime.now();
	}

	@Override
	public String toString() {
		return "PaypalProduct [id=" + id + ", productId=" + productId + ", name=" + name + ", merchant=" + merchant
				+ ", createTimestamp=" + createTimestamp + "]";
	}
}
