package tim18.ftn.uns.ac.rs.pcc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tim18.ftn.uns.ac.rs.pcc.model.BankInfo;


public interface BankInfoRepository  extends JpaRepository<BankInfo, Integer> {

	Optional<BankInfo> findByBankIdFromPan(String bankId);

}
