package tim18.ftn.uns.ac.rs.paypalpayment.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tim18.ftn.uns.ac.rs.paypalpayment.model.PaypalSubscription;

public interface PaypalSubscriptionRepository extends JpaRepository<PaypalSubscription, Integer>{
	Optional<PaypalSubscription> findByPlanId(String planId);
	Optional<PaypalSubscription> findBySubscriptionId(String subscriptionId);
	List<PaypalSubscription> findAllByStatus(String status);
}

