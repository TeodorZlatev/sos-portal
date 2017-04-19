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
					consumes = MediaType.APPLICATION_JSON_VALUE)
	public void insertInhabitant(@RequestBody UserDto user) {
		userService.insertInhabitant(user);
	}
	
	@RequestMapping(
			method = RequestMethod.GET,
			value = "/usersNightTaxesAboutBlock",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public List<UserDto> getPeopleWithNightTaxesByBlockId(@RequestParam int blockId, @RequestParam int pageNumber) {
		return userService.getPeopleWithNightTaxes(blockId, pageNumber);
	}
	
	@RequestMapping(
			method = RequestMethod.GET,
			value = "/usersNightTaxesAboutBlockCount",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public String getPeopleWithNightTaxesByBlockIdCount(@RequestParam int blockId, @RequestParam int pageNumber) {
		return userService.getCountPeopleWithNightTaxes(blockId, pageNumber);
	}
}
