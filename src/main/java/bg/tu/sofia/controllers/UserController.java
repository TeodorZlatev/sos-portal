package bg.tu.sofia.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import bg.tu.sofia.dtos.UserDto;
import bg.tu.sofia.services.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.GET,
					value = "/hosts/{roomId : \\d+}/{blockId : \\d+}",
					produces = MediaType.APPLICATION_JSON_VALUE)
	public List<UserDto> getHostsByRoomIdAndBlockId(String roomId, String blockId) {
		List<UserDto> hosts = userService.getAllByRoomIdAndBlockId(Integer.parseInt(roomId), Integer.parseInt(blockId));
		return hosts;
	}
	
	
	@RequestMapping(method = RequestMethod.POST,
					value = "/inhabitant",
					consumes = "application/json")
	public void insertInhabitant(@RequestBody UserDto user) {
		userService.insertInhabitant(user);
	}
}
