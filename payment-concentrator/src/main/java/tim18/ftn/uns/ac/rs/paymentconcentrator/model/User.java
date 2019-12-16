package tim18.ftn.uns.ac.rs.paymentconcentrator.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String firstName;

	@Column(nullable = false)
	private String lastName;

	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false, unique = true)
	private String website;

	@Column
	private String organizationName;

	@Getter(AccessLevel.NONE)
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<Application> applications;
	
	public Set<Application> getApplications() {
		if (applications == null) {
			applications = new HashSet<>();
		}
		return applications;
	}
	
	@Getter(AccessLevel.NONE)
	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Authority> authorities;

	public Set<Authority> getUserAuthorities() {
		if (authorities == null) {
			authorities = new HashSet<>();
		}
		return authorities;
	}

	public User(User user) {
		this.id = user.getId();
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.authorities = user.getUserAuthorities();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.organizationName = user.getOrganizationName();
	}

}
