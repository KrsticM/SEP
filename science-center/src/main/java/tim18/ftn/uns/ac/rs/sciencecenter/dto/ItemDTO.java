package tim18.ftn.uns.ac.rs.sciencecenter.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tim18.ftn.uns.ac.rs.sciencecenter.model.Item;

@Getter
@Setter
@NoArgsConstructor
public class ItemDTO {
	
	private String name;
	private Double price;
	private String api_key;
	private Integer merchantId;
	
	public ItemDTO(Item item) {
		this.name = item.getName();
		this.price = item.getPrice();
		this.api_key = item.getMerchant().getApi_key();
		this.merchantId = item.getMerchant().getId();
	}

}
