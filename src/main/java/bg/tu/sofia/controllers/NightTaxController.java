package bg.tu.sofia.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bg.tu.sofia.constants.NightTaxStatusEnum;
import bg.tu.sofia.dtos.NightTaxDto;
import bg.tu.sofia.services.NightTaxService;

@RestController
public class NightTaxController {

	@Autowired
	private NightTaxService nightTaxService;

	@RequestMapping(
			method = RequestMethod.POST, 
			value = "/nightTax", 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public void createNightTax(@RequestBody NightTaxDto nightTax) {
		nightTaxService.createNightTax(nightTax);
	}
	
	@RequestMapping(
			method = RequestMethod.GET,
			value = "/nightTaxesPerUser",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public List<NightTaxDto> getPeopleWithNightTaxes(@RequestParam(name = "userId") int userId,
			@RequestParam(name = "status") NightTaxStatusEnum status,
			@RequestParam(name = "pageNumber") int pageNumber) {
		return nightTaxService.getNightTaxesOfUser(userId, status, pageNumber);
	}
	
	@RequestMapping(
			method = RequestMethod.GET,
			value = "/nightTaxesPerUserCount",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public String getNightTaxesCount(@RequestParam(name = "userId") int userId,
			@RequestParam(name = "status") NightTaxStatusEnum status,
			@RequestParam(name = "pageNumber") int pageNumber) {
		return nightTaxService.getCountNightTaxes(userId, status, pageNumber);
	}
}
