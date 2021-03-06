package tim18.ftn.uns.ac.rs.bitcoinpayment.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tim18.ftn.uns.ac.rs.bitcoinpayment.dto.MerchantInfoDTO;
import tim18.ftn.uns.ac.rs.bitcoinpayment.service.MerchantService;


@RestController
@RequestMapping("/merchant")
public class MerchantController {

	Logger logger = LoggerFactory.getLogger(MerchantController.class);
	
	@Autowired
	private MerchantService merchantService;
	
	@RequestMapping(value = "/addOrUpdate/{appId}", method = RequestMethod.POST)
	public String addOrUpdateConfig(@PathVariable Integer appId, @RequestBody MerchantInfoDTO merchantInfoDTO) {
		logger.info("Adding or updating config for merchant with coingate token" + merchantInfoDTO.getCoingateToken() + " and application with id " + appId);
		System.err.println("Merchant coingateToken: " + merchantInfoDTO.getCoingateToken());
		return merchantService.addOrUpdateConfig(merchantInfoDTO, appId);
	}
	
}
