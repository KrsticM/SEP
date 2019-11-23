package tim18.ftn.uns.ac.rs.bitcoinpayment.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tim18.ftn.uns.ac.rs.bitcoinpayment.dto.Field;

@RestController
@RequestMapping("/form")
public class FormController {

	@RequestMapping(value = "/data", method = RequestMethod.GET)
	public List<Field> getData() {
		List<Field> ret = new ArrayList<Field>();
		ret.add(new Field("nesto", "Nesto", true, "String"));
		ret.add(new Field("josNesto", "Jos nesto", true, "String"));
		return ret;
	}
}
