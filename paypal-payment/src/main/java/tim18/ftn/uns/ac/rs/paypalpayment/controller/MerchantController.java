package tim18.ftn.uns.ac.rs.paypalpayment.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tim18.ftn.uns.ac.rs.paypalpayment.dto.MerchantRegisterDTO;
import tim18.ftn.uns.ac.rs.paypalpayment.service.MerchantService;

@RestController
@RequestMapping("/merchant")
public class MerchantController {
	
	Logger logger = LoggerFactory.getLogger(MerchantController.class);
	
	@Autowired
	private MerchantService merchantService;

	@RequestMapping(value = "/addOrUpdate/{appId}", method = RequestMethod.POST)
	public String addOrUpdateConfig(@PathVariable Integer appId, @RequestBody MerchantRegisterDTO registrationDTO) {
		System.out.println("Registering of merchant");
		logger.info("Registering of merchant");
		merchantService.registerMerchant(appId, registrationDTO);
		return "http://localhost:8200/view/successfulConfig";
	}
}
