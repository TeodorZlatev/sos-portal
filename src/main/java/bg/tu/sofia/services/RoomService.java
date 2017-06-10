package bg.tu.sofia.services;

import java.util.List;

import bg.tu.sofia.dtos.RoomDto;

public interface RoomService {
	public List<RoomDto> getAllByBlockId(int blockId);

	public RoomDto getByNumberAndBlockId(String roomNumber, int blockId);
}
