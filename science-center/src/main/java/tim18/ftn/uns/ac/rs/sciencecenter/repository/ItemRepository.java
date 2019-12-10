package tim18.ftn.uns.ac.rs.sciencecenter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tim18.ftn.uns.ac.rs.sciencecenter.model.Item;

public interface ItemRepository extends JpaRepository<Item, Integer>{

}
