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

import bg.tu.sofia.dtos.UserDto;
import bg.tu.sofia.services.UserService;
import bg.tu.sofia.utils.StructuredResponse;

@RestController
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(method = RequestMethod.GET,
					value = "/api/hosts",
					produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('HOST','ADMINISTRATOR')")
	public List<UserDto> getHostsByRoomIdAndBlockId(@RequestParam int roomId, @RequestParam int blockId) {
		List<UserDto> hosts = userService.getAllByRoomIdAndBlockId(roomId, blockId);
		return hosts;
	}
	
	
	@RequestMapping(method = RequestMethod.POST,
					value = "/api/user",
					produces = MediaType.APPLICATION_JSON_VALUE,
					consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('HOST','ADMINISTRATOR')")
	public StructuredResponse insertUser(@RequestBody UserDto user) {
		return userService.insertUser(user);
	}
	
	@RequestMapping(
			method = RequestMethod.GET,
			value = "/api/userNightTaxes",
			produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('INHABITED')")
	public UserDto getPersonWithNightTaxes(@RequestParam int userId) {
		return userService.getPersonWithNightTaxes(userId);
	}
	
	@RequestMapping(
			method = RequestMethod.GET,
			value = "/api/usersNightTaxes",
			produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('HOST','CASHIER','ADMINISTRATOR')")
	public List<UserDto> getPeopleWithNightTaxes(@RequestParam(required = false) String blockId, @RequestParam int pageNumber) {
		return userService.getPeopleWithNightTaxes(null, blockId, pageNumber);
	}
	
	@RequestMapping(
			method = RequestMethod.GET,
			value = "/api/usersNightTaxes/pagination",
			produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('HOST','CASHIER','ADMINISTRATOR')")
	public String getPaginationPeopleWithNightTaxes(@RequestParam(required = false) String marker, @RequestParam(required = false) String blockId, @RequestParam int pageNumber) {
		return userService.getPaginationPeopleWithNightTaxes(marker, blockId, pageNumber);
	}
	
	@RequestMapping(
			method = RequestMethod.GET,
			value = "/api/search",
			produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('HOST','CASHIER','ADMINISTRATOR')")
	public List<UserDto> searchByMarker(@RequestParam String marker, @RequestParam(required = false) String blockId, @RequestParam int pageNumber) {
		return userService.getPeopleWithNightTaxes(marker, blockId, pageNumber);
	}
}
