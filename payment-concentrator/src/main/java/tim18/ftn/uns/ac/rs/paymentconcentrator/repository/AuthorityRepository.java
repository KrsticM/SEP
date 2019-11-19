package tim18.ftn.uns.ac.rs.paymentconcentrator.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tim18.ftn.uns.ac.rs.paymentconcentrator.model.Authority;	

public interface AuthorityRepository extends JpaRepository<Authority, Integer>{
  Optional<Authority> findByName(String name);
}