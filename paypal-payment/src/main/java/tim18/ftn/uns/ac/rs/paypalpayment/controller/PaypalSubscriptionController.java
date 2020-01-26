package tim18.ftn.uns.ac.rs.paypalpayment.controller;

import java.io.UnsupportedEncodingException;

import javax.validation.Valid;

import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tim18.ftn.uns.ac.rs.paypalpayment.dto.OrderDTO;
import tim18.ftn.uns.ac.rs.paypalpayment.model.Order;
import tim18.ftn.uns.ac.rs.paypalpayment.model.PaypalPlan;
import tim18.ftn.uns.ac.rs.paypalpayment.model.PaypalProduct;
import tim18.ftn.uns.ac.rs.paypalpayment.model.PaypalSubscription;
import tim18.ftn.uns.ac.rs.paypalpayment.service.BillingPlanService;
import tim18.ftn.uns.ac.rs.paypalpayment.service.SubscriptionService;

@RestController
public class PaypalSubscriptionController {
	
	Logger logger = LoggerFactory.getLogger(PaypalPaymentController.class);

	@Autowired
	BillingPlanService billingPlanService;
	
	@Autowired
	SubscriptionService subscriptionService;
	
	@RequestMapping(value = "/createPlan", method = RequestMethod.POST)
	public ResponseEntity<PaypalPlan> createOrder(@RequestBody @Valid OrderDTO orderDTO) throws UnsupportedEncodingException{
		Order order = orderDTO.createOrder();
		PaypalProduct product = billingPlanService.createProduct(order);
		PaypalPlan plan = billingPlanService.createBillingPlan(product, order);
		System.out.println("Product: \n" + product.toString());
		System.out.println("Billing plan: \n" + plan.toString());
		return ResponseEntity.ok(plan);
	}

	@RequestMapping(value = "/subscribe/{planId}", method = RequestMethod.POST)
	public ResponseEntity<PaypalSubscription> subscribeForPlan(@PathVariable Integer planId) throws UnsupportedEncodingException{
		PaypalPlan plan = billingPlanService.getPlan(planId);
		
		PaypalSubscription createdSubscription = subscriptionService.createSubscription(plan);
		System.out.println("Billing plan: \n" + plan.toString());
		System.out.println("Response: \n" + createdSubscription.toString());

		return ResponseEntity.ok(createdSubscription);
	}
}
