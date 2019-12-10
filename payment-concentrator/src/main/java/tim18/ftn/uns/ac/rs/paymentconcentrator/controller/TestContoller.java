package tim18.ftn.uns.ac.rs.paymentconcentrator.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestContoller {

	@PreAuthorize("hasAnyRole('USER')")
	@GetMapping("/personalni")
	public String personalni() {
		return "User";
	}

}
