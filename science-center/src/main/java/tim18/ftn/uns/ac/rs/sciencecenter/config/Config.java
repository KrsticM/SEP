package tim18.ftn.uns.ac.rs.sciencecenter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Config {

	@Bean
	public RestTemplate restTemplate()  {
		return new RestTemplate();
	}
}
