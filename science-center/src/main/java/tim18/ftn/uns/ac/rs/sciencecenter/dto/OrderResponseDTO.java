package tim18.ftn.uns.ac.rs.sciencecenter.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class OrderResponseDTO {
	private Integer id;
	private Integer orderIdScienceCenter;
	private Double price;
	private String callbackUrl;
}
