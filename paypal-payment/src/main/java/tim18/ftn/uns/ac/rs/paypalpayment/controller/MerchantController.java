package tim18.ftn.uns.ac.rs.paypalpayment.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tim18.ftn.uns.ac.rs.paypalpayment.dto.MerchantRegisterDTO;
import tim18.ftn.uns.ac.rs.paypalpayment.model.Merchant;
import tim18.ftn.uns.ac.rs.paypalpayment.service.MerchantService;

@RestController
@RequestMapping("/merchant")
public class MerchantController {
	@Autowired
	private MerchantService merchantService;

	@RequestMapping(value = "/addOrUpdate/{appId}", method = RequestMethod.POST)
	public ResponseEntity<Merchant> addOrUpdateConfig(@PathVariable Integer appId, @RequestBody MerchantRegisterDTO registrationDTO) {
		System.out.println("Registering of merchant");
		Merchant merchant = merchantService.registerMerchant(appId, registrationDTO);
		return ResponseEntity.ok(merchant);
	}
}
