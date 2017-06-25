package bg.tu.sofia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import bg.tu.sofia.dtos.Data;
import bg.tu.sofia.services.AuthenticationService;
import bg.tu.sofia.utils.StructuredResponse;

@RestController
public class AuthenticationController {
	
	@Autowired
	private AuthenticationService authenticationService;

	@RequestMapping(method = RequestMethod.POST, 
					value = "/login",
					consumes = MediaType.APPLICATION_JSON_VALUE, 
					produces = MediaType.APPLICATION_JSON_VALUE)
	public StructuredResponse login(@RequestBody Data data) {
		return authenticationService.authenticate(data.getPersonalNumber(), data.getPassword());
	}

}
