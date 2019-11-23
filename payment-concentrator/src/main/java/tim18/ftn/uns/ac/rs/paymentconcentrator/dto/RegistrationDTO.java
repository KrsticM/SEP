package tim18.ftn.uns.ac.rs.paymentconcentrator.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import tim18.ftn.uns.ac.rs.paymentconcentrator.model.EnterpriseUser;
import tim18.ftn.uns.ac.rs.paymentconcentrator.model.PersonalUser;

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
	
	private String authority;

	public RegistrationDTO() {

	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public PersonalUser createPersonalUser() {
		PersonalUser ret = new PersonalUser();
		ret.setEmail(this.email);
		ret.setFirstName(this.firstName);
		ret.setLastName(this.lastName);
		ret.setOrganizationName(this.organizationName);
		ret.setWebsite(this.website);
		return ret;
	}

	public EnterpriseUser createEnterpriseUser() {
		EnterpriseUser ret = new EnterpriseUser();
		ret.setEmail(this.email);
		ret.setFirstName(this.firstName);
		ret.setLastName(this.lastName);
		ret.setOrganizationName(this.organizationName);
		ret.setWebsite(this.website);
		return ret;
	}
}