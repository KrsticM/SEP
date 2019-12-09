package tim18.ftn.uns.ac.rs.paymentconcentrator.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tim18.ftn.uns.ac.rs.paymentconcentrator.model.User;

@NoArgsConstructor
@Getter
@Setter
public class RegistrationDTO {

	@NotBlank(message = "Please, enter a firstname.")
	private String firstName;

	@NotBlank(message = "Please, enter a lastname.")
	private String lastName;

	private String organizationName;

	@NotBlank(message = "Please, enter a email.")
	@Email(message = "Email is not in correct format.")
	private String email;

	@NotBlank(message = "Please, enter a website.")
	private String website;

	@NotBlank(message = "Please, enter a password.")
	private String password;

	public User createUser() {
		User ret = new User();
		ret.setEmail(this.email);
		ret.setFirstName(this.firstName);
		ret.setLastName(this.lastName);
		ret.setOrganizationName(this.organizationName);
		ret.setWebsite(this.website);
		return ret;
	}
}