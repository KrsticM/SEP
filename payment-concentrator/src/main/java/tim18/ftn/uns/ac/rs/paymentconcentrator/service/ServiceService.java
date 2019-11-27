package tim18.ftn.uns.ac.rs.paymentconcentrator.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

@Service
public class ServiceService {

	@Autowired
	private DiscoveryClient discoveryClient;
	
	public List<String> getAll() {
		List<String> allServices = discoveryClient.getServices();
		allServices.remove("payment-concentrator"); // ovo nije servis placanja
		return allServices;
	}
}
