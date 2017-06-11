package bg.tu.sofia.services.impl;

import java.util.List;
import java.util.stream.Collectors;

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
		List<Room> rooms = roomRepository.findAllByBlockIdOrderByNumberAsc(blockId);
		
		return rooms.stream().map(this::fromEntity).collect(Collectors.toList());
	}

	@Override
	public RoomDto getByRoomId(int roomId) {
		Room room = roomRepository.findOne(roomId);
		
		return this.fromEntity(room);
	}
	
	@Override
	public String getRoomByUserId(int userId) {
		return roomRepository.findByUserId(userId);
	}
	
	private Room toEntity(RoomDto roomDto) {
		Room room = new Room();
		
		room.setId(roomDto.getId());
		room.setNumber(roomDto.getNumber());
		// TODO: blockId
		
		return room;
	}

	private RoomDto fromEntity(Room room) {
		RoomDto roomDto = new RoomDto();
		
		roomDto.setId(room.getId());
		roomDto.setNumber(room.getNumber());
		roomDto.setBlockId(room.getBlock().getId());
		
		return roomDto;
	}

}
