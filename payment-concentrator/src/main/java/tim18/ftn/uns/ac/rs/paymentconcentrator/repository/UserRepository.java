package tim18.ftn.uns.ac.rs.paymentconcentrator.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tim18.ftn.uns.ac.rs.paymentconcentrator.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
  Optional<User> findByEmail(String email);
}