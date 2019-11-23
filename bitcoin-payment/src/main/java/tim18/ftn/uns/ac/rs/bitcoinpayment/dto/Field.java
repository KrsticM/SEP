package tim18.ftn.uns.ac.rs.bitcoinpayment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//Lambok annotations
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Field {
	
	private String name;
	private String label;
	private Boolean required;
	private String type; // TODO: videcemo da li ce ostati string	
}
