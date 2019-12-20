package tim18.ftn.uns.ac.rs.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tim18.ftn.uns.ac.rs.bank.model.Client;

public interface ClientRepository  extends JpaRepository<Client, Integer>{

}
