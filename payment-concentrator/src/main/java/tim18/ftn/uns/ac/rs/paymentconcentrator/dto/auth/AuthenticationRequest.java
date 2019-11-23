package tim18.ftn.uns.ac.rs.paymentconcentrator.dto.auth;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AuthenticationRequest {
	@NotBlank(message = "Please, enter your email.")
	protected String email;

	@NotBlank(message = "Please, enter your password.")
	protected String password;
}