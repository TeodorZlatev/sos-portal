package bg.tu.sofia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import bg.tu.sofia.dtos.Data;
import bg.tu.sofia.security.util.JwtTokenGenerator;
import bg.tu.sofia.services.AuthenticationService;
import bg.tu.sofia.utils.StructuredResponse;

@RestController
public class AuthenticationController {

	
	@Autowired
	private AuthenticationService authenticationService;
	
	@Autowired
	private JwtTokenGenerator jwtTokenGenerator;

	@RequestMapping(method = RequestMethod.POST, 
					value = "/login",
					consumes = MediaType.APPLICATION_JSON_VALUE, 
					produces = MediaType.APPLICATION_JSON_VALUE)
	public StructuredResponse login(@RequestBody Data data) {
		return authenticationService.authenticate(data.getPersonalNumber(), data.getPassword());
	}

    @RequestMapping(value = "/api/admin", method = RequestMethod.POST)
//    @PreAuthorize("hasRole('ADMIN')")
    public String admin2() {

        return "I'm admin";
    }
    
    @RequestMapping(value = "/api/user", method = RequestMethod.POST)
    @PreAuthorize("hasRole('INHABITED')")
    public String user2() {

        return "I'm user";
    }

}
