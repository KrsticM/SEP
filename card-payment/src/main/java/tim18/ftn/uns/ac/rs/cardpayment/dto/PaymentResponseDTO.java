package tim18.ftn.uns.ac.rs.cardpayment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PaymentResponseDTO {
	private String paymentId;
	private String paymentUrl;
}
