package tim18.ftn.uns.ac.rs.bitcoinpayment.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
	
	public Merchant(MerchantInfoDTO merchantDTO, Integer applicationId) {
		this.coingateToken = merchantDTO.getCoingateToken();
		this.applicationId = applicationId;
	}
	
}
