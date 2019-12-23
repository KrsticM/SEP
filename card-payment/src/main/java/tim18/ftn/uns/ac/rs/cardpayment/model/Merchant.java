package tim18.ftn.uns.ac.rs.cardpayment.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tim18.ftn.uns.ac.rs.cardpayment.dto.MerchantInfoDTO;

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
	private String merchantId;

	@Column(nullable = false)
	private String merchantPassword;
	
	@Column(nullable = false)
	private Integer applicationId;  
	
	public Merchant(MerchantInfoDTO merchantDTO, Integer applicationId) {
		this.merchantId = merchantDTO.getMerchantId();
		this.merchantPassword = merchantDTO.getMerchantPassword();
		this.applicationId = applicationId;
	}
	
	
	
}
