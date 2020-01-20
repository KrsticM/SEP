package tim18.ftn.uns.ac.rs.pcc.dto;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class PccResponseDTO {
	private Boolean isAuthentificated;
	private Boolean isTransactionAutorized;
	private Integer acquirerOrderId;
	private Date acquirerTimestamp;
}
