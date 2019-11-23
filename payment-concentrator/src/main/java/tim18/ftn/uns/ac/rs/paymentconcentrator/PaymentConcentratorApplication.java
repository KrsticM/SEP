package tim18.ftn.uns.ac.rs.paymentconcentrator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PaymentConcentratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentConcentratorApplication.class, args);
	}
	

}
