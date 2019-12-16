package tim18.ftn.uns.ac.rs.paymentconcentrator.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Where(clause="active=true")
@Table(name = "applications")
public class Application {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false)
	private String name;
	
	// @Convert(converter = StringConverter.class)
	@Column(nullable = false, unique = true)
	private String token;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private User user; // Naucna centrala kojoj ovaj casopis pripada

	@Getter(AccessLevel.NONE)
	@ManyToMany(fetch = FetchType.EAGER)
	private Set<PaymentMethod> methods;

	public Set<PaymentMethod> getMethods() {
		if (methods == null) {
			methods = new HashSet<>();
		}
		return methods;
	}
	
	@Column(nullable = false)
	private Boolean active;

}
