package tim18.ftn.uns.ac.rs.bank.dto;

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
	private String merchantOrderId;
	private Date merchantTimestamp;
	private String successUrl;
	private String failedUrl;
	private String errorUrl;	
	private String paymentId;
	private String paymentUrl;
	
	// TODO: Obrisati
	@Override
	public String toString() {
		return "PaymentRequestDTO [merchantId=" + merchantId + ", merchantPassword=" + merchantPassword + ", amount="
				+ amount + ", merchantOrderId=" + merchantOrderId + ", merchantTimestamp=" + merchantTimestamp
				+ ", successUrl=" + successUrl + ", failedUrl=" + failedUrl + ", errorUrl=" + errorUrl + ", paymentId="
				+ paymentId + ", paymentUrl=" + paymentUrl + "]";
	}
	
	
	
}
