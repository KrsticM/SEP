package tim18.ftn.uns.ac.rs.paymentconcentrator.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim18.ftn.uns.ac.rs.paymentconcentrator.dto.ApplicationResponseDTO;
import tim18.ftn.uns.ac.rs.paymentconcentrator.exceptions.NotFoundException;
import tim18.ftn.uns.ac.rs.paymentconcentrator.model.Application;
import tim18.ftn.uns.ac.rs.paymentconcentrator.model.PaymentMethod;
import tim18.ftn.uns.ac.rs.paymentconcentrator.repository.PaymentMethodRepository;

@Service
public class PaymentMethodService {

	Logger logger = LoggerFactory.getLogger(PaymentMethodService.class);

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
			logger.error("Method " + method + " does not existis.");
			throw new NotFoundException(method, PaymentMethod.class.getSimpleName());
		}

		Application app = applicationService.findById(appId);
		if(!app.getUser().getId().equals(userId)) {
			// TODO: logger.errror
			throw new NotFoundException(appId, Application.class.getSimpleName());
		}	
		
		PaymentMethod paymentMethod = findByName(method);
		app.getMethods().add(paymentMethod);
		logger.info("Method " + method + " is saved for application with id " + appId + " and user with id " + userId);
		return new ApplicationResponseDTO(applicationService.saveApp(app));

	}
	
	public ApplicationResponseDTO removePaymentMethod(String method, Integer userId, Integer appId) throws NotFoundException {
		Application app = applicationService.findById(appId);
		Set<PaymentMethod> paymentMethods = app.getMethods();
		PaymentMethod paymentMethod = findByName(method);
		
		if(!paymentMethods.contains(paymentMethod)) {
			logger.error("Method " + method + " does not existis.");
			throw new NotFoundException(method, PaymentMethod.class.getSimpleName());
		}
		
		if(!app.getUser().getId().equals(userId)) {
			// TODO: logger.errror
			throw new NotFoundException(appId, Application.class.getSimpleName());
		}		

		app.getMethods().remove(paymentMethod);
		logger.info("Method " + method + " is removed for application with id " + appId + " and user with id " + userId);
		return new ApplicationResponseDTO(applicationService.saveApp(app));
		
	}

	public List<PaymentMethod> getPaymentMethods(Integer userId, Integer appId) throws NotFoundException {
		Application app = applicationService.findById(appId);
		
		if(!app.getUser().getId().equals(userId)) {
			// TODO: logger.errror
			throw new NotFoundException(appId, Application.class.getSimpleName());
		}		
		
		List<PaymentMethod> ret = new ArrayList<PaymentMethod>();
		for(PaymentMethod pm: app.getMethods()) {
			ret.add(pm);
		}
		return ret;
	}
	
	public List<PaymentMethod> getPaymentMethods(UUID appApiKey) throws NotFoundException {
		Application app = applicationService.findByApiKey(appApiKey.toString());		
		
		List<PaymentMethod> ret = new ArrayList<PaymentMethod>();
		for(PaymentMethod pm: app.getMethods()) {
			ret.add(pm);
		}
		return ret;
	}

}
