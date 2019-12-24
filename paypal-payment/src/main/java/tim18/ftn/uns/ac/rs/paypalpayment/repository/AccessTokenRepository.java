package tim18.ftn.uns.ac.rs.paypalpayment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tim18.ftn.uns.ac.rs.paypalpayment.model.AccessToken;

public interface AccessTokenRepository extends JpaRepository<AccessToken, Integer> {
}