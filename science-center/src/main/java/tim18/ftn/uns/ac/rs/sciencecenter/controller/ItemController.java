package tim18.ftn.uns.ac.rs.sciencecenter.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tim18.ftn.uns.ac.rs.sciencecenter.dto.ItemDTO;
import tim18.ftn.uns.ac.rs.sciencecenter.dto.PaymentRequestDTO;
import tim18.ftn.uns.ac.rs.sciencecenter.service.ItemService;

@RestController
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getItems() {
		List<ItemDTO> ret = itemService.getItems();
		return ResponseEntity.ok(ret);
	}
	
	@RequestMapping(value="/pay", method = RequestMethod.POST)
	public void  makePayment(@RequestBody PaymentRequestDTO paymentRequestDTO, HttpServletResponse httpServletResponse) {
		System.out.println(paymentRequestDTO);
		httpServletResponse.setHeader("Location", "http://localhost:8100/test/test");
	    httpServletResponse.setStatus(302);
	}
}
