package tim18.ftn.uns.ac.rs.sciencecenter.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim18.ftn.uns.ac.rs.sciencecenter.dto.ItemDTO;
import tim18.ftn.uns.ac.rs.sciencecenter.model.Item;
import tim18.ftn.uns.ac.rs.sciencecenter.repository.ItemRepository;

@Service
public class ItemService {

	@Autowired
	private ItemRepository itemRepository;
	
	public List<ItemDTO> getItems() {
		List<Item> list = itemRepository.findAll();
		List<ItemDTO> ret = new ArrayList<ItemDTO>();
		for(Item i : list) {
			ret.add(new ItemDTO(i));
		}
		return ret;
	}

}
