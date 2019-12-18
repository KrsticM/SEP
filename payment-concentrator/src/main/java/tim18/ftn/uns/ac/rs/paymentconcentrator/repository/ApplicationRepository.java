package tim18.ftn.uns.ac.rs.paymentconcentrator.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import tim18.ftn.uns.ac.rs.paymentconcentrator.model.Application;

public interface ApplicationRepository extends JpaRepository<Application, Integer>{

	Optional<Application> findByToken(String appApiKey);

}
