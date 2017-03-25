package bg.tu.sofia.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import bg.tu.sofia.dtos.RoomDto;
import bg.tu.sofia.services.RoomService;

@Controller
public class RoomController {

	@Autowired
	private RoomService roomService;

	@RequestMapping(method = RequestMethod.GET, 
					value = "/rooms",
					produces = "application/json")
	@ResponseBody
	public List<RoomDto> getRoomsByBlockId() {
		List<RoomDto> rooms = roomService.getAllByBlockId(1);
		return rooms;
	}
}
