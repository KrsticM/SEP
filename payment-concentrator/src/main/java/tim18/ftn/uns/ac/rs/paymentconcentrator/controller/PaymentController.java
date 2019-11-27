package tim18.ftn.uns.ac.rs.paymentconcentrator.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import tim18.ftn.uns.ac.rs.paymentconcentrator.dto.form.Field;
import tim18.ftn.uns.ac.rs.paymentconcentrator.service.ServiceService;

@RestController
@RequestMapping("/payment")
public class PaymentController {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ServiceService serviceService;
	
	@GetMapping("/{method}")
	public String test(@PathVariable("method") String method) {

		String result = restTemplate.getForObject("https://" + method + "/printMe", String.class);

		return result;		
	}
	
	@GetMapping("/form/{method}")
	public List<Field> getFormData(@PathVariable("method") String method) {
		@SuppressWarnings("unchecked")
		List<Field> ret = restTemplate.getForObject("http://" + method + "/form/data", ArrayList.class);
		return ret;
	}

	@GetMapping("/all-services")
	public List<String> getAll() {
		return serviceService.getAll();
	}
	
}
