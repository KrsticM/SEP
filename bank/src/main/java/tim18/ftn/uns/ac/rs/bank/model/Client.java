package tim18.ftn.uns.ac.rs.bank.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Client {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false, unique = true)
	private String panNumber;
	
	@Column(nullable = false)
	private String cardHolder;
	
	@Column(nullable = false)
	private Integer cvv;
	
	@Column(nullable = false)
	private String expirationDate;
	
	@Column
	private String merchantId;

	@Column
	private String merchantPassword;
	
	@Column
	private Double availableFunds;
	
	@Column
	private Double reservedFunds;	
	
}
