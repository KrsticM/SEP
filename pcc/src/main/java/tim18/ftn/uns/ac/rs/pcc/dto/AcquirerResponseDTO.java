package tim18.ftn.uns.ac.rs.pcc.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class AcquirerResponseDTO {
	public AcquirerResponseDTO(PccResponseDTO body) {
		this.isAuthentificated = body.getIsAuthentificated();
		this.isTransactionAutorized = body.getIsTransactionAutorized();
	}
	private Boolean isAuthentificated;
	private Boolean isTransactionAutorized;
}
