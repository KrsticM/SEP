package tim18.ftn.uns.ac.rs.paypalpayment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class PaypalPaymentApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaypalPaymentApplication.class, args);
	}
	
	@Configuration
	class RestTemplateConfig {

		// Create a bean for restTemplate to call services
		@Bean
		public RestTemplate restTemplate() {
			return new RestTemplate();
		}
	}


}
