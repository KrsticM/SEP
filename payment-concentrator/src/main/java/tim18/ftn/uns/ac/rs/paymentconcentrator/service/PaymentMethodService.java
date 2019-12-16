package tim18.ftn.uns.ac.rs.paymentconcentrator.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim18.ftn.uns.ac.rs.paymentconcentrator.dto.ApplicationResponseDTO;
import tim18.ftn.uns.ac.rs.paymentconcentrator.exceptions.NotFoundException;
import tim18.ftn.uns.ac.rs.paymentconcentrator.model.Application;
import tim18.ftn.uns.ac.rs.paymentconcentrator.model.PaymentMethod;
import tim18.ftn.uns.ac.rs.paymentconcentrator.repository.PaymentMethodRepository;

@Service
public class PaymentMethodService {

	@Autowired
	private PaymentMethodRepository paymentMethodRepository;

	@Autowired
	private ServiceService serviceService;

	@Autowired
	private ApplicationService applicationService; 
	
	/**
	 * Finds {@link PaymentMethod}. named @param name. If no payment method with
	 * that name is found, creates new payment method.
	 * 
	 * @param name
	 * @return
	 */
	public PaymentMethod findByName(String name) {
		PaymentMethod ret = null;
		Optional<PaymentMethod> paymentMethod = paymentMethodRepository.findByName(name);
		if (paymentMethod.isPresent()) {
			ret = paymentMethod.get();
		} else {
			ret = savePaymentMethod(name);
		}
		return ret;
	}

	private PaymentMethod savePaymentMethod(String name) {
		PaymentMethod paymentMethod = new PaymentMethod();
		paymentMethod.setName(name);
		return paymentMethodRepository.save(paymentMethod);
	}

	public ApplicationResponseDTO addPaymentMethod(String method, Integer userId, Integer appId) throws NotFoundException {
		List<String> services = serviceService.getAll();

		if (!services.contains(method)) {
			throw new NotFoundException(method, PaymentMethod.class.getSimpleName());
		}

		Application app = applicationService.findById(appId);
		if(!app.getUser().getId().equals(userId)) {
			throw new NotFoundException(appId, Application.class.getSimpleName());
		}	
		
		PaymentMethod paymentMethod = findByName(method);
		app.getMethods().add(paymentMethod);
		return new ApplicationResponseDTO(applicationService.saveApp(app));

	}
	
	public ApplicationResponseDTO removePaymentMethod(String method, Integer userId, Integer appId) throws NotFoundException {

		Application app = applicationService.findById(appId);
		Set<PaymentMethod> paymentMethods = app.getMethods();
		PaymentMethod paymentMethod = findByName(method);
		
		if(!paymentMethods.contains(paymentMethod)) {
			throw new NotFoundException(method, PaymentMethod.class.getSimpleName());
		}
		
		if(!app.getUser().getId().equals(userId)) {
			throw new NotFoundException(appId, Application.class.getSimpleName());
		}		

		app.getMethods().remove(paymentMethod);
		return new ApplicationResponseDTO(applicationService.saveApp(app));
		
	}

	public List<PaymentMethod> getPaymentMethods(Integer userId, Integer appId) throws NotFoundException {
		Application app = applicationService.findById(appId);
		
		if(!app.getUser().getId().equals(userId)) {
			throw new NotFoundException(appId, Application.class.getSimpleName());
		}		
		
		List<PaymentMethod> ret = new ArrayList<PaymentMethod>();
		for(PaymentMethod pm: app.getMethods()) {
			ret.add(pm);
		}
		return ret;
	}

}
