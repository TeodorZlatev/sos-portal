package bg.tu.sofia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import bg.tu.sofia.dtos.NightTaxDto;
import bg.tu.sofia.services.NightTaxService;

@Controller
public class NightTaxController {

	@Autowired
	private NightTaxService nightTaxService;

	@RequestMapping(
			method = RequestMethod.POST, 
			value = "/nightTax", 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void createNightTax(NightTaxDto nightTax) {
		nightTaxService.createNightTax(nightTax);
	}
}
