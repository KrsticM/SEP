package tim18.ftn.uns.ac.rs.bitcoinpayment.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tim18.ftn.uns.ac.rs.bitcoinpayment.dto.MerchantInfoDTO;


@RestController
@RequestMapping("/merchant")
public class MerchantController {

	
	@RequestMapping(value = "/addOrUpdate/{appId}", method = RequestMethod.POST)
	public String addOrUpdateConfig(@PathVariable Integer appId, @RequestBody MerchantInfoDTO merchantInfoDTO) {
		System.err.println("Merchant coingateToken: " + merchantInfoDTO.getCoingateToken());
		return null;
	}
	
}
