package tim18.ftn.uns.ac.rs.paypalpayment.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SubscriptionPlanDTO {

	@NotBlank(message = "Please, enter subscription duration.")
	private Integer subscriptionDuration;

}
