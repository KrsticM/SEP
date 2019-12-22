package tim18.ftn.uns.ac.rs.cardpayment.dto;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PaymentRequestDTO {

	private String merchantId;
	private String merchantPassword;
	private Double amount;
	private Integer merchantOrderId;
	private Date merchantTimestamp;
	private String successUrl;
	private String failedUrl;
	private String errorUrl;	
	private String callbackUrl;
	
}
