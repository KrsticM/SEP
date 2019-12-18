package tim18.ftn.uns.ac.rs.bitcoinpayment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FormController {

	@RequestMapping(value = "/index/{appId}")
	public String index(@PathVariable String appId, Model model) {
	
		System.out.println(appId);
		
		
		model.addAttribute("appId", appId);
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
