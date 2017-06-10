package bg.tu.sofia.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import bg.tu.sofia.dtos.Data;
import bg.tu.sofia.services.UserService;
import bg.tu.sofia.utils.StructuredResponse;
import bg.tu.sofia.utils.StructuredResponse.RESPONSE_STATUS;

@RestController
public class AuthenticationController {

	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.POST, value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public StructuredResponse login(@RequestBody Data data) {
		try {
			Integer userId = authenticate(data.getPersonalNumber(), data.getPassword());

			if (userId != null) {
				String token = generateToken();
				this.userService.insertToken(userId, token);
				
				return new StructuredResponse(200, RESPONSE_STATUS.SUCCESS, token, null);
			}

			return new StructuredResponse(401, RESPONSE_STATUS.FAIL, null, "Невалидни данни");
		} catch (Exception e) {
			return new StructuredResponse(401, RESPONSE_STATUS.FAIL, null, "Невалидни данни");
		}
	}

	private Integer authenticate(String personalNumber, String password) throws Exception {
		Integer userId = this.userService.authenticateUser(personalNumber, password);
		return userId;
	}

	private String generateToken() {
		return UUID.randomUUID().toString();
	}
}
