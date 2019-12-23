package tim18.ftn.uns.ac.rs.paypalpayment.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tim18.ftn.uns.ac.rs.paypalpayment.dto.Field;
import tim18.ftn.uns.ac.rs.paypalpayment.dto.MerchantRegisterDTO;
import tim18.ftn.uns.ac.rs.paypalpayment.model.Merchant;
import tim18.ftn.uns.ac.rs.paypalpayment.service.MerchantService;

@RestController
public class MerchantController {
	@Autowired
	private MerchantService merchantService;

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<Merchant> register(@RequestBody @Valid MerchantRegisterDTO registrationDTO) {
		Merchant merchant = merchantService.registerMerchant(registrationDTO);
		return ResponseEntity.ok(merchant);
	}
}
