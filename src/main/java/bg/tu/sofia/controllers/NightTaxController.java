package bg.tu.sofia.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bg.tu.sofia.constants.NightTaxStatusEnum;
import bg.tu.sofia.dtos.NightTaxDto;
import bg.tu.sofia.services.NightTaxService;
import bg.tu.sofia.utils.StructuredResponse;

@RestController
public class NightTaxController {

	@Autowired
	private NightTaxService nightTaxService;

	@RequestMapping(
			method = RequestMethod.POST, 
			value = "/api/nightTax", 
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('HOST','ADMINISTRATOR')")
	public StructuredResponse createNightTax(@RequestBody NightTaxDto nightTax) {
		return nightTaxService.createNightTax(nightTax);
	}
	
	@RequestMapping(
			method = RequestMethod.GET,
			value = "/api/nightTaxesPerUser",
			produces = MediaType.APPLICATION_JSON_VALUE)
	// all roles
	public List<NightTaxDto> getPeopleWithNightTaxes(@RequestParam(name = "userId") int userId,
			@RequestParam(name = "status") NightTaxStatusEnum status,
			@RequestParam(name = "pageNumber") int pageNumber) {
		return nightTaxService.getNightTaxesOfUser(userId, status, pageNumber);
	}
	
	@RequestMapping(
			method = RequestMethod.GET,
			value = "/api/nightTaxesPerUserCount",
			produces = MediaType.APPLICATION_JSON_VALUE)
	// all roles
	public String getNightTaxesCount(@RequestParam(name = "userId") int userId,
			@RequestParam(name = "status") NightTaxStatusEnum status,
			@RequestParam(name = "pageNumber") int pageNumber) {
		return nightTaxService.getCountNightTaxes(userId, status, pageNumber);
	}
	
	@RequestMapping(
			method = RequestMethod.POST,
			value = "/api/payNightTaxes",
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('CASHIER','ADMINISTRATOR')")
	public StructuredResponse payNightTaxes(@RequestBody List<NightTaxDto> nightTaxes) {
		return nightTaxService.payNightTaxes(nightTaxes);
	}
	
	
	
}