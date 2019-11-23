package tim18.ftn.uns.ac.rs.paypalpayment.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tim18.ftn.uns.ac.rs.paypalpayment.dto.Field;

@RestController
@RequestMapping("/form")
public class FormController {

	@RequestMapping(value = "/data", method = RequestMethod.GET)
	public List<Field> getData() {
		List<Field> ret = new ArrayList<Field>();
		ret.add(new Field("ime", "Ime", true, "String"));
		ret.add(new Field("prezime", "Prezime", true, "String"));
		return ret;
	}

}
