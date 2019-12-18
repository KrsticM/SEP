package tim18.ftn.uns.ac.rs.sciencecenter.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import tim18.ftn.uns.ac.rs.sciencecenter.dto.ItemDTO;
import tim18.ftn.uns.ac.rs.sciencecenter.dto.PaymentRequestDTO;
import tim18.ftn.uns.ac.rs.sciencecenter.exceptions.NotFoundException;
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
	public String  makePayment(@RequestBody PaymentRequestDTO paymentRequestDTO, HttpServletResponse httpServletResponse) throws NotFoundException {
		System.out.println(paymentRequestDTO);
		// Pretpostavka: Svi artikli bi trebalo da pripadaju jednom prodavcu
		String merchantApiKey = "";
		for(Integer id : paymentRequestDTO.getIds()) {
			merchantApiKey = itemService.getItem(id).getMerchant().getApi_key();
			break; // Za sve artikle je isti
		}
		// Mozda order
		
	    // return new ModelAndView("redirect:" + "http://localhost:8762/payment-concentrator/choosePaymentMethod/" + merchantApiKey);
		return "http://localhost:8762/payment-concentrator/choosePaymentMethod/" + merchantApiKey;
	}
	
}
