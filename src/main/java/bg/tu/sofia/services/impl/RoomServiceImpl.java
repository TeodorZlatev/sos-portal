package bg.tu.sofia.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bg.tu.sofia.dtos.RoomDto;
import bg.tu.sofia.entities.Room;
import bg.tu.sofia.repositories.RoomRepository;
import bg.tu.sofia.services.RoomService;

@Component
public class RoomServiceImpl implements RoomService {
	
	@Autowired
	private RoomRepository roomRepository;

	@Override
	public List<RoomDto> getAllByBlockId(int blockId) {
		List<Room> rooms = roomRepository.findAllByBlockId(blockId);
		List<RoomDto> roomsDto = new ArrayList<RoomDto>();
		rooms.forEach(room -> roomsDto.add(RoomDto.fromEntity(room)));
		return roomsDto;
	}

	@Override
	public RoomDto getByRoomAndBlockId(String roomNumber, int blockId) {
		Room room = roomRepository.findByNumberAndBlockId(roomNumber, blockId);
		return RoomDto.fromEntity(room);
	}
}
