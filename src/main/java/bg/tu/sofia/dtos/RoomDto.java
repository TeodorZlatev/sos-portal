package bg.tu.sofia.dtos;

import bg.tu.sofia.entities.Room;

public class RoomDto {
	private int id;
	private String number;
	private int blockId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public int getBlockId() {
		return blockId;
	}

	public void setBlockId(int blockId) {
		this.blockId = blockId;
	}

	public Room toEntity() {
		Room room = new Room();
		room.setId(this.getId());
		room.setNumber(this.getNumber());
		// TODO: blockId
		return room;
	}

	public static RoomDto fromEntity(Room room) {
		RoomDto roomDto = new RoomDto();
		roomDto.setId(room.getId());
		roomDto.setNumber(room.getNumber());
		roomDto.setBlockId(room.getBlock().getId());
		return roomDto;
	}
}
