package tim18.ftn.uns.ac.rs.bank.dto;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PccResponseDTO {
	private Boolean isAuthentificated;
	private Boolean isTransactionAutorized;
	private Integer acquirerOrderId;
	private Date acquirerTimestamp;
}
