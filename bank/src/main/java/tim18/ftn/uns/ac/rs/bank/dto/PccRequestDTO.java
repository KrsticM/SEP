package tim18.ftn.uns.ac.rs.bank.dto;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PccRequestDTO {
	private String panNumber;
	private String cardHolder;
	private Integer cvv;
	private String mm;
	private String yy;
	private Integer acquirerOrderId;
	private Date acquirerTimestamp;
	private Double amount;
}
