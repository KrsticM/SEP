package tim18.ftn.uns.ac.rs.paymentconcentrator.dto.temp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TokenValidationResponse {
	private Boolean isValid = false;
	private Integer userId;
	private String email;
	private String authorities;
}
