package tim18.ftn.uns.ac.rs.bank.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ClientDTO {
	private String panNumber;
	private String cardHolder;
	private Integer cvv;
	private String mm;
	private String yy;



}
