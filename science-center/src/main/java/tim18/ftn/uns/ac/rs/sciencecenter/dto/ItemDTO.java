package tim18.ftn.uns.ac.rs.sciencecenter.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tim18.ftn.uns.ac.rs.sciencecenter.model.Item;

@Getter
@Setter
@NoArgsConstructor
public class ItemDTO {
	
	private Integer id;
	private String name;
	private Double price;
	private Integer merchantId;
	private String imageUrl;
	
	public ItemDTO(Item item) {
		this.id = item.getId();
		this.name = item.getName();
		this.price = item.getPrice();
		this.merchantId = item.getMerchant().getId();
		this.imageUrl = item.getImageUrl();
	}

}
