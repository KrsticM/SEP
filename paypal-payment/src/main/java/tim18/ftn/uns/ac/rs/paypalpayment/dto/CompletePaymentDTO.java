package tim18.ftn.uns.ac.rs.paypalpayment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CompletePaymentDTO {

	private Integer order_id;
	private String status;
}
