package tim18.ftn.uns.ac.rs.paymentconcentrator.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim18.ftn.uns.ac.rs.paymentconcentrator.dto.ApplicationDTO;
import tim18.ftn.uns.ac.rs.paymentconcentrator.dto.ApplicationResponseDTO;
import tim18.ftn.uns.ac.rs.paymentconcentrator.exceptions.NotFoundException;
import tim18.ftn.uns.ac.rs.paymentconcentrator.model.Application;
import tim18.ftn.uns.ac.rs.paymentconcentrator.model.User;
import tim18.ftn.uns.ac.rs.paymentconcentrator.repository.ApplicationRepository;

@Service
public class ApplicationService {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ApplicationRepository applicationRepository;
	
	public ApplicationResponseDTO addApplication(Integer userId, ApplicationDTO applicationDTO) throws NotFoundException {
		User user = userService.findUserById(userId);
		Application app = new Application();
		app.setName(applicationDTO.getName());
		app.setToken(UUID.randomUUID().toString());
		app.setUser(user);
		app.setActive(true);
		return new ApplicationResponseDTO(applicationRepository.save(app));
	}
	
	public ApplicationResponseDTO removeApplication(Integer userId, Integer appId) throws NotFoundException {
		Optional<Application> app = applicationRepository.findById(appId);
		
		if(!app.isPresent()) {
			throw new NotFoundException(appId, Application.class.getSimpleName());
		}
		
		if(!app.get().getUser().getId().equals(userId)) {
			throw new NotFoundException(appId, Application.class.getSimpleName());
		}
		
		app.get().setActive(false);
		return new ApplicationResponseDTO(applicationRepository.save(app.get()));
	}
	
	public Application saveApp(Application app) {
		return applicationRepository.save(app);
	}
	
	public Application findById(Integer appId) throws NotFoundException {
		Optional<Application> app = applicationRepository.findById(appId);
		
		if(!app.isPresent()) {
			throw new NotFoundException(appId, Application.class.getSimpleName());
		}
		
		return app.get();
	}

	public List<ApplicationResponseDTO> getApplications(Integer userId) throws NotFoundException {
		User user = userService.findUserById(userId);
		List<ApplicationResponseDTO> ret = new ArrayList<ApplicationResponseDTO>();
		for(Application app: user.getApplications()) {
			ret.add(new ApplicationResponseDTO(app));
		}
		return ret;
	}

}
