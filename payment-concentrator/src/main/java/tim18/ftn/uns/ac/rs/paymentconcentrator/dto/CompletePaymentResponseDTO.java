package tim18.ftn.uns.ac.rs.paymentconcentrator.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class CompletePaymentResponseDTO {
	private Integer order_id;
	private String status;
}
