package tim18.ftn.uns.ac.rs.bitcoinpayment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FormController {

	@RequestMapping(value = "/index/{appId}")
	public String index(@RequestHeader(value="UserId") Integer userId, @PathVariable Integer appId) {
		System.out.println(appId);
		return "index";
	}

}

/*
 * @RestController
 * 
 * @RequestMapping("/form") public class FormController {
 * 
 * @RequestMapping(value = "/data", method = RequestMethod.GET) public
 * List<Field> getData() { List<Field> ret = new ArrayList<Field>(); ret.add(new
 * Field("nesto", "Nesto", true, "String")); ret.add(new Field("josNesto",
 * "Jos nesto", true, "String")); return ret; } }
 */
