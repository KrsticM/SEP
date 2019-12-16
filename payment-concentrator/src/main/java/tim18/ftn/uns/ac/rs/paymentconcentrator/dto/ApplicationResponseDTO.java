package tim18.ftn.uns.ac.rs.paymentconcentrator.dto;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tim18.ftn.uns.ac.rs.paymentconcentrator.model.Application;
import tim18.ftn.uns.ac.rs.paymentconcentrator.model.PaymentMethod;

@Getter
@Setter
@NoArgsConstructor
public class ApplicationResponseDTO {
	private Integer id;
	private String name;
	private String token;
	private Integer userId;
	private Set<PaymentMethod> methods;
	
	
	public ApplicationResponseDTO(Application app) {
		super();
		this.id = app.getId();
		this.name = app.getName();
		this.token = app.getToken();
		this.userId = app.getUser().getId();
		this.methods = new HashSet<>();
		for(PaymentMethod pm : app.getMethods()) {
			this.methods.add(pm); // Maybe DTO later
		}
	}
}
