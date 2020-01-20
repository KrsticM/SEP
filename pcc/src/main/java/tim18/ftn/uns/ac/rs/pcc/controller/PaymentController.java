package tim18.ftn.uns.ac.rs.pcc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import tim18.ftn.uns.ac.rs.pcc.dto.AcquirerResponseDTO;
import tim18.ftn.uns.ac.rs.pcc.dto.PccRequestDTO;
import tim18.ftn.uns.ac.rs.pcc.dto.PccResponseDTO;
import tim18.ftn.uns.ac.rs.pcc.service.BankInfoService;

@RestController
@RequestMapping("/pcc")
public class PaymentController {

	@Autowired
	private BankInfoService bankInfoService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@RequestMapping(value = "/payRedirect", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<?> payRedirect(@RequestBody PccRequestDTO pccRequestDTO) {
		System.out.println("U kontrolleru payRedirect");
		System.out.println(pccRequestDTO);
		
		// Skontati koja je banka iz PAN-a i poslati zahtev na tu banku
		String bankId = getBankIdFromPan(pccRequestDTO.getPanNumber());
		String bankUrl = bankInfoService.getBankUrl(bankId);
		System.out.println("bankURL: " + bankUrl);
		// Saljemo zahtev na BankUrl
		
		// Usmeravanje ka servisu banke (kupca)
		ResponseEntity<PccResponseDTO> responseEntity = restTemplate.exchange(bankUrl, HttpMethod.POST,
				new HttpEntity<PccRequestDTO>(pccRequestDTO), PccResponseDTO.class);
		
		System.out.println("Odgovor: ");
		System.out.println(responseEntity.getBody());
		// Sada ovaj odgovor treba poslati banci prodavca
		
		AcquirerResponseDTO ret = new AcquirerResponseDTO(responseEntity.getBody());
		return new ResponseEntity<>(ret, HttpStatus.OK);
	}
	
	private String getBankIdFromPan(String panNumber) {
		
		String withoutDashes = panNumber.replace("-", "");
		String number = withoutDashes.substring(1, 7);
		return number;
	}
}
