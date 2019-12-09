package tim18.ftn.uns.ac.rs.paymentconcentrator.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim18.ftn.uns.ac.rs.paymentconcentrator.exceptions.NotFoundException;
import tim18.ftn.uns.ac.rs.paymentconcentrator.model.PaymentMethod;
import tim18.ftn.uns.ac.rs.paymentconcentrator.model.User;
import tim18.ftn.uns.ac.rs.paymentconcentrator.repository.PaymentMethodRepository;

@Service
public class PaymentMethodService {

	@Autowired
	private PaymentMethodRepository paymentMethodRepository;

	@Autowired
	private ServiceService serviceService;

	@Autowired
	private PaymentMethodService paymentMethodService;

	@Autowired
	private UserService userService;

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

	public User addPaymentMethod(String method, Integer userId) throws NotFoundException {
		List<String> services = serviceService.getAll();

		if (!services.contains(method)) {
			throw new NotFoundException(method, PaymentMethod.class.getSimpleName());
		}

		PaymentMethod paymentMethod = paymentMethodService.findByName(method);
		User user = userService.findUserById(userId);

		//user.getMethods().add(paymentMethod);
		return userService.saveUser(user);

	}
	
	public User removePaymentMethod(String method, Integer userId) throws NotFoundException {
		/*User user = userService.findUserById(userId);
		Set<PaymentMethod> paymentMethods = user.getMethods();
		PaymentMethod paymentMethod = paymentMethodService.findByName(method);
		
		if(!paymentMethods.contains(paymentMethod)) {
			throw new NotFoundException(method, PaymentMethod.class.getSimpleName());
		}

		//user.getMethods().remove(paymentMethod);
		return userService.saveUser(user);*/
		return null;
		
	}

}
