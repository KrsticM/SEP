package tim18.ftn.uns.ac.rs.sciencecenter.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaymentRequestDTO {
	
	private List<Integer> ids = new ArrayList<Integer>();

	@Override
	public String toString() {
		return "PaymentRequestDTO [ids=" + ids + "]";
	}

}
