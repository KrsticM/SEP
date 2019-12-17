package tim18.ftn.uns.ac.rs.pcgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import tim18.ftn.uns.ac.rs.pcgateway.filter.authentification.AuthenticationFilter;


@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
public class PcGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(PcGatewayApplication.class, args);
	}

	@Bean
	public AuthenticationFilter authenticationFilter() {
	    return new AuthenticationFilter();
	}
}
