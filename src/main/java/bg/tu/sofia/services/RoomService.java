package bg.tu.sofia.services;

import java.util.List;

import bg.tu.sofia.dtos.RoomDto;

public interface RoomService {
	public List<RoomDto> getAllByBlockId(int blockId);

	public RoomDto getByRoomAndBlockId(String room, int blockId);
}
