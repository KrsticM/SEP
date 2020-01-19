package tim18.ftn.uns.ac.rs.bitcoinpayment.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tim18.ftn.uns.ac.rs.bitcoinpayment.dto.MerchantInfoDTO;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Merchant {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false)
	private String coingateToken;
	
	@Column(nullable = false)
	private Integer applicationId;  
	
	// OneToMany 
	@Getter(AccessLevel.NONE)
	@OneToMany(mappedBy = "merchant", fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<Order> orders;
	
	public Set<Order> getOrders() {
		if (orders == null) {
			orders = new HashSet<>();
		}
		return orders;
	}
	
	public Merchant(MerchantInfoDTO merchantDTO, Integer applicationId) {
		this.coingateToken = merchantDTO.getCoingateToken();
		this.applicationId = applicationId;
	}
	
}
