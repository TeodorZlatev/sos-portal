package bg.tu.sofia.services;

import java.util.List;

import bg.tu.sofia.dtos.RoomDto;

public interface RoomService {
	public List<RoomDto> getAllByBlockId(int blockId);

	public RoomDto getByRoomId(int roomId);

	public RoomDto getRoomByUserId(int userId);
}
