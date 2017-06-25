package bg.tu.sofia.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bg.tu.sofia.dtos.RoomDto;
import bg.tu.sofia.services.RoomService;

@RestController
public class RoomController {

	@Autowired
	private RoomService roomService;

	@RequestMapping(method = RequestMethod.GET, 
					value = "/api/rooms",
					produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('HOST','ADMINISTRATOR')")
	public List<RoomDto> getRoomsByBlockId(@RequestParam int blockId) {
		List<RoomDto> rooms = roomService.getAllByBlockId(blockId);
		return rooms;
	}
}
