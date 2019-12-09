package tim18.ftn.uns.ac.rs.paymentconcentrator.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tim18.ftn.uns.ac.rs.paymentconcentrator.config.StringConverter;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "applications")
public class Application {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	//@Convert(converter = StringConverter.class)
	@Column(nullable = false, unique = true)
	private String token;
	
	@Getter(AccessLevel.NONE)
	@ManyToMany(fetch = FetchType.EAGER)
	protected Set<PaymentMethod> methods; 

	public Set<PaymentMethod> getMethods() {
		if (methods == null) {
			methods = new HashSet<>();
		}
		return methods;
	}
	

}
