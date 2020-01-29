package tim18.ftn.uns.ac.rs.bitcoinpayment.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "orders")
public class Order {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column
	private Integer orderIdScienceCenter;
	
	@Column
	private Double price;
	
	@Column
	private String callbackUrl;
	
	@Column
	private OrderStatus status;
	
	@Column
	private Integer ticks;
	
	@Column
	private Integer orderIdCoinGate;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn
	private Merchant merchant;

	@Override
	public String toString() {
		return "Order [id=" + id + ", orderIdScienceCenter=" + orderIdScienceCenter + ", price=" + price
				+ ", callbackUrl=" + callbackUrl + ", status=" + status + "]";
	}
	
	
	
}
