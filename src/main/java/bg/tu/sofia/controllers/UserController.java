package bg.tu.sofia.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
					value = "/hosts",
					produces = MediaType.APPLICATION_JSON_VALUE)
	public List<UserDto> getHostsByRoomIdAndBlockId(@RequestParam int roomId, @RequestParam int blockId) {
		List<UserDto> hosts = userService.getAllByRoomIdAndBlockId(roomId, blockId);
		return hosts;
	}
	
	
	@RequestMapping(method = RequestMethod.POST,
					value = "/inhabitant",
					produces = MediaType.APPLICATION_JSON_VALUE,
					consumes = MediaType.APPLICATION_JSON_VALUE)
	public StructuredResponse insertInhabitant(@RequestBody UserDto user) {
		return userService.insertInhabitant(user);
	}
	
	@RequestMapping(
			method = RequestMethod.GET,
			value = "/userNightTaxes",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public UserDto getPersonWithNightTaxes(@RequestParam int userId) {
		return userService.getPersonWithNightTaxes(userId);
	}
	
	@RequestMapping(
			method = RequestMethod.GET,
			value = "/usersNightTaxes",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public List<UserDto> getPeopleWithNightTaxes(@RequestParam(required = false) String blockId, @RequestParam int pageNumber) {
		return userService.getPeopleWithNightTaxes(blockId, pageNumber);
	}
	
	@RequestMapping(
			method = RequestMethod.GET,
			value = "/usersNightTaxesCount",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public String getPeopleWithNightTaxesCount(@RequestParam(required = false) String blockId, @RequestParam int pageNumber) {
		return userService.getCountPeopleWithNightTaxes(blockId, pageNumber);
	}
	
	@RequestMapping(
			method = RequestMethod.GET,
			value = "/search",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public List<UserDto> searchByMarker(@RequestParam String marker, @RequestParam(required = false) String blockId, @RequestParam int pageNumber) {
		return userService.getPeopleWithNightTaxesByMarker(marker, blockId, pageNumber);
	}
}
