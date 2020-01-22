package tim18.ftn.uns.ac.rs.paypalpayment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tim18.ftn.uns.ac.rs.paypalpayment.model.PaypalSubscription;

public interface PaypalSubscriptionRepository extends JpaRepository<PaypalSubscription, Integer>{
}

